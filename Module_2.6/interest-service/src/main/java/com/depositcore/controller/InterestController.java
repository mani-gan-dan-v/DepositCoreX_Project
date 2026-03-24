package com.depositcore.controller;

import com.depositcore.dto.*;
import com.depositcore.service.InterestService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/interest")
public class InterestController {

    private final InterestService service;

    public InterestController(InterestService service) {
        this.service = service;
    }

    // Calculate + Accrue
    @PostMapping("/accrue")
    public InterestResponseDTO accrueInterest(@RequestBody InterestRequestDTO request) {
        return service.createAccrual(request);
    }

    // Post Interest
    @PostMapping("/post/{accountId}")
    public InterestResponseDTO postInterest(
            @PathVariable Long accountId,
            @RequestParam BigDecimal amount) {

        return service.postInterest(accountId, amount);
    }
}