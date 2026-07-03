package com.eaglepath.claims.controller;

import com.eaglepath.claims.model.Claim;
import com.eaglepath.claims.service.ClaimService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * REST controller for pharmacy claims.
 * Demonstrates RESTful design, proper HTTP status codes, and OpenAPI documentation.
 */
@RestController
@RequestMapping("/api/v1/claims")
@Tag(name = "Claims", description = "Pharmacy Claims Management API")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @GetMapping
    @Operation(summary = "Get all claims")
    public ResponseEntity<List<Claim>> getAllClaims() {
        return ResponseEntity.ok(claimService.getAllClaims());
    }

    @GetMapping("/{claimId}")
    @Operation(summary = "Get claim by ID")
    public ResponseEntity<Claim> getClaimById(@PathVariable String claimId) {
        return ResponseEntity.ok(claimService.getClaimById(claimId));
    }

    @PostMapping
    @Operation(summary = "Submit a new claim")
    public ResponseEntity<Claim> createClaim(@Valid @RequestBody Claim claim) {
        Claim created = claimService.createClaim(claim);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{claimId}/status")
    @Operation(summary = "Update claim status")
    public ResponseEntity<Claim> updateStatus(
            @PathVariable String claimId,
            @RequestParam Claim.ClaimStatus status) {
        return ResponseEntity.ok(claimService.updateClaimStatus(claimId, status));
    }

    @DeleteMapping("/{claimId}")
    @Operation(summary = "Delete a claim")
    public ResponseEntity<Void> deleteClaim(@PathVariable String claimId) {
        claimService.deleteClaim(claimId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/flagged")
    @Operation(summary = "Get all flagged claims")
    public ResponseEntity<List<Claim>> getFlaggedClaims() {
        return ResponseEntity.ok(claimService.getFlaggedClaims());
    }

    @GetMapping("/patient/{patientName}")
    @Operation(summary = "Get claims by patient name")
    public ResponseEntity<List<Claim>> getByPatient(@PathVariable String patientName) {
        return ResponseEntity.ok(claimService.getClaimsByPatient(patientName));
    }

    @GetMapping("/high-value")
    @Operation(summary = "Get high-value claims above threshold")
    public ResponseEntity<List<Claim>> getHighValueClaims(
            @RequestParam(defaultValue = "500.0") Double threshold) {
        return ResponseEntity.ok(claimService.getHighValueClaims(threshold));
    }

    @GetMapping("/analytics")
    @Operation(summary = "Get claims analytics summary")
    public ResponseEntity<Map<String, Object>> getAnalytics() {
        return ResponseEntity.ok(Map.of(
            "totalClaims",        claimService.getAllClaims().size(),
            "totalAmount",        claimService.getTotalAmount(),
            "averageAmount",      claimService.getAverageClaimAmount(),
            "flaggedCount",       claimService.getFlaggedCount()
        ));
    }
}
