package com.cts.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customerref")
public class CustomerRef {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerid")
    private Long customerId;

    @Column(name = "cifnumber", length = 50, unique = true)
    private String cifNumber;

    @Column(name = "fullname", length = 200)
    private String fullName;

    @Column(name = "segment", length = 100)
    private String segment;

    @Column(name = "kycstatus", length = 50)
    private String kycStatus;

    @Column(name = "status", length = 30)
    private String status;

    public CustomerRef() {}

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getCifNumber() { return cifNumber; }
    public void setCifNumber(String cifNumber) { this.cifNumber = cifNumber; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getSegment() { return segment; }
    public void setSegment(String segment) { this.segment = segment; }

    public String getKycStatus() { return kycStatus; }
    public void setKycStatus(String kycStatus) { this.kycStatus = kycStatus; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
