package com.factory.machining.dto;

import jakarta.validation.constraints.NotNull;
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
public class ScheduleWorkOrderRequest {

    private String machineId; // Specific machine or null for general pool
    private String machineType; // CNC, LATHE, MILL
    private LocalDateTime scheduledStartTime;
    private Integer estimatedDurationMinutes;
    private List<String> operatorIds;
    private String notes;
}
