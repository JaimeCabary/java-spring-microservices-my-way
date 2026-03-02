package com.pm.billingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDate;

@Entity
public class BillingAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String patientId;
    private String patientName;
    private String status;
    private double amount;
    private String createdAt;

    public BillingAccount() {}

    public BillingAccount(String id, String patientId, String patientName, String status, double amount, String createdAt) {
        this.id = id;
        this.patientId = patientId;
        this.patientName = patientName;
        this.status = status;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPatientId() {
        return patientId;
    }
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    public String getPatientName() {
        return patientName;
    }
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
