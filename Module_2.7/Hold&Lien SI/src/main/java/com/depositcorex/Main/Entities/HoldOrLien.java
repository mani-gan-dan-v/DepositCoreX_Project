package com.depositcorex.Main.Entities;



import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Data
@Table(name = "hold_or_lien")
public class HoldOrLien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long holdId; 

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private DepositAccount account; 

    @Column(nullable = false)
    private String holdType; // "HOLD" or "LIEN" [cite: 1]

    @Column(nullable = false)
    private BigDecimal amount;

    private String reason;

    @Column(nullable = false)
    private LocalDateTime placedDate = LocalDateTime.now(); 

    @Column(nullable = false)
    private String status; // "ACTIVE" or "RELEASED" [cite: 1]
}