package com.factory.fabrication.dto;

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
public class OperatorAssignmentResponse {

    private String operatorId;
    private String operatorName;
    private String specialization;
    private List<String> certifications;
    private List<String> assignedWorkOrderIds;
    private LocalDateTime assignedAt;
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    private String status; // ASSIGNED, ACTIVE, COMPLETED
    private String notes;
}
