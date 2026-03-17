package com.factory.assembly.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignOperatorResponse {

    private String operatorId;
    private String workstationId;
    private String taskType;
    private String assignmentStatus;
    private LocalDateTime assignedAt;
}
