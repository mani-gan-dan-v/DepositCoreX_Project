package com.depositcore.controller;

import com.depositcore.dto.InterestRequestDTO;
import com.depositcore.dto.InterestResponseDTO;
import com.depositcore.service.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/interest")
public class InterestController {

    @Autowired
    private InterestService interestService;

    @PostMapping("/accrue")
    public InterestResponseDTO accrue(@RequestBody InterestRequestDTO request) {
        return interestService.createAccrual(request);
    }

    @PostMapping("/post")
    public InterestResponseDTO post(
            @RequestParam Long accountId,
            @RequestParam BigDecimal amount) {

        return interestService.postInterest(accountId, amount);
    }
}