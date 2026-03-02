package com.pm.billingservice.controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.pm.billingservice.model.BillingAccount;
import com.pm.billingservice.repository.BillingAccountRepository;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class BillingAccountController {

    private final BillingAccountRepository billingAccountRepository;

    @Autowired
    public BillingAccountController(BillingAccountRepository billingAccountRepository) {
        this.billingAccountRepository = billingAccountRepository;
    }

    @GetMapping
    public List<BillingAccount> getAllAccounts() {
        return billingAccountRepository.findAll();
    }

    @PostMapping
    public BillingAccount createAccount(@RequestBody BillingAccountRequest req) {
        BillingAccount account = new BillingAccount(
            null,
            req.patientId,
            req.patientName,
            "Active",
            req.amount,
            java.time.LocalDate.now().toString()
        );
        return billingAccountRepository.save(account);
    }

    public static class BillingAccountRequest {
        public String patientId;
        public String patientName;
        public double amount;
    }
}
