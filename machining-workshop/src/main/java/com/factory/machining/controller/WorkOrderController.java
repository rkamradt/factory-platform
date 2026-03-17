package com.factory.machining.controller;

import com.factory.machining.dto.*;
import com.factory.machining.service.WorkOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/work-orders")
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    public WorkOrderController(WorkOrderService workOrderService) {
        this.workOrderService = workOrderService;
    }

    /**
     * Accept machining work order from production planning
     * POST /work-orders/{orderId}/accept
     */
    @PostMapping("/{orderId}/accept")
    public ResponseEntity<AcceptWorkOrderResponse> acceptWorkOrder(
            @PathVariable String orderId,
            @Valid @RequestBody AcceptWorkOrderRequest request) {
        AcceptWorkOrderResponse response = workOrderService.acceptWorkOrder(orderId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Schedule work on specific machines or general machining pool
     * POST /work-orders/{orderId}/schedule
     */
    @PostMapping("/{orderId}/schedule")
    public ResponseEntity<ScheduleWorkOrderResponse> scheduleWorkOrder(
            @PathVariable String orderId,
            @Valid @RequestBody ScheduleWorkOrderRequest request) {
        ScheduleWorkOrderResponse response = workOrderService.scheduleWorkOrder(orderId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Update work order progress through setup, machining, inspection stages
     * PUT /work-orders/{orderId}/progress
     */
    @PutMapping("/{orderId}/progress")
    public ResponseEntity<UpdateProgressResponse> updateProgress(
            @PathVariable String orderId,
            @Valid @RequestBody UpdateProgressRequest request) {
        UpdateProgressResponse response = workOrderService.updateProgress(orderId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
