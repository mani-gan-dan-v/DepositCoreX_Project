package com.depositcore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="interest_accrual")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterestAccrual{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long accrualId;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private LocalDate periodStart;

    @Column(nullable = false)
    private LocalDate periodEnd;

    @Column(nullable = false, precision = 15, scale=2)
    private BigDecimal interestAmount;

    @Column(nullable = false)
    private LocalDateTime calculatedDate;
}