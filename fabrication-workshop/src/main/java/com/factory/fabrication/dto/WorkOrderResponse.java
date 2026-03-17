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
public class WorkOrderResponse {

    private String workOrderId;
    private String productionOrderId;
    private String status; // PENDING, ACCEPTED, SCHEDULED, IN_PROGRESS, COMPLETED, ON_HOLD
    private String currentStage; // CUTTING, FORMING, WELDING, FINISHING
    private String assignedTeam;
    private String equipmentId;
    private LocalDateTime acceptedAt;
    private LocalDateTime scheduledAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private Integer progressPercentage;
    private List<MaterialConsumption> materials;
    private List<String> operators;
    private String notes;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MaterialConsumption {
        private String materialId;
        private String materialName;
        private Double plannedQuantity;
        private Double consumedQuantity;
        private Double wasteQuantity;
        private String unit;
    }
}
