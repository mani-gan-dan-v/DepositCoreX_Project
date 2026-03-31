package com.depositcore.entity;
import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import com.depositcore.entity.PostingType;

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
    @Enumerated(EnumType.STRING)
    private PostingType postingType;  //CASA /FD/ RD


}
