package com.factory.assembly.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProgressRequest {

    @NotBlank(message = "Build stage is required")
    private String buildStage;

    private Integer completedUnits;

    private String currentWorkstation;

    private String notes;

    private Double progressPercentage;
}
