package com.cts.project.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "depositproduct")
public class DepositProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productid")
    private Long productId;

    @Column(name = "productname", length = 150, nullable = false, unique = true)
    private String productName;

    @Column(name = "category", length = 80)
    private String category;

    @Column(name = "minamount", precision = 19, scale = 4)
    private BigDecimal minAmount;

    @Column(name = "maxamount", precision = 19, scale = 4)
    private BigDecimal maxAmount;

    @Column(name = "mintenure")
    private Integer minTenure;

    @Column(name = "maxtenure")
    private Integer maxTenure;

    @Column(name = "interestmethod", length = 50)
    private String interestMethod;

    @Column(name = "status", length = 30)
    private String status;

    public DepositProduct() {}

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public BigDecimal getMinAmount() { return minAmount; }
    public void setMinAmount(BigDecimal minAmount) { this.minAmount = minAmount; }

    public BigDecimal getMaxAmount() { return maxAmount; }
    public void setMaxAmount(BigDecimal maxAmount) { this.maxAmount = maxAmount; }

    public Integer getMinTenure() { return minTenure; }
    public void setMinTenure(Integer minTenure) { this.minTenure = minTenure; }

    public Integer getMaxTenure() { return maxTenure; }
    public void setMaxTenure(Integer maxTenure) { this.maxTenure = maxTenure; }

    public String getInterestMethod() { return interestMethod; }
    public void setInterestMethod(String interestMethod) { this.interestMethod = interestMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
