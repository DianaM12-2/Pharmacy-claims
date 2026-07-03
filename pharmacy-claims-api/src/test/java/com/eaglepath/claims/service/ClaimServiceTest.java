package com.eaglepath.claims.service;

import com.eaglepath.claims.exception.ClaimNotFoundException;
import com.eaglepath.claims.exception.DuplicateClaimException;
import com.eaglepath.claims.model.Claim;
import com.eaglepath.claims.repository.ClaimRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClaimService Unit Tests")
class ClaimServiceTest {

    @Mock
    private ClaimRepository claimRepository;

    @InjectMocks
    private ClaimService claimService;

    private Claim sampleClaim;

    @BeforeEach
    void setUp() {
        sampleClaim = new Claim("RX-TEST-001", "Maria Garcia", "Lisinopril", 45.00);
    }

    @Test
    @DisplayName("Should return claim when valid ID is provided")
    void getClaimById_validId_returnsClaim() {
        when(claimRepository.findByClaimId("RX-TEST-001")).thenReturn(Optional.of(sampleClaim));
        Claim result = claimService.getClaimById("RX-TEST-001");
        assertNotNull(result);
        assertEquals("RX-TEST-001", result.getClaimId());
        assertEquals("Maria Garcia", result.getPatientName());
    }

    @Test
    @DisplayName("Should throw ClaimNotFoundException when claim does not exist")
    void getClaimById_invalidId_throwsException() {
        when(claimRepository.findByClaimId("RX-INVALID")).thenReturn(Optional.empty());
        assertThrows(ClaimNotFoundException.class, () -> claimService.getClaimById("RX-INVALID"));
    }

    @Test
    @DisplayName("Should throw DuplicateClaimException when claim already exists")
    void createClaim_duplicateId_throwsException() {
        when(claimRepository.findByClaimId("RX-TEST-001")).thenReturn(Optional.of(sampleClaim));
        assertThrows(DuplicateClaimException.class, () -> claimService.createClaim(sampleClaim));
    }

    @Test
    @DisplayName("Should flag claim when amount exceeds $500")
    void createClaim_highValue_flagsForReview() {
        Claim highValueClaim = new Claim("RX-HIGH-001", "Test Patient", "Specialty Drug", 750.00);
        when(claimRepository.findByClaimId("RX-HIGH-001")).thenReturn(Optional.empty());
        when(claimRepository.countByPatientName("Test Patient")).thenReturn(0);
        when(claimRepository.save(any(Claim.class))).thenReturn(highValueClaim);

        Claim result = claimService.createClaim(highValueClaim);
        assertTrue(result.isFlaggedForReview());
        assertEquals(Claim.ClaimStatus.FLAGGED, result.getStatus());
    }

    @Test
    @DisplayName("Should flag claim when patient has 3 or more existing claims")
    void createClaim_duplicatePatient_flagsForReview() {
        Claim newClaim = new Claim("RX-NEW-001", "Maria Garcia", "Metformin", 50.00);
        when(claimRepository.findByClaimId("RX-NEW-001")).thenReturn(Optional.empty());
        when(claimRepository.countByPatientName("Maria Garcia")).thenReturn(3);
        when(claimRepository.save(any(Claim.class))).thenReturn(newClaim);

        claimService.createClaim(newClaim);
        assertTrue(newClaim.isFlaggedForReview());
    }

    @Test
    @DisplayName("Should calculate total amount correctly using Streams")
    void getTotalAmount_returnsCorrectSum() {
        Claim c1 = new Claim("RX-A", "Patient A", "Drug A", 100.0);
        Claim c2 = new Claim("RX-B", "Patient B", "Drug B", 200.0);
        when(claimRepository.findAll()).thenReturn(List.of(c1, c2));

        double total = claimService.getTotalAmount();
        assertEquals(300.0, total, 0.001);
    }

    @Test
    @DisplayName("Should count flagged claims correctly")
    void getFlaggedCount_returnsCorrectCount() {
        Claim flagged = new Claim("RX-F", "Patient", "Drug", 600.0);
        flagged.evaluateForFraud(0);
        Claim normal = new Claim("RX-N", "Patient2", "Drug2", 50.0);
        when(claimRepository.findAll()).thenReturn(List.of(flagged, normal));

        long count = claimService.getFlaggedCount();
        assertEquals(1L, count);
    }
}
