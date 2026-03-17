package com.factory.quality.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EscalateFailureResponse {

    private String escalationId;
    private String failureId;
    private String escalationLevel;
    private String status; // ESCALATED, IN_REVIEW, RESOLVED, CLOSED
    private List<String> notifiedParties;
    private List<String> correctiveActions;
    private boolean productionHold;
    private LocalDateTime escalatedAt;
    private String escalatedBy;
}
