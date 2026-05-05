package com.depositcore.service;

import com.depositcore.dto.*;
import com.depositcore.entity.*;
import com.depositcore.exception.ResourceNotFoundException;
import com.depositcore.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;

@Service
public class InterestService {

    private final InterestAccrualRepository accrualRepo;
    private final InterestPostingRepository postingRepo;

    public InterestService(InterestAccrualRepository accrualRepo,
                           InterestPostingRepository postingRepo) {
        this.accrualRepo = accrualRepo;
        this.postingRepo = postingRepo;
    }

    // Interest Calculation

    private BigDecimal calculateInterest(BigDecimal principal, int days) {

        BigDecimal rate;

        if (principal.compareTo(new BigDecimal("10000")) <= 0) {
            rate = new BigDecimal("3");
        } else if (principal.compareTo(new BigDecimal("50000")) <= 0) {
            rate = new BigDecimal("4");
        } else {
            rate = new BigDecimal("5");
        }

        return principal
                .multiply(rate)
                .multiply(BigDecimal.valueOf(days))
                .divide(new BigDecimal("36500"), 2, RoundingMode.HALF_UP);
    }


    // Create Accrual
    @Transactional
    public InterestResponseDTO createAccrual(InterestRequestDTO request) {

        BigDecimal interest = calculateInterest(
                request.getPrincipal(),
                request.getDays()
        );

        InterestAccrual accrual = InterestAccrual.builder()
                .accountId(request.getAccountId())
                .periodStart(LocalDate.now().minusDays(request.getDays()))
                .periodEnd(LocalDate.now())
                .interestAmount(interest)
                .calculatedDate(LocalDateTime.now())
                .build();

        accrualRepo.save(accrual);

        return InterestResponseDTO.builder()
                .accountId(request.getAccountId())
                .interestAmount(interest)
                .message("Interest accrued successfully")
                .build();
    }

    @Transactional
    public InterestResponseDTO postInterest(Long accountId, PostingType postingType) {

        InterestAccrual accrual = accrualRepo
                .findTopByAccountIdOrderByCalculatedDateDesc(accountId);

        if (accrual == null) {
            throw new ResourceNotFoundException("No accrual found for account");
        }

        boolean alreadyPosted = postingRepo.existsByAccountId(accountId)
                &&
                accrualRepo.findTopByAccountIdOrderByCalculatedDateDesc(accountId)!=null;

        if(alreadyPosted){
            throw new IllegalStateException("Interest already posted for this account");
        }

        InterestPosting posting = InterestPosting.builder()
                .accountId(accountId)
                .amount(accrual.getInterestAmount())
                .postingDate(LocalDateTime.now())
                .postingType(postingType)
                .build();


        postingRepo.save(posting);

        return InterestResponseDTO.builder()
                .accountId(accountId)
                .interestAmount(accrual.getInterestAmount())
                .message("Interest posted successfully")
                .build();
    }
}