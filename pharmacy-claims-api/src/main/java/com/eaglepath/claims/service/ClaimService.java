package com.eaglepath.claims.service;

import com.eaglepath.claims.exception.ClaimNotFoundException;
import com.eaglepath.claims.exception.DuplicateClaimException;
import com.eaglepath.claims.model.Claim;
import com.eaglepath.claims.repository.ClaimRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Service layer containing all business logic for claim processing.
 * Demonstrates OOP, Streams, Lambda expressions, and transaction management.
 */
@Service
@Transactional
public class ClaimService {

    private final ClaimRepository claimRepository;

    public ClaimService(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    public Claim getClaimById(String claimId) {
        return claimRepository.findByClaimId(claimId)
                .orElseThrow(() -> new ClaimNotFoundException("Claim not found: " + claimId));
    }

    public Claim createClaim(Claim claim) {
        // Check for duplicate
        if (claimRepository.findByClaimId(claim.getClaimId()).isPresent()) {
            throw new DuplicateClaimException("Claim already exists: " + claim.getClaimId());
        }

        // Run fraud evaluation
        int existingCount = claimRepository.countByPatientName(claim.getPatientName());
        claim.evaluateForFraud(existingCount);

        return claimRepository.save(claim);
    }

    public Claim updateClaimStatus(String claimId, Claim.ClaimStatus newStatus) {
        Claim claim = getClaimById(claimId);
        claim.setStatus(newStatus);
        return claimRepository.save(claim);
    }

    public void deleteClaim(String claimId) {
        Claim claim = getClaimById(claimId);
        claimRepository.delete(claim);
    }

    public List<Claim> getFlaggedClaims() {
        return claimRepository.findByFlaggedForReviewTrue();
    }

    public List<Claim> getClaimsByPatient(String patientName) {
        return claimRepository.findByPatientName(patientName);
    }

    public List<Claim> getHighValueClaims(Double threshold) {
        return claimRepository.findHighValueClaims(threshold);
    }

    // Analytics using Streams + Lambdas
    public double getTotalAmount() {
        return getAllClaims().stream()
                .mapToDouble(Claim::getAmount)
                .sum();
    }

    public long getFlaggedCount() {
        return getAllClaims().stream()
                .filter(Claim::isFlaggedForReview)
                .count();
    }

    public double getAverageClaimAmount() {
        return getAllClaims().stream()
                .mapToDouble(Claim::getAmount)
                .average()
                .orElse(0.0);
    }
}
