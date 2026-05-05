package com.cts.project.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "depositaccount")
public class DepositAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountid")
    private Long accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerid")
    private CustomerRef customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productid")
    private DepositProduct product;

    @Column(name = "accountnumber", length = 40, unique = true)
    private String accountNumber;

    @Column(name = "category", length = 40)
    private String category;

    @Column(name = "currency", length = 10)
    private String currency;

    @Column(name = "opendate")
    private LocalDate openDate;

    @Column(name = "status", length = 30)
    private String status;

    public DepositAccount() {}

    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }

    public CustomerRef getCustomer() { return customer; }
    public void setCustomer(CustomerRef customer) { this.customer = customer; }

    public DepositProduct getProduct() { return product; }
    public void setProduct(DepositProduct product) { this.product = product; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public LocalDate getOpenDate() { return openDate; }
    public void setOpenDate(LocalDate openDate) { this.openDate = openDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
