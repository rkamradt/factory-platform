package com.factory.machining.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleWorkOrderResponse {

    private String workOrderId;
    private String orderId;
    private String status; // SCHEDULED
    private String machineId;
    private String machineType;
    private LocalDateTime scheduledStartTime;
    private LocalDateTime scheduledEndTime;
    private Integer estimatedDurationMinutes;
}
