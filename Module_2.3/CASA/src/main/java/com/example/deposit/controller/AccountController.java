package com.example.deposit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.deposit.dto.AccountResponse;
import com.example.deposit.dto.CasaAccountRequest;
import com.example.deposit.dto.TermDepositRequest;
import com.example.deposit.entity.DepositAccount;
import com.example.deposit.entity.TermDeposit;
import com.example.deposit.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    @PostMapping("/casa")
    public ResponseEntity<AccountResponse> createCasaAccount(
            @RequestBody CasaAccountRequest request) {

        AccountResponse response = accountService.createCasaAccount(request);
        return ResponseEntity.ok(response);
    }

    
    @PostMapping("/term-deposit")
    public ResponseEntity<AccountResponse> createTermDepositAccount(
            @RequestBody TermDepositRequest request) {

        AccountResponse response = accountService.createTermDepositAccount(request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("{accountId}")
    public DepositAccount getDepositAccountById(@PathVariable Long accountId) {
    	return accountService.getDepositAccountById(accountId);
    }
    
    @GetMapping("/term-deposit/{accountId}")
    public TermDeposit getTermDepositByAccountId(@PathVariable Long accountId) {
    	return accountService.getTermDepositByAccountId(accountId);
    }
    
}