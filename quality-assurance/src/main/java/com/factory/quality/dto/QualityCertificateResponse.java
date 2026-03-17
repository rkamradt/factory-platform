package com.factory.quality.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QualityCertificateResponse {

    private String certificateId;
    private String batchId;
    private String materialId;
    private String materialDescription;
    private String certificateNumber;
    private String status; // ISSUED, REVOKED, EXPIRED
    private LocalDateTime issuedDate;
    private LocalDateTime expiryDate;
    private String issuedBy;
    private List<String> inspectionIds;
    private Map<String, Object> certifiedSpecifications;
    private Map<String, Object> testResults;
    private String complianceStandards;
    private String notes;
}
