package com.depositcore.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterestResponseDTO {

    private Long accountId;
    private BigDecimal interestAmount;
    private String message;
}