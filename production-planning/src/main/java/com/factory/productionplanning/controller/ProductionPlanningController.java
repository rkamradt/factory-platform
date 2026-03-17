package com.factory.productionplanning.controller;

import com.factory.productionplanning.dto.*;
import com.factory.productionplanning.service.ProductionPlanningService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ProductionPlanningController {

    private final ProductionPlanningService productionPlanningService;

    public ProductionPlanningController(ProductionPlanningService productionPlanningService) {
        this.productionPlanningService = productionPlanningService;
    }

    /**
     * Create production schedule from demand requirements
     * POST /production-schedules
     */
    @PostMapping("/production-schedules")
    public ResponseEntity<ProductionScheduleResponse> createProductionSchedule(
            @Valid @RequestBody CreateProductionScheduleRequest request) {
        ProductionScheduleResponse response = productionPlanningService.createProductionSchedule(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Generate work orders for workshops and assembly lines
     * POST /work-orders
     */
    @PostMapping("/work-orders")
    public ResponseEntity<WorkOrderResponse> createWorkOrder(
            @Valid @RequestBody CreateWorkOrderRequest request) {
        WorkOrderResponse response = productionPlanningService.createWorkOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Create production order with material reservations
     * POST /production-orders
     */
    @PostMapping("/production-orders")
    public ResponseEntity<ProductionOrderResponse> createProductionOrder(
            @Valid @RequestBody CreateProductionOrderRequest request) {
        ProductionOrderResponse response = productionPlanningService.createProductionOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Check production feasibility against capacity and materials
     * GET /production-orders/{orderId}/feasibility
     */
    @GetMapping("/production-orders/{orderId}/feasibility")
    public ResponseEntity<FeasibilityCheckResponse> checkProductionFeasibility(
            @PathVariable String orderId) {
        FeasibilityCheckResponse response = productionPlanningService.checkProductionFeasibility(orderId);
        return ResponseEntity.ok(response);
    }

    /**
     * Reschedule production based on delays or constraints
     * PUT /production-schedules/{scheduleId}/reschedule
     */
    @PutMapping("/production-schedules/{scheduleId}/reschedule")
    public ResponseEntity<ProductionScheduleResponse> rescheduleProduction(
            @PathVariable String scheduleId,
            @Valid @RequestBody RescheduleRequest request) {
        ProductionScheduleResponse response = productionPlanningService.rescheduleProduction(scheduleId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Query current capacity utilization across all resources
     * GET /capacity/utilization
     */
    @GetMapping("/capacity/utilization")
    public ResponseEntity<CapacityUtilizationResponse> getCapacityUtilization() {
        CapacityUtilizationResponse response = productionPlanningService.getCapacityUtilization();
        return ResponseEntity.ok(response);
    }

    /**
     * Get production order status and progress
     * GET /production-orders/{orderId}/status
     */
    @GetMapping("/production-orders/{orderId}/status")
    public ResponseEntity<ProductionOrderStatusResponse> getProductionOrderStatus(
            @PathVariable String orderId) {
        ProductionOrderStatusResponse response = productionPlanningService.getProductionOrderStatus(orderId);
        return ResponseEntity.ok(response);
    }
}
