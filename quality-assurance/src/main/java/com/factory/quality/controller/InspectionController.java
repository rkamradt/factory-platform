package com.factory.quality.controller;

import com.factory.quality.dto.InspectionResultRequest;
import com.factory.quality.dto.InspectionResultResponse;
import com.factory.quality.dto.ScheduleInspectionRequest;
import com.factory.quality.dto.ScheduleInspectionResponse;
import com.factory.quality.service.InspectionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inspections")
public class InspectionController {

    private final InspectionService inspectionService;

    public InspectionController(InspectionService inspectionService) {
        this.inspectionService = inspectionService;
    }

    /**
     * Schedule quality inspections for materials, components, or products
     * POST /inspections/schedule
     */
    @PostMapping("/schedule")
    public ResponseEntity<ScheduleInspectionResponse> scheduleInspection(
            @Valid @RequestBody ScheduleInspectionRequest request) {
        ScheduleInspectionResponse response = inspectionService.scheduleInspection(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Record inspection results and quality measurements
     * POST /inspections/{inspectionId}/results
     */
    @PostMapping("/{inspectionId}/results")
    public ResponseEntity<InspectionResultResponse> recordInspectionResult(
            @PathVariable String inspectionId,
            @Valid @RequestBody InspectionResultRequest request) {
        InspectionResultResponse response = inspectionService.recordInspectionResult(inspectionId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get inspection details
     * GET /inspections/{inspectionId}
     */
    @GetMapping("/{inspectionId}")
    public ResponseEntity<ScheduleInspectionResponse> getInspection(@PathVariable String inspectionId) {
        ScheduleInspectionResponse response = inspectionService.getInspection(inspectionId);
        return ResponseEntity.ok(response);
    }
}
