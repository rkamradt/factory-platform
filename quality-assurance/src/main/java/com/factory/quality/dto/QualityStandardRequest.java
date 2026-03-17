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
public class QualityStandardRequest {

    @NotBlank(message = "Standard name is required")
    private String standardName;

    @NotBlank(message = "Material type is required")
    private String materialType;

    @NotNull(message = "Specifications are required")
    private Map<String, Object> specifications;

    @NotNull(message = "Inspection procedures are required")
    private List<InspectionProcedure> procedures;

    private String version;

    private String description;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InspectionProcedure {
        private String procedureName;
        private String description;
        private Map<String, Object> acceptanceCriteria;
        private List<String> requiredEquipment;
        private String testMethod;
    }
}
