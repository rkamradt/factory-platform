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
public class ProductionOrderStatusResponse {

    private String productionOrderId;
    private String productId;
    private Integer quantity;
    private String status; // CREATED, SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED, ON_HOLD
    private Integer completedQuantity;
    private Double progressPercentage;
    private LocalDateTime createdAt;
    private LocalDateTime scheduledStartDate;
    private LocalDateTime scheduledEndDate;
    private LocalDateTime actualStartDate;
    private LocalDateTime actualCompletionDate;
    private LocalDateTime dueDate;
    private List<WorkOrderStatus> workOrders;
    private List<String> issues;
    private String message;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class WorkOrderStatus {
        private String workOrderId;
        private String workshopType;
        private String operationType;
        private String status;
        private Integer quantity;
        private Integer completedQuantity;
        private LocalDateTime scheduledStartDate;
        private LocalDateTime scheduledEndDate;
    }
}
