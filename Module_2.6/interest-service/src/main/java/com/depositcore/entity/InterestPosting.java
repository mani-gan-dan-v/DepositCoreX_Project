package com.depositcore.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="interest_posting")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterestPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long postingId;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private LocalDateTime postingDate;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private String postingType;  //CASA /FD/ RD


}
