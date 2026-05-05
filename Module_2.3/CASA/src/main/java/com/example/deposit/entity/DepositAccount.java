package com.example.deposit.entity;

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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DepositAccount")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositAccount {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "AccountID")
	    private Long accountId;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "CustomerID")
	    private CustomerRef customer;
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "ProductID")
	    private DepositProduct product;

	    @Column(name = "AccountNumber", length = 40, unique = true)
	    private String accountNumber;

	    @Column(name = "Category", length = 40)
	    private String category;

	    @Column(name = "Currency", length = 10)
	    private String currency;

	    @Column(name = "OpenDate")
	    private LocalDate openDate;

	    @Column(name = "Status", length = 30)
	    private String status;
	    
}
