package com.factory.compliance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditTrailResponse {

    private List<AuditEntry> entries;
    private Integer totalRecords;
    private Integer page;
    private Integer size;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuditEntry {
        private String logId;
        private String materialId;
        private Double quantity;
        private String sourceLocation;
        private String destinationLocation;
        private String movementType;
        private String transferType;
        private String executedBy;
        private LocalDateTime executedAt;
        private LocalDateTime loggedAt;
        private String authorizationId;
        private String orderId;
        private Map<String, Object> additionalContext;
    }
}
