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
public class ProductionOrderResponse {

    private String productionOrderId;
    private String productId;
    private Integer quantity;
    private String bomId;
    private String bomVersion;
    private String status;
    private LocalDateTime dueDate;
    private String priority;
    private String customerId;
    private String scheduleId;
    private String reservationId;
    private List<MaterialRequirement> materialRequirements;
    private LocalDateTime createdAt;
    private String message;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MaterialRequirement {
        private String materialId;
        private String materialName;
        private Double requiredQuantity;
        private String unit;
        private boolean reserved;
    }
}
