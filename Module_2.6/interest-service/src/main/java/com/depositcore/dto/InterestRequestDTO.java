package com.depositcore.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterestRequestDTO {

    private Long accountId;
    private BigDecimal principal;
    private BigDecimal rate;
    private int days;
}