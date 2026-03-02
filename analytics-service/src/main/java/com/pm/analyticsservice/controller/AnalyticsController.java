package com.pm.analyticsservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@RestController
@RequestMapping("/stats")
public class AnalyticsController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${PATIENT_SERVICE_URL:http://localhost:4000}")
    private String patientServiceUrl;

    @Value("${BILLING_SERVICE_URL:http://localhost:4001}")
    private String billingServiceUrl;

    @GetMapping
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        // Fetch patients
        List<?> patients = List.of();
        try {
            ResponseEntity<List> response = restTemplate.getForEntity(patientServiceUrl + "/patients", List.class);
            patients = response.getBody() != null ? response.getBody() : List.of();
        } catch (Exception e) {
            // log error
        }
        int totalPatients = patients.size();
        int activePatients = totalPatients; // For demo, all are active

        // Fetch billing accounts
        List<Map<String, Object>> accounts = List.of();
        double totalRevenue = 0.0;
        try {
            ResponseEntity<List> response = restTemplate.getForEntity(billingServiceUrl + "/accounts", List.class);
            accounts = response.getBody() != null ? response.getBody() : List.of();
            for (Object acc : accounts) {
                if (acc instanceof Map accMap && accMap.get("amount") != null) {
                    try {
                        totalRevenue += Double.parseDouble(accMap.get("amount").toString());
                    } catch (NumberFormatException ignore) {}
                }
            }
        } catch (Exception e) {
            // log error
        }

        stats.put("totalPatients", totalPatients);
        stats.put("activePatients", activePatients);
        stats.put("totalRevenue", totalRevenue);
        stats.put("avgWaitTime", 14);
        stats.put("monthlyAdmissions", 5);
        stats.put("readmissionRate", 0.02);
        return stats;
    }
}
