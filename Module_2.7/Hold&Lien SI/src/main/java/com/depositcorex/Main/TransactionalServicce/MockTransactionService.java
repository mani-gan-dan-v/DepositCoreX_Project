package com.depositcorex.Main.TransactionalServicce;



import com.depositcorex.Main.Entities.*;
import com.depositcorex.Main.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class MockTransactionService {

    @Autowired private AccountRepository accountRepo;

    @Transactional
    public void internalTransfer(DepositAccount from, DepositAccount to, BigDecimal amount) {
        // 1. Deduct from sender
        from.setCurrentBalance(from.getCurrentBalance().subtract(amount));
        
        // 2. Add to receiver
        to.setCurrentBalance(to.getCurrentBalance().add(amount));

        // 3. Save both (MySQL updates)
        accountRepo.save(from);
        accountRepo.save(to);
        
        System.out.println("LEDGER UPDATED: Moved " + amount + " from " + from.getAccountNumber() + " to " + to.getAccountNumber());
    }
}