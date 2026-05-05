package com.depositcorex.customer_onboarding_service.controller;

import com.depositcorex.customer_onboarding_service.model.CustomerRef;
import com.depositcorex.customer_onboarding_service.service.CustomerRefService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customer-reference")
@RequiredArgsConstructor

public class CustomerRefController {

    private final CustomerRefService service;





    @Transactional
    @PostMapping("/sync")
    public ResponseEntity<CustomerRef> syncCustomer(@Valid @RequestBody CustomerRef customer) {
        CustomerRef savedCustomer = service.saveCustomer(customer);
        // Changed from 'new ResponseEntity' to the fluent style
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }


    @GetMapping("/view")
    public ResponseEntity<List<CustomerRef>> getAll() {
        return ResponseEntity.ok(service.getAllCustomers());
    }


    @GetMapping("/{cifNumber}")
    public ResponseEntity<CustomerRef> getByCif(@PathVariable String cifNumber) {
        return ResponseEntity.ok(service.getCustomerByCif(cifNumber));
    }

    @GetMapping("/hello")
    public String hello(){
        return "Service is UP";
    }
}



