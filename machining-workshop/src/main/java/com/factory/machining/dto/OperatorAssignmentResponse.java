package com.factory.machining.dto;

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

    private String assignmentId;
    private String operatorId;
    private String operatorName;
    private String machineId;
    private String workOrderId;
    private String status; // ASSIGNED, ACTIVE, COMPLETED
    private LocalDateTime assignedAt;
    private LocalDateTime estimatedEndTime;
    private List<String> certifications;
}
