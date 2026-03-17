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
public class MachineStatusResponse {

    private String machineId;
    private String status;
    private String machineType;
    private LocalDateTime statusChangedAt;
    private String operatorId;
    private String currentWorkOrderId;
    private Integer estimatedDowntimeMinutes;
    private Boolean maintenanceRequired;
}
