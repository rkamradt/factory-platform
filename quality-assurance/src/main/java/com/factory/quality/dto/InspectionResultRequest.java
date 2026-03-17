package com.factory.quality.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InspectionResultRequest {

    @NotBlank(message = "Result status is required")
    private String result; // PASS, FAIL, CONDITIONAL_PASS

    @NotBlank(message = "Inspector ID is required")
    private String inspectorId;

    @NotNull(message = "Measurements are required")
    private Map<String, Object> measurements;

    private List<String> defects;

    private String notes;

    private String correctionRequired; // NONE, REWORK, SCRAP, RETURN_TO_SUPPLIER

    private Map<String, Object> testResults;
}
