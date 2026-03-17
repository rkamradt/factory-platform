package com.factory.fabrication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentStatusRequest {

    @NotBlank(message = "Equipment ID is required")
    private String equipmentId;

    @NotNull(message = "Status is required")
    private String status; // OPERATIONAL, DOWN, MAINTENANCE, STANDBY

    private String equipmentType; // CUTTING, FORMING, WELDING, FINISHING

    private LocalDateTime statusChangedAt;

    private String reason; // breakdown, scheduled maintenance, etc.

    private LocalDateTime expectedAvailableAt;

    private String maintenanceType; // PREVENTIVE, CORRECTIVE, EMERGENCY

    private String reportedBy;

    private String notes;
}
