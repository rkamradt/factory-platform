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
public class QualityHistoryResponse {

    private String materialId;
    private int totalInspections;
    private int passedInspections;
    private int failedInspections;
    private double passRate;
    private List<InspectionSummary> inspectionHistory;
    private Map<String, Integer> defectPatterns;
    private List<String> commonDefects;
    private Map<String, Object> trendAnalysis;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InspectionSummary {
        private String inspectionId;
        private String batchId;
        private String result;
        private LocalDateTime inspectionDate;
        private List<String> defects;
    }
}
