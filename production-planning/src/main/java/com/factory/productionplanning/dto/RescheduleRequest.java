package com.factory.productionplanning.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RescheduleRequest {

    @NotNull(message = "Reason is required")
    private String reason; // DELAY, CAPACITY_CONSTRAINT, MATERIAL_SHORTAGE, QUALITY_ISSUE

    private LocalDateTime newStartDate;

    private LocalDateTime newEndDate;

    private List<String> affectedProductionOrders;

    private String priority;

    private String notes;
}
