package com.factory.quality.service;

import com.factory.quality.dto.InspectionResultResponse;
import com.factory.quality.dto.QualityHistoryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QualityHistoryService {

    private static final Logger logger = LoggerFactory.getLogger(QualityHistoryService.class);
    private final InspectionService inspectionService;

    public QualityHistoryService(InspectionService inspectionService) {
        this.inspectionService = inspectionService;
    }

    /**
     * Query quality history and defect patterns for a material
     */
    public QualityHistoryResponse getQualityHistory(String materialId) {
        logger.info("Retrieving quality history for material: {}", materialId);

        List<InspectionResultResponse> results = inspectionService.getInspectionResultsForMaterial(materialId);

        int totalInspections = results.size();
        long passedInspections = results.stream()
                .filter(r -> "PASS".equals(r.getResult()))
                .count();
        int failedInspections = totalInspections - (int) passedInspections;
        double passRate = totalInspections > 0 ? (double) passedInspections / totalInspections * 100 : 0.0;

        // Build inspection summary list
        List<QualityHistoryResponse.InspectionSummary> summaries = results.stream()
                .map(r -> new QualityHistoryResponse.InspectionSummary(
                        r.getInspectionId(),
                        r.getBatchId(),
                        r.getResult(),
                        r.getCompletedAt(),
                        r.getDefects()
                ))
                .toList();

        // Analyze defect patterns
        Map<String, Integer> defectPatterns = new HashMap<>();
        List<String> allDefects = new ArrayList<>();

        for (InspectionResultResponse result : results) {
            if (result.getDefects() != null) {
                for (String defect : result.getDefects()) {
                    defectPatterns.put(defect, defectPatterns.getOrDefault(defect, 0) + 1);
                    allDefects.add(defect);
                }
            }
        }

        // Get most common defects
        List<String> commonDefects = defectPatterns.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .toList();

        // Build trend analysis
        Map<String, Object> trendAnalysis = new HashMap<>();
        trendAnalysis.put("passRate", passRate);
        trendAnalysis.put("totalDefects", allDefects.size());
        trendAnalysis.put("averageDefectsPerInspection", totalInspections > 0 ? (double) allDefects.size() / totalInspections : 0);
        trendAnalysis.put("trend", passRate >= 90 ? "GOOD" : passRate >= 75 ? "ACCEPTABLE" : "CONCERNING");

        return new QualityHistoryResponse(
                materialId,
                totalInspections,
                (int) passedInspections,
                failedInspections,
                passRate,
                summaries,
                defectPatterns,
                commonDefects,
                trendAnalysis
        );
    }
}
