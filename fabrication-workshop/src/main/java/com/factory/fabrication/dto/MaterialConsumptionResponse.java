package com.factory.fabrication.dto;

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
public class MaterialConsumptionResponse {

    private String workOrderId;
    private String stage;
    private LocalDateTime consumedAt;
    private List<MaterialConsumption> materials;
    private Double totalWasteRate; // percentage
    private String notes;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MaterialConsumption {
        private String materialId;
        private String materialName;
        private Double consumedQuantity;
        private Double wasteQuantity;
        private Double reworkQuantity;
        private String unit;
        private Double wasteRate; // percentage
    }
}
