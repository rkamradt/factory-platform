package com.factory.productionplanning.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductionScheduleRequest {

    @NotNull(message = "Schedule name is required")
    private String scheduleName;

    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    private LocalDateTime endDate;

    @NotEmpty(message = "Demand requirements cannot be empty")
    private List<DemandRequirement> demandRequirements;

    private String priority;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DemandRequirement {
        @NotNull(message = "Product ID is required")
        private String productId;

        @NotNull(message = "Quantity is required")
        private Integer quantity;

        private LocalDateTime dueDate;

        private String customerId;
    }
}
