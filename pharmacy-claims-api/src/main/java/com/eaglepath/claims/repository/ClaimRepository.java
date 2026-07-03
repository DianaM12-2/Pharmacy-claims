package com.eaglepath.claims.repository;

import com.eaglepath.claims.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {

    Optional<Claim> findByClaimId(String claimId);

    List<Claim> findByPatientName(String patientName);

    List<Claim> findByFlaggedForReviewTrue();

    List<Claim> findByStatus(Claim.ClaimStatus status);

    int countByPatientName(String patientName);

    @Query("SELECT c FROM Claim c WHERE c.amount > :threshold")
    List<Claim> findHighValueClaims(Double threshold);

    @Query("SELECT SUM(c.amount) FROM Claim c WHERE c.status = 'APPROVED'")
    Double getTotalApprovedAmount();
}
