package com.factory.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllocationResponse {

    private String allocationId;
    private String reservationId;
    private String productionOrderId;
    private List<AllocatedMaterial> allocatedMaterials;
    private LocalDateTime allocatedAt;
    private String status;
    private String message;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AllocatedMaterial {
        private String materialId;
        private Double quantity;
        private String unit;
        private String locationId;
    }
}
