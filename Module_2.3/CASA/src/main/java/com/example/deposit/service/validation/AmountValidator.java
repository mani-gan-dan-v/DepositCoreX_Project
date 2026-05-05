package com.example.deposit.service.validation;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.example.deposit.entity.DepositProduct;
import com.example.deposit.exception.ResourceNotFoundException;
import com.example.deposit.exception.ValidationException;

@Component
public class AmountValidator {

    public void validate(BigDecimal principalAmount, DepositProduct product) {

        if (principalAmount == null) {
            throw new ResourceNotFoundException("Principal amount is required");
        }

        if (principalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Principal amount must be greater than zero");
        }

        if (product.getMinAmount() != null &&
                principalAmount.compareTo(product.getMinAmount()) < 0) {

            throw new ValidationException(
                    "Principal amount is less than minimum allowed for this product");
        }

        if (product.getMaxAmount() != null &&
                principalAmount.compareTo(product.getMaxAmount()) > 0) {

            throw new ValidationException(
                    "Principal amount exceeds maximum allowed for this product");
        }
    }
}
