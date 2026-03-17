package com.factory.quality.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EscalateFailureRequest {

    @NotBlank(message = "Escalation level is required")
    private String escalationLevel; // SUPERVISOR, QUALITY_MANAGER, PRODUCTION_MANAGER, EXECUTIVE

    @NotBlank(message = "Reason is required")
    private String reason;

    private List<String> suggestedActions;

    private String impact; // CRITICAL, HIGH, MEDIUM, LOW

    private String rootCauseAnalysis;

    private boolean productionHold;
}
