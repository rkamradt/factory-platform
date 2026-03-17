package com.factory.quality.controller;

import com.factory.quality.dto.*;
import com.factory.quality.service.CertificationService;
import com.factory.quality.service.FailureEscalationService;
import com.factory.quality.service.QualityHistoryService;
import com.factory.quality.service.QualityStandardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quality")
public class QualityController {

    private final QualityHistoryService historyService;
    private final QualityStandardService standardService;
    private final CertificationService certificationService;
    private final FailureEscalationService escalationService;

    public QualityController(QualityHistoryService historyService,
                           QualityStandardService standardService,
                           CertificationService certificationService,
                           FailureEscalationService escalationService) {
        this.historyService = historyService;
        this.standardService = standardService;
        this.certificationService = certificationService;
        this.escalationService = escalationService;
    }

    /**
     * Query quality history and defect patterns for a material
     * GET /quality/history/{materialId}
     */
    @GetMapping("/history/{materialId}")
    public ResponseEntity<QualityHistoryResponse> getQualityHistory(@PathVariable String materialId) {
        QualityHistoryResponse response = historyService.getQualityHistory(materialId);
        return ResponseEntity.ok(response);
    }

    /**
     * Create or update quality standards and inspection procedures
     * POST /quality/standards
     */
    @PostMapping("/standards")
    public ResponseEntity<QualityStandardResponse> createQualityStandard(
            @Valid @RequestBody QualityStandardRequest request) {
        QualityStandardResponse response = standardService.createOrUpdateStandard(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get quality standard by ID
     * GET /quality/standards/{standardId}
     */
    @GetMapping("/standards/{standardId}")
    public ResponseEntity<QualityStandardResponse> getQualityStandard(@PathVariable String standardId) {
        QualityStandardResponse response = standardService.getStandard(standardId);
        return ResponseEntity.ok(response);
    }

    /**
     * Generate quality certificates for compliant batches
     * GET /quality/certificates/{batchId}
     */
    @GetMapping("/certificates/{batchId}")
    public ResponseEntity<QualityCertificateResponse> getCertificate(@PathVariable String batchId) {
        QualityCertificateResponse response = certificationService.getCertificateForBatch(batchId);
        return ResponseEntity.ok(response);
    }

    /**
     * Issue quality certificate for a batch (POST endpoint for certificate generation)
     * POST /quality/certificates/{batchId}/issue
     */
    @PostMapping("/certificates/{batchId}/issue")
    public ResponseEntity<QualityCertificateResponse> issueCertificate(@PathVariable String batchId) {
        QualityCertificateResponse response = certificationService.generateCertificate(batchId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Escalate quality failures and trigger corrective actions
     * POST /quality/failures/{failureId}/escalate
     */
    @PostMapping("/failures/{failureId}/escalate")
    public ResponseEntity<EscalateFailureResponse> escalateFailure(
            @PathVariable String failureId,
            @Valid @RequestBody EscalateFailureRequest request) {
        EscalateFailureResponse response = escalationService.escalateFailure(failureId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get escalation details
     * GET /quality/failures/escalations/{escalationId}
     */
    @GetMapping("/failures/escalations/{escalationId}")
    public ResponseEntity<EscalateFailureResponse> getEscalation(@PathVariable String escalationId) {
        EscalateFailureResponse response = escalationService.getEscalation(escalationId);
        return ResponseEntity.ok(response);
    }
}
