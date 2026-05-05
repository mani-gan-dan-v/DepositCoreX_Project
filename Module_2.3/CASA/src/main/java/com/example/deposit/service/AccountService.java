package com.example.deposit.service;

import com.example.deposit.dto.AccountResponse;
import com.example.deposit.dto.CasaAccountRequest;
import com.example.deposit.dto.TermDepositRequest;
import com.example.deposit.entity.DepositAccount;
import com.example.deposit.entity.TermDeposit;

public interface AccountService {

    AccountResponse createCasaAccount(CasaAccountRequest request);

    AccountResponse createTermDepositAccount(TermDepositRequest request);
    

    DepositAccount getDepositAccountById(Long accountId);

    TermDeposit getTermDepositByAccountId(Long accountId);

}