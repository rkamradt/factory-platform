package com.factory.machining.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MachineStatusRequest {

    @NotBlank(message = "Status is required")
    private String status; // OPERATIONAL, IDLE, SETUP, MAINTENANCE, BREAKDOWN

    private String operatorId;
    private String workOrderId;
    private String downtimeReason;
    private Integer estimatedDowntimeMinutes;
    private String maintenanceType; // PREVENTIVE, CORRECTIVE, EMERGENCY
    private String notes;
}
