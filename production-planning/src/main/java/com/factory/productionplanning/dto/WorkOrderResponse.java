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
public class WorkOrderResponse {

    private String workOrderId;
    private String productionOrderId;
    private String workshopType;
    private String operationType;
    private String componentId;
    private Integer quantity;
    private String status;
    private LocalDateTime scheduledStartDate;
    private LocalDateTime scheduledEndDate;
    private LocalDateTime dueDate;
    private String assignedWorkshop;
    private List<String> requiredMaterials;
    private LocalDateTime createdAt;
    private String message;
}
