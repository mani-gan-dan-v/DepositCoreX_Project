package com.depositcorex.customer_onboarding_service.service;

import com.depositcorex.customer_onboarding_service.model.CustomerRef;
import com.depositcorex.customer_onboarding_service.repository.CustomerRefRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service

public class CustomerRefService {

    private final CustomerRefRepository repository;

    public CustomerRefService(CustomerRefRepository repository){
        this.repository = repository;
    }



    public CustomerRef saveCustomer(CustomerRef customer) {
        // Check if CIF already exists to prevent duplicates before saving
          repository.findByCifNumber(customer.getCifNumber()).ifPresent(c -> {
            throw new IllegalStateException("Customer with CIF " + customer.getCifNumber() + " already exists.");
        });
        return repository.save(customer);
    }




   //this is get method to get all customers
    public List<CustomerRef> getAllCustomers() {
        return repository.findAll();
    }


    @Cacheable(value = "customers", key = "#cifNumber")
    public CustomerRef getCustomerByCif(String cifNumber) {
        return repository.findByCifNumber(cifNumber)
                .orElseThrow(() -> new EntityNotFoundException("Customer Reference with CIF " + cifNumber + " not found."));
    }
}





