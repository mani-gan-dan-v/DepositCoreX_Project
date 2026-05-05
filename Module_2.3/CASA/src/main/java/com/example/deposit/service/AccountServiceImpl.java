package com.example.deposit.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.deposit.dto.AccountResponse;
import com.example.deposit.dto.CasaAccountRequest;
import com.example.deposit.dto.TermDepositRequest;
import com.example.deposit.entity.CustomerRef;
import com.example.deposit.entity.DepositAccount;
import com.example.deposit.entity.DepositProduct;
import com.example.deposit.entity.TermDeposit;
import com.example.deposit.exception.ResourceNotFoundException;
import com.example.deposit.repository.CustomerRefRepository;
import com.example.deposit.repository.DepositAccountRepository;
import com.example.deposit.repository.DepositProductRepository;
import com.example.deposit.repository.TermDepositRepository;
import com.example.deposit.service.validation.AmountValidator;
import com.example.deposit.service.validation.ProductValidator;

@Service
public class AccountServiceImpl implements AccountService {

    private final CustomerRefRepository customerRefRepository;
    private final DepositProductRepository depositProductRepository;
    private final DepositAccountRepository depositAccountRepository;
    private final TermDepositRepository termDepositRepository;
    private final ProductValidator productValidator;
    private final AmountValidator amountValidator;

    public AccountServiceImpl(CustomerRefRepository customerRefRepository,
                              DepositProductRepository depositProductRepository,
                              DepositAccountRepository depositAccountRepository,
                              TermDepositRepository termDepositRepository,
                              ProductValidator productValidator,
                              AmountValidator amountValidator) {

        this.customerRefRepository = customerRefRepository;
        this.depositProductRepository = depositProductRepository;
        this.depositAccountRepository = depositAccountRepository;
        this.termDepositRepository = termDepositRepository;
        this.productValidator = productValidator;
        this.amountValidator = amountValidator;
    }

    @Override
    public AccountResponse createCasaAccount(CasaAccountRequest request) {

        CustomerRef customer = customerRefRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        DepositProduct product = depositProductRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        
        // validation checks

//        if (!"ACTIVE".equalsIgnoreCase(product.getStatus())) {
//        	throw new RuntimeException("Product is not active");
//        }
//
//        if (!"CASA".equalsIgnoreCase(product.getCategory())) {
//        	throw new RuntimeException("Invalid product for CASA account creation");
//        }
        
        productValidator.validateForCasa(product);

        DepositAccount account = new DepositAccount();
        account.setCustomer(customer);
        account.setProduct(product);
        account.setAccountNumber(generateAccountNumber());
        account.setCategory(request.getCategory());     // SAVINGS / CURRENT
        account.setCurrency(request.getCurrency());
        account.setOpenDate(LocalDate.now());
        account.setStatus("ACTIVE");

        depositAccountRepository.save(account);

        AccountResponse response = new AccountResponse();
        response.setAccountId(account.getAccountId());
        response.setAccountNumber(account.getAccountNumber());
        response.setStatus(account.getStatus());
        response.setMessage("CASA account created successfully");

        return response;
    }

    @Override
    @Transactional
    public AccountResponse createTermDepositAccount(TermDepositRequest request) {

        CustomerRef customer = customerRefRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        DepositProduct product = depositProductRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        

//        if (!"ACTIVE".equalsIgnoreCase(product.getStatus())) {
//        	throw new RuntimeException("Product is not active");
//        }
//
//        String category = product.getCategory();
//
//        if (!("FD".equalsIgnoreCase(category) || "RD".equalsIgnoreCase(category))) {
//        	throw new RuntimeException("Invalid product for Term Deposit creation");
//        }
        productValidator.validateForTermDeposit(product);
        
//        if (request.getPrincipalAmount() == null) {
//            throw new RuntimeException("Principal amount is required");
//        }
//
//        if (request.getPrincipalAmount().compareTo(BigDecimal.ZERO) <= 0) {
//            throw new RuntimeException("Principal amount must be greater than zero");
//        }
        
        amountValidator.validate(request.getPrincipalAmount(), product);
        
        DepositAccount account = new DepositAccount();
        account.setCustomer(customer);
        account.setProduct(product);
        account.setAccountNumber(generateAccountNumber());
        account.setCategory(product.getCategory());   // FD or RD
        account.setCurrency(request.getCurrency());
        account.setOpenDate(LocalDate.now());
        account.setStatus("ACTIVE");

        depositAccountRepository.save(account);

        TermDeposit termDeposit = new TermDeposit();
        termDeposit.setAccount(account);
        termDeposit.setPrincipalAmount(request.getPrincipalAmount());
        termDeposit.setTenureMonths(request.getTenureMonths());
        termDeposit.setRate(null); // Interest to be calculated later
        termDeposit.setStartDate(LocalDate.now());
        termDeposit.setMaturityDate(
                LocalDate.now().plusMonths(request.getTenureMonths()));
        termDeposit.setPayoutMode(request.getPayoutMode());
        termDeposit.setStatus("ACTIVE");

        termDepositRepository.save(termDeposit);

        AccountResponse response = new AccountResponse();
        response.setAccountId(account.getAccountId());
        response.setAccountNumber(account.getAccountNumber());
        response.setStatus(account.getStatus());
        response.setMessage("Term Deposit account created successfully");

        return response;
    }
    
    @Override
    public DepositAccount getDepositAccountById(Long accountId) {
        return depositAccountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
    }

    @Override
    public TermDeposit getTermDepositByAccountId(Long accountId) {
        return termDepositRepository.findByAccount_AccountId(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Term Deposit not found"));
        
    }


    private String generateAccountNumber() {
        return "ACCT-" + UUID.randomUUID()
                .toString()
                .substring(0, 10)
                .toUpperCase();
    }
}