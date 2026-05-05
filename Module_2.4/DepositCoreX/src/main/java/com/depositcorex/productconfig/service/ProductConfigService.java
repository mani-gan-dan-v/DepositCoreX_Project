
package com.depositcorex.productconfig.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depositcorex.productconfig.constants.InterestMethod;
import com.depositcorex.productconfig.dto.SimulationResponse;
import com.depositcorex.productconfig.entity.DepositProduct;
import com.depositcorex.productconfig.entity.InterestTable;
import com.depositcorex.productconfig.repository.DepositProductRepository;

@Service
public class ProductConfigService {

    @Autowired 
    private DepositProductRepository productRepository;

    @Transactional
    public DepositProduct saveProduct(DepositProduct product) {
        // Null-safety check for required fields
        if (product.getMinAmount() == null || product.getMaxAmount() == null) {
            throw new RuntimeException("Validation Error: minAmount and maxAmount are required.");
        }

        if (product.getMinAmount() > product.getMaxAmount()) {
            throw new RuntimeException("Validation Error: MinAmount cannot be greater than MaxAmount");
        }

        // Maintain bi-directional relationship for JPA Cascade
        if (product.getInterestSlabs() != null) {
            product.getInterestSlabs().forEach(s -> s.setProduct(product));
        }
        if (product.getChargeRules() != null) {
            product.getChargeRules().forEach(c -> c.setProduct(product));
        }
        
        return productRepository.save(product);
    }

    public List<DepositProduct> getAllProducts() {
        return productRepository.findAll();
    }

    public DepositProduct getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete: Product not found with ID " + id);
        }
        productRepository.deleteById(id);
    }

    public SimulationResponse simulateMaturity(Long productId, Double amount, Integer tenureMonths) {
        DepositProduct product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product Not Found"));

        // Interest Lookup Logic
        Double rate = product.getInterestSlabs().stream()
                .filter(s -> tenureMonths >= s.getTenureFrom() && tenureMonths <= s.getTenureTo())
                .map(InterestTable::getRate)
                .findFirst()
                .orElse(0.0);

        Double maturity;
        double t = tenureMonths / 12.0; 
        double r = rate / 100.0;

        if (product.getInterestMethod() == InterestMethod.SIMPLE) {
            maturity = amount * (1 + (r * t));
        } else {
            int n = 4; // Quarterly
            maturity = amount * Math.pow((1 + r/n), (n * t));
        }

        return new SimulationResponse(amount, rate, (maturity - amount), maturity, product.getInterestMethod().name());
    }
}