package com.factory.productionplanning.dto;

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
public class FeasibilityCheckResponse {

    private String productionOrderId;
    private boolean feasible;
    private String status;
    private CapacityFeasibility capacityFeasibility;
    private MaterialFeasibility materialFeasibility;
    private List<String> constraints;
    private LocalDateTime earliestStartDate;
    private LocalDateTime estimatedCompletionDate;
    private String message;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CapacityFeasibility {
        private boolean available;
        private Double utilizationPercentage;
        private List<String> bottlenecks;
        private String message;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MaterialFeasibility {
        private boolean available;
        private List<MaterialShortage> shortages;
        private String message;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MaterialShortage {
        private String materialId;
        private String materialName;
        private Double requiredQuantity;
        private Double availableQuantity;
        private Double shortageQuantity;
        private String unit;
    }
}
