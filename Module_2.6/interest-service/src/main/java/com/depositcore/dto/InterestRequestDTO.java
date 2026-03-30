package com.depositcore.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterestRequestDTO {

    @NotNull(message = "Account ID cannot be null")
    private Long accountId;

    @NotNull(message = "Principal cannot be null")
    @Positive(message = "Principal must be greater than 0")
    private BigDecimal principal;

    @NotNull(message = "Days cannot be null")
    @Positive(message = "Days must be greater than 0")
    private Integer days;
}