package com.factory.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReconcileInventoryResponse {

    private String reconciliationId;
    private String locationId;
    private LocalDateTime reconciledAt;
    private String countedBy;
    private Integer totalDiscrepancies;
    private List<DiscrepancyDetail> discrepancies;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DiscrepancyDetail {
        private String materialId;
        private Double systemCount;
        private Double physicalCount;
        private Double variance;
        private String unit;
        private String action; // ADJUSTED, PENDING_REVIEW, IGNORED
    }
}
