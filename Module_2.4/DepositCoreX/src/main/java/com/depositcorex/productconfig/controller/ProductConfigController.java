package com.depositcorex.productconfig.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.depositcorex.productconfig.dto.SimulationResponse;
import com.depositcorex.productconfig.entity.DepositProduct;
import com.depositcorex.productconfig.service.ProductConfigService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductConfigController {

    @Autowired 
    private ProductConfigService service;

    // CREATE
    @PostMapping
    public DepositProduct createProduct(@RequestBody DepositProduct product) {
        return service.saveProduct(product);
    }

    // READ ALL
    @GetMapping
    public List<DepositProduct> getAll() {
        return service.getAllProducts();
    }

    // READ ONE
    @GetMapping("/{id}")
    public DepositProduct getProduct(@PathVariable Long id) {
        return service.getProductById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public DepositProduct updateProduct(@PathVariable Long id, @RequestBody DepositProduct product) {
        product.setProductID(id); // Ensure we update the correct ID
        return service.saveProduct(product);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return "Product " + id + " has been successfully deleted.";
    }

    // CALCULATE/SIMULATE
    @GetMapping("/{id}/simulate")
    public SimulationResponse simulate(@PathVariable Long id, 
                                       @RequestParam Double amount, 
                                       @RequestParam Integer tenure) {
        return service.simulateMaturity(id, amount, tenure);
    }
}