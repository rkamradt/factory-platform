package com.factory.fabrication.controller;

import com.factory.fabrication.dto.*;
import com.factory.fabrication.service.FabricationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/work-orders")
public class WorkOrderController {

    private final FabricationService fabricationService;

    public WorkOrderController(FabricationService fabricationService) {
        this.fabricationService = fabricationService;
    }

    /**
     * Accept fabrication work order from production planning
     * POST /work-orders/{orderId}/accept
     */
    @PostMapping("/{orderId}/accept")
    public ResponseEntity<WorkOrderResponse> acceptWorkOrder(
            @PathVariable String orderId,
            @Valid @RequestBody AcceptWorkOrderRequest request) {
        WorkOrderResponse response = fabricationService.acceptWorkOrder(orderId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Schedule work on specific equipment or general fabrication pool
     * POST /work-orders/{orderId}/schedule
     */
    @PostMapping("/{orderId}/schedule")
    public ResponseEntity<WorkOrderResponse> scheduleWorkOrder(
            @PathVariable String orderId,
            @Valid @RequestBody ScheduleWorkOrderRequest request) {
        WorkOrderResponse response = fabricationService.scheduleWorkOrder(orderId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Update work order progress through cutting, forming, welding, finishing stages
     * PUT /work-orders/{orderId}/progress
     */
    @PutMapping("/{orderId}/progress")
    public ResponseEntity<WorkOrderResponse> updateProgress(
            @PathVariable String orderId,
            @Valid @RequestBody UpdateProgressRequest request) {
        WorkOrderResponse response = fabricationService.updateProgress(orderId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Track material consumption, waste rates, and rework
     * POST /work-orders/{orderId}/consumption
     */
    @PostMapping("/{orderId}/consumption")
    public ResponseEntity<MaterialConsumptionResponse> trackMaterialConsumption(
            @PathVariable String orderId,
            @Valid @RequestBody MaterialConsumptionRequest request) {
        MaterialConsumptionResponse response = fabricationService.trackMaterialConsumption(orderId, request);
        return ResponseEntity.ok(response);
    }
}
