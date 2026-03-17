package com.factory.machining.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialConsumptionRequest {

    @NotNull(message = "Materials list is required")
    private List<MaterialUsage> materials;

    private String operatorId;
    private String machineId;
    private String notes;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MaterialUsage {
        private String materialId;
        private String materialName;

        @NotNull(message = "Consumed quantity is required")
        @Positive(message = "Consumed quantity must be positive")
        private Double consumedQuantity;

        private Double scrapQuantity;
        private String unit;
        private String scrapReason;
    }
}
