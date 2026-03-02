package com.pm.billingservice.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/accounts")
public class BillingAccountController {

    private final List<BillingAccount> accounts = new ArrayList<>(List.of(
        new BillingAccount("1", "p1", "John Doe", "Active", 120.0, "2026-03-02"),
        new BillingAccount("2", "p2", "Jane Smith", "Pending", 80.0, "2026-03-01")
    ));

    @GetMapping
    public List<BillingAccount> getAllAccounts() {
        return accounts;
    }

    @PostMapping
    public BillingAccount createAccount(@RequestBody BillingAccountRequest req) {
        String id = UUID.randomUUID().toString();
        BillingAccount account = new BillingAccount(
            id,
            req.patientId,
            req.patientName,
            "Active",
            req.amount,
            java.time.LocalDate.now().toString()
        );
        accounts.add(account);
        return account;
    }

    static class BillingAccountRequest {
        public String patientId;
        public String patientName;
        public double amount;
    }

    // Example BillingAccount class (replace with your real model)
    static class BillingAccount {
        public String id;
        public String patientId;
        public String patientName;
        public String status;
        public double amount;
        public String createdAt;

        public BillingAccount(String id, String patientId, String patientName, String status, double amount, String createdAt) {
            this.id = id;
            this.patientId = patientId;
            this.patientName = patientName;
            this.status = status;
            this.amount = amount;
            this.createdAt = createdAt;
        }
    }
}
