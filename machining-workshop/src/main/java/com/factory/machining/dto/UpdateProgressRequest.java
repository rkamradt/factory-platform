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
public class UpdateProgressRequest {

    @NotBlank(message = "Stage is required")
    private String stage; // SETUP, MACHINING, INSPECTION, COMPLETED

    private Integer completedQuantity;
    private Integer rejectedQuantity;
    private String operatorId;
    private String machineId;
    private String notes;
    private String qualityStatus; // PASS, FAIL, PENDING
}
