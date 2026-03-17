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
public class ProductionScheduleResponse {

    private String scheduleId;
    private String scheduleName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private List<ScheduledProduction> scheduledProductions;
    private LocalDateTime createdAt;
    private String message;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ScheduledProduction {
        private String productionOrderId;
        private String productId;
        private Integer quantity;
        private LocalDateTime scheduledStartDate;
        private LocalDateTime scheduledEndDate;
        private String assignedResource;
    }
}
