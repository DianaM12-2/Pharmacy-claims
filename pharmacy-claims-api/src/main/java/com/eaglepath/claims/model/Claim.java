package com.eaglepath.claims.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * Represents a pharmacy claim in the system.
 * Demonstrates OOP, JPA entity mapping, and Bean Validation.
 */
@Entity
@Table(name = "claims")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Claim ID is required")
    @Column(unique = true, nullable = false)
    private String claimId;

    @NotBlank(message = "Patient name is required")
    private String patientName;

    @NotBlank(message = "Medication name is required")
    private String medication;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Double amount;

    @Enumerated(EnumType.STRING)
    private ClaimStatus status = ClaimStatus.PENDING;

    private boolean flaggedForReview = false;

    private String flagReason;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    // Enums
    public enum ClaimStatus {
        PENDING, APPROVED, REJECTED, FLAGGED
    }

    // Constructors
    public Claim() {}

    public Claim(String claimId, String patientName, String medication, Double amount) {
        this.claimId = claimId;
        this.patientName = patientName;
        this.medication = medication;
        this.amount = amount;
    }

    // Business logic — flag high-value claims
    public void evaluateForFraud(int existingClaimsCount) {
        if (this.amount > 500.0) {
            this.flaggedForReview = true;
            this.flagReason = "High-value claim exceeds $500 threshold";
            this.status = ClaimStatus.FLAGGED;
        }
        if (existingClaimsCount >= 3) {
            this.flaggedForReview = true;
            this.flagReason = "Duplicate claim detected: patient has " + existingClaimsCount + " existing claims";
            this.status = ClaimStatus.FLAGGED;
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getClaimId() { return claimId; }
    public void setClaimId(String claimId) { this.claimId = claimId; }
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public String getMedication() { return medication; }
    public void setMedication(String medication) { this.medication = medication; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public ClaimStatus getStatus() { return status; }
    public void setStatus(ClaimStatus status) { this.status = status; }
    public boolean isFlaggedForReview() { return flaggedForReview; }
    public void setFlaggedForReview(boolean flaggedForReview) { this.flaggedForReview = flaggedForReview; }
    public String getFlagReason() { return flagReason; }
    public void setFlagReason(String flagReason) { this.flagReason = flagReason; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
