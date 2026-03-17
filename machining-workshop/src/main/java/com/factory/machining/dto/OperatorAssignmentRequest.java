package com.factory.machining.dto;

import jakarta.validation.constraints.NotBlank;
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
public class OperatorAssignmentRequest {

    @NotBlank(message = "Machine ID is required")
    private String machineId;

    private String workOrderId;
    private LocalDateTime assignmentStartTime;
    private Integer estimatedDurationMinutes;
    private List<String> requiredCertifications;
    private String shiftId;
    private String notes;
}
