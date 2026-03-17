package com.factory.fabrication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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

    @NotBlank(message = "Operator ID is required")
    private String operatorId;

    @NotBlank(message = "Operator name is required")
    private String operatorName;

    @NotEmpty(message = "At least one work order must be specified")
    private List<String> workOrderIds;

    private String specialization; // WELDING, CUTTING, FORMING, FINISHING

    @NotEmpty(message = "At least one certification is required")
    private List<String> certifications;

    private LocalDateTime assignedAt;

    private LocalDateTime shiftStart;

    private LocalDateTime shiftEnd;

    private String notes;
}
