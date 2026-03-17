package com.factory.bom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResponse {

    private String bomId;
    private Boolean isValid;
    private List<ValidationIssue> issues;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ValidationIssue {
        private String severity; // ERROR, WARNING, INFO
        private String issueType; // CIRCULAR_DEPENDENCY, MISSING_COMPONENT, INCOMPLETE_SPECIFICATION
        private String description;
        private List<String> affectedComponents;
    }
}
