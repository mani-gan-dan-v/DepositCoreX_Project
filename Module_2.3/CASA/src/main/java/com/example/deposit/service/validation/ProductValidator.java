package com.example.deposit.service.validation;

import org.springframework.stereotype.Component;

import com.example.deposit.entity.DepositProduct;
import com.example.deposit.exception.ResourceNotFoundException;
import com.example.deposit.exception.ValidationException;

@Component
public class ProductValidator {

    public void validateForCasa(DepositProduct product) {

        if (product == null) {
            throw new ResourceNotFoundException("Invalid product id");
        }

        if (!"ACTIVE".equalsIgnoreCase(product.getStatus())) {
            throw new ValidationException("Product is not active");
        }

        if (!"CASA".equalsIgnoreCase(product.getCategory())) {
            throw new ValidationException("Invalid product for CASA account creation");
        }
    }

    public void validateForTermDeposit(DepositProduct product) {

        if (product == null) {
            throw new ResourceNotFoundException("Invalid product id");
        }

        if (!"ACTIVE".equalsIgnoreCase(product.getStatus())) {
            throw new ValidationException("Product is not active");
        }

        String category = product.getCategory();

        if (!("FD".equalsIgnoreCase(category) || "RD".equalsIgnoreCase(category))) {
            throw new ValidationException("Invalid product for Term Deposit creation");
        }
    }
}
