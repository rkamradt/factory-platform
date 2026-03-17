package com.factory.fabrication.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class UpdateProgressRequest {

    @NotBlank(message = "Current stage is required")
    private String currentStage; // CUTTING, FORMING, WELDING, FINISHING

    @NotNull(message = "Progress percentage is required")
    @Min(value = 0, message = "Progress must be at least 0")
    @Max(value = 100, message = "Progress cannot exceed 100")
    private Integer progressPercentage;

    private String status; // IN_PROGRESS, COMPLETED, ON_HOLD

    private LocalDateTime updatedAt;

    private String notes;

    private Boolean qualityIssueDetected;

    private String qualityIssueDescription;
}
