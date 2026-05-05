package com.depositcorex.Main.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.depositcorex.Main.Entities.*;
import com.depositcorex.Main.Repository.*;
import com.depositcorex.Main.Exception.ResourceNotFoundException;
import com.depositcorex.Main.Exception.InvalidTransactionException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ServicingService {

    private final HoldRepository holdRepo;
    private final StandingInstructionRepository siRepo;
    private final AccountRepository accountRepo;

    // Constructor Injection (Proper standard)
    public ServicingService(HoldRepository holdRepo, 
                            StandingInstructionRepository siRepo, 
                            AccountRepository accountRepo) {
        this.holdRepo = holdRepo;
        this.siRepo = siRepo;
        this.accountRepo = accountRepo;
    }

    /**
     * Places a Hold or Lien on an account.
     */
    @Transactional
    public HoldOrLien placeHold(Long accountId, BigDecimal amount, String reason, String type) {
        DepositAccount account = accountRepo.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account ID " + accountId + " not found"));

        HoldOrLien hold = new HoldOrLien();
        hold.setAccount(account);
        hold.setAmount(amount);
        hold.setHoldType(type);
        hold.setReason(reason);
        hold.setStatus("ACTIVE");
        hold.setPlacedDate(LocalDateTime.now());

        return holdRepo.save(hold);
    }

    /**
     * Releases an existing Hold or Lien.
     */
    @Transactional
    public void releaseHold(Long holdId) {
        HoldOrLien hold = holdRepo.findById(holdId)
                .orElseThrow(() -> new ResourceNotFoundException("Hold record ID " + holdId + " not found"));
        
        hold.setStatus("RELEASED");
        holdRepo.save(hold);
    }

    /**
     * Calculates available balance.
     */
    public BigDecimal getAvailableBalance(Long accountId) {
        DepositAccount account = accountRepo.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        BigDecimal totalHolds = holdRepo.sumActiveHolds(accountId);
        if (totalHolds == null) totalHolds = BigDecimal.ZERO;

        return account.getCurrentBalance().subtract(totalHolds);
    }

    /**
     * Sets up a recurring transfer.
     */
    @Transactional
    public StandingInstruction createStandingInstruction(Long fromAccId, Long toAccId, 
                                                         BigDecimal amount, String frequency) {
        DepositAccount fromAccount = accountRepo.findById(fromAccId)
                .orElseThrow(() -> new ResourceNotFoundException("Source Account " + fromAccId + " not found"));
        
        DepositAccount toAccount = accountRepo.findById(toAccId)
                .orElseThrow(() -> new ResourceNotFoundException("Destination Account " + toAccId + " not found"));

        StandingInstruction si = new StandingInstruction();
        si.setFromAccount(fromAccount);
        si.setToAccount(toAccount);
        si.setAmount(amount);
        si.setFrequency(frequency);
        si.setNextRunDate(LocalDate.now()); 
        si.setStatus("ACTIVE");

        return siRepo.save(si);
    }

    /**
     * Validates if funds are available after subtracting holds.
     */
    public void validateFundAvailability(Long accountId, BigDecimal debitAmount) {
        DepositAccount account = accountRepo.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        BigDecimal totalHolds = holdRepo.sumActiveHolds(accountId);
        if (totalHolds == null) totalHolds = BigDecimal.ZERO;

        BigDecimal availableBalance = account.getCurrentBalance().subtract(totalHolds);

        if (debitAmount.compareTo(availableBalance) > 0) {
            throw new InvalidTransactionException("Insufficient available funds. Required: " + debitAmount + 
                                                 ", Available: " + availableBalance);
        }
    }

    /**
     * Validates account status for SI execution.
     */
    public void validateAccountStatusForSI(DepositAccount account) {
        String status = account.getStatus();
        
        if ("CLOSED".equalsIgnoreCase(status) || "DORMANT".equalsIgnoreCase(status)) {
            throw new InvalidTransactionException("SI Execution Failed: Account " + 
                                                 account.getAccountNumber() + " is " + status);
        }
        
        if (!"ACTIVE".equalsIgnoreCase(status)) {
            throw new InvalidTransactionException("SI Execution Failed: Account must be ACTIVE.");
        }
    }
}