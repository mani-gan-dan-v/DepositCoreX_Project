package com.depositcorex.Main.Entities;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Entity
@Data
@Table(name = "standing_instruction")
public class StandingInstruction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long siId; 

    @ManyToOne
    @JoinColumn(name = "from_account_id", nullable = false)
    private DepositAccount fromAccount; 

    @ManyToOne
    @JoinColumn(name = "to_account_id", nullable = false)
    private DepositAccount toAccount; 

    @Column(nullable = false)
    private BigDecimal amount; 

    @Column(nullable = false)
    private String frequency; // e.g., "MONTHLY", "WEEKLY" [cite: 1]

    @Column(nullable = false)
    private LocalDate nextRunDate; 

    @Column(nullable = false)
    private String status; // e.g., "ACTIVE", "FAILED", "COMPLETED" [cite: 1]
}