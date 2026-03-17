package com.factory.quality.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleInspectionRequest {

    @NotBlank(message = "Material ID is required")
    private String materialId;

    @NotBlank(message = "Inspection type is required")
    private String inspectionType; // INCOMING, IN_PROCESS, FINAL, etc.

    @NotBlank(message = "Standard ID is required")
    private String standardId;

    private String batchId;

    private String supplierId;

    private LocalDateTime scheduledDate;

    private String priority; // HIGH, MEDIUM, LOW

    private String notes;
}
