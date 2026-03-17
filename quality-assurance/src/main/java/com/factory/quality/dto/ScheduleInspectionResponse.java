package com.factory.quality.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleInspectionResponse {

    private String inspectionId;
    private String materialId;
    private String inspectionType;
    private String standardId;
    private String batchId;
    private String status; // SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED
    private LocalDateTime scheduledDate;
    private String assignedInspector;
    private LocalDateTime createdAt;
}
