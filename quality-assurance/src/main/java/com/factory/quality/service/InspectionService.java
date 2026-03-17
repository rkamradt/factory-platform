package com.factory.quality.service;

import com.factory.quality.dto.*;
import com.factory.quality.event.QualityEventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class InspectionService {

    private static final Logger logger = LoggerFactory.getLogger(InspectionService.class);
    private final QualityEventProducer eventProducer;

    // In-memory storage for demo (replace with database in production)
    private final Map<String, ScheduleInspectionResponse> inspections = new HashMap<>();
    private final Map<String, InspectionResultResponse> inspectionResults = new HashMap<>();

    public InspectionService(QualityEventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    /**
     * Schedule quality inspections for materials, components, or products
     */
    public ScheduleInspectionResponse scheduleInspection(ScheduleInspectionRequest request) {
        logger.info("Scheduling inspection for material: {}, type: {}",
                request.getMaterialId(), request.getInspectionType());

        String inspectionId = "INSP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        ScheduleInspectionResponse response = new ScheduleInspectionResponse(
                inspectionId,
                request.getMaterialId(),
                request.getInspectionType(),
                request.getStandardId(),
                request.getBatchId(),
                "SCHEDULED",
                request.getScheduledDate() != null ? request.getScheduledDate() : LocalDateTime.now().plusHours(2),
                null, // Assign inspector later
                LocalDateTime.now()
        );

        inspections.put(inspectionId, response);

        logger.info("Inspection scheduled with ID: {}", inspectionId);
        return response;
    }

    /**
     * Record inspection results and quality measurements
     */
    public InspectionResultResponse recordInspectionResult(String inspectionId, InspectionResultRequest request) {
        logger.info("Recording inspection result for inspection: {}, result: {}", inspectionId, request.getResult());

        ScheduleInspectionResponse inspection = inspections.get(inspectionId);
        if (inspection == null) {
            throw new IllegalArgumentException("Inspection not found: " + inspectionId);
        }

        // Update inspection status
        inspection.setStatus("COMPLETED");

        // Create result response
        InspectionResultResponse resultResponse = new InspectionResultResponse(
                inspectionId,
                inspection.getMaterialId(),
                inspection.getBatchId(),
                request.getResult(),
                request.getInspectorId(),
                request.getMeasurements(),
                request.getDefects(),
                request.getCorrectionRequired(),
                LocalDateTime.now(),
                "PASS".equals(request.getResult())
        );

        inspectionResults.put(inspectionId, resultResponse);

        // Publish inspection completed event
        eventProducer.publishInspectionCompleted(
                inspectionId,
                inspection.getMaterialId(),
                inspection.getBatchId(),
                request.getResult(),
                request.getMeasurements(),
                request.getDefects()
        );

        // If inspection failed, publish failure detected event
        if ("FAIL".equals(request.getResult())) {
            String failureId = "FAIL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            eventProducer.publishQualityFailureDetected(
                    failureId,
                    inspectionId,
                    inspection.getMaterialId(),
                    inspection.getBatchId(),
                    request.getDefects(),
                    request.getCorrectionRequired()
            );
        }

        // If passed, may trigger certificate issuance
        if ("PASS".equals(request.getResult()) && inspection.getBatchId() != null) {
            // Certificate will be generated separately via API
            logger.info("Inspection passed for batch: {}, certificate can be issued", inspection.getBatchId());
        }

        return resultResponse;
    }

    /**
     * Get inspection by ID
     */
    public ScheduleInspectionResponse getInspection(String inspectionId) {
        ScheduleInspectionResponse inspection = inspections.get(inspectionId);
        if (inspection == null) {
            throw new IllegalArgumentException("Inspection not found: " + inspectionId);
        }
        return inspection;
    }

    /**
     * Get all inspections for a material
     */
    public List<InspectionResultResponse> getInspectionResultsForMaterial(String materialId) {
        return inspectionResults.values().stream()
                .filter(result -> materialId.equals(result.getMaterialId()))
                .toList();
    }
}
