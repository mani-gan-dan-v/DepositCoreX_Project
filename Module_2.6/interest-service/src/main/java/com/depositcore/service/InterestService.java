package com.depositcore.service;

import com.depositcore.dto.*;
import com.depositcore.entity.*;
import com.depositcore.repository.*;
import org.springframework.stereotype.Service;

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
    public BigDecimal calculateInterest(BigDecimal principal, BigDecimal rate, int days) {

        BigDecimal interest = principal
                .multiply(rate)
                .multiply(BigDecimal.valueOf(days))
                .divide(BigDecimal.valueOf(100 * 365), 2, RoundingMode.HALF_UP);

        return interest;
    }

    // Create Accrual
    public InterestResponseDTO createAccrual(InterestRequestDTO request) {

        BigDecimal interest = calculateInterest(
                request.getPrincipal(),
                request.getRate(),
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

    // Post Interest
    public InterestResponseDTO postInterest(Long accountId, BigDecimal amount) {

        InterestPosting posting = InterestPosting.builder()
                .accountId(accountId)
                .amount(amount)
                .postingDate(LocalDateTime.now())
                .postingType("CASA")
                .build();

        postingRepo.save(posting);

        return InterestResponseDTO.builder()
                .accountId(accountId)
                .interestAmount(amount)
                .message("Interest posted successfully")
                .build();
    }
}