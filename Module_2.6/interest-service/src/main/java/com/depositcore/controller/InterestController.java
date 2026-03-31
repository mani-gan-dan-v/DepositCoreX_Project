package com.depositcore.controller;

import com.depositcore.dto.InterestRequestDTO;
import com.depositcore.dto.InterestResponseDTO;
import com.depositcore.entity.PostingType;
import com.depositcore.service.InterestService;
import com.depositcore.entity.PostingType;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/interest")
public class InterestController {

    @Autowired
    private InterestService interestService;

    @PostMapping("/accrue")
    public InterestResponseDTO accrue(@Valid @RequestBody InterestRequestDTO request) {
        return interestService.createAccrual(request);
    }

    @PostMapping("/post")
    public InterestResponseDTO post(
            @RequestParam Long accountId, @RequestParam PostingType postingType) {

        return interestService.postInterest(accountId,postingType);
    }
}
