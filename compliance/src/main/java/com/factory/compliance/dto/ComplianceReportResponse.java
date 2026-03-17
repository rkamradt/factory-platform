package com.factory.compliance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceReportResponse {

    private String reportId;
    private String reportType; // e.g., "DAILY", "WEEKLY", "MONTHLY", "TRANSFER_SUMMARY", "VIOLATION_REPORT"
    private LocalDateTime generatedAt;
    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;
    private Map<String, Object> summary;
    private Map<String, Object> details;
    private Map<String, Object> statistics;
}
