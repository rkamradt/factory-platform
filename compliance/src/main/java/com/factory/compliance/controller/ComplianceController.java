package com.factory.compliance.controller;

import com.factory.compliance.dto.*;
import com.factory.compliance.service.ComplianceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compliance")
public class ComplianceController {

    private final ComplianceService complianceService;

    public ComplianceController(ComplianceService complianceService) {
        this.complianceService = complianceService;
    }

    /**
     * Authorize material transfer request (currently always approves)
     * POST /compliance/authorize-transfer
     */
    @PostMapping("/authorize-transfer")
    public ResponseEntity<AuthorizeTransferResponse> authorizeTransfer(
            @Valid @RequestBody AuthorizeTransferRequest request) {
        AuthorizeTransferResponse response = complianceService.authorizeTransfer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Log completed material movement with full context
     * POST /compliance/log-movement
     */
    @PostMapping("/log-movement")
    public ResponseEntity<LogMovementResponse> logMovement(
            @Valid @RequestBody LogMovementRequest request) {
        LogMovementResponse response = complianceService.logMovement(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Query audit trail by material, location, time range, or transfer type
     * GET /compliance/audit-trail
     */
    @GetMapping("/audit-trail")
    public ResponseEntity<AuditTrailResponse> getAuditTrail(
            @RequestParam(required = false) String materialId,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String movementType,
            @RequestParam(required = false) String transferType,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(required = false) String orderId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {

        AuditTrailQuery query = AuditTrailQuery.builder()
                .materialId(materialId)
                .location(location)
                .movementType(movementType)
                .transferType(transferType)
                .orderId(orderId)
                .page(page)
                .size(size)
                .build();

        AuditTrailResponse response = complianceService.getAuditTrail(query);
        return ResponseEntity.ok(response);
    }

    /**
     * Validate transfer request against current compliance rules
     * POST /compliance/validate-transfer
     */
    @PostMapping("/validate-transfer")
    public ResponseEntity<ValidateTransferResponse> validateTransfer(
            @Valid @RequestBody ValidateTransferRequest request) {
        ValidateTransferResponse response = complianceService.validateTransfer(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Generate compliance reports for auditing purposes
     * GET /compliance/reports/{type}
     */
    @GetMapping("/reports/{type}")
    public ResponseEntity<ComplianceReportResponse> generateReport(
            @PathVariable String type,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        ComplianceReportResponse response = complianceService.generateReport(type, startDate, endDate);
        return ResponseEntity.ok(response);
    }
}
