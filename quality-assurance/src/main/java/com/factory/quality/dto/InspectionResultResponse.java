package com.factory.quality.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InspectionResultResponse {

    private String inspectionId;
    private String materialId;
    private String batchId;
    private String result;
    private String inspectorId;
    private Map<String, Object> measurements;
    private List<String> defects;
    private String correctionRequired;
    private LocalDateTime completedAt;
    private boolean certificateIssued;
}
