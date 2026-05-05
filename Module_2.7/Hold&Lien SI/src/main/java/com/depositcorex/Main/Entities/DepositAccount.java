package com.depositcorex.Main.Entities;



import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "deposit_account")
public class DepositAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false)
    private Long customerId; // Reference to CustomerOnboarding

    @Column(nullable = false)
    private Long productId; // Reference to DepositProduct

    @Column(unique = true, nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String category; // SAVINGS, CURRENT, FD, RD

    @Column(nullable = false)
    private String currency; // e.g., "USD"

    @Column(nullable = false)
    private LocalDateTime openDate = LocalDateTime.now();

    @Column(nullable = false, precision = 18, scale = 4)
    private BigDecimal currentBalance = BigDecimal.ZERO;

    @Column(nullable = false)
    private String status; // ACTIVE, DORMANT, CLOSED
}