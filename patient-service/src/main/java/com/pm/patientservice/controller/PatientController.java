import org.springframework.web.bind.annotation.RequestHeader;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
  @GetMapping("/me")
  @Operation(summary = "Get current user's patient record")
  public ResponseEntity<PatientResponseDTO> getMyPatient(@RequestHeader("Authorization") String authHeader) {
    // Extract JWT token
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return ResponseEntity.status(401).build();
    }
    String token = authHeader.substring(7);
    String secret = System.getenv("JWT_SECRET");
    if (secret == null) {
      // fallback to hardcoded or config value if needed
      secret = "fyfgPnukEBCqnVS0FC6qNrQs+6FVaKIFF9IY6woS0V5r7O7dXhOkmDoO6Lg1I3f6hVAH29q4xLb+DTsLzzesXg==";
    }
    String email;
    try {
      Claims claims = Jwts.parser()
        .setSigningKey(secret.getBytes())
        .parseClaimsJws(token)
        .getBody();
      email = claims.getSubject();
    } catch (Exception e) {
      return ResponseEntity.status(401).build();
    }
    PatientResponseDTO patient = patientService.getPatientByEmail(email);
    if (patient == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(patient);
  }
package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import com.pm.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "API for managing Patients")
public class PatientController {

  private final PatientService patientService;

  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }

  @GetMapping
  @Operation(summary = "Get Patients")
  public ResponseEntity<List<PatientResponseDTO>> getPatients() {
    List<PatientResponseDTO> patients = patientService.getPatients();
    return ResponseEntity.ok().body(patients);
  }

  @PostMapping
  @Operation(summary = "Create a new Patient")
  public ResponseEntity<PatientResponseDTO> createPatient(
      @Validated({Default.class, CreatePatientValidationGroup.class})
      @RequestBody PatientRequestDTO patientRequestDTO) {

    PatientResponseDTO patientResponseDTO = patientService.createPatient(
        patientRequestDTO);

    return ResponseEntity.ok().body(patientResponseDTO);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update a new Patient")
  public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id,
      @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {

    PatientResponseDTO patientResponseDTO = patientService.updatePatient(id,
        patientRequestDTO);

    return ResponseEntity.ok().body(patientResponseDTO);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a Patient")
  public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
    patientService.deletePatient(id);
    return ResponseEntity.noContent().build();
  }
}
