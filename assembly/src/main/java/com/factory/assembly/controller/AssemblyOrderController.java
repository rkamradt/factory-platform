package com.factory.assembly.controller;

import com.factory.assembly.dto.*;
import com.factory.assembly.service.AssemblyOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assembly-orders")
public class AssemblyOrderController {

    private final AssemblyOrderService assemblyOrderService;

    public AssemblyOrderController(AssemblyOrderService assemblyOrderService) {
        this.assemblyOrderService = assemblyOrderService;
    }

    /**
     * Accept assembly order from production planning
     * POST /assembly-orders/{orderId}/accept
     */
    @PostMapping("/{orderId}/accept")
    public ResponseEntity<AcceptAssemblyOrderResponse> acceptAssemblyOrder(
            @PathVariable String orderId,
            @Valid @RequestBody AcceptAssemblyOrderRequest request) {
        AcceptAssemblyOrderResponse response = assemblyOrderService.acceptAssemblyOrder(orderId, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    /**
     * Schedule work on assembly lines or workstations
     * POST /assembly-orders/{orderId}/schedule
     */
    @PostMapping("/{orderId}/schedule")
    public ResponseEntity<ScheduleAssemblyResponse> scheduleAssembly(
            @PathVariable String orderId,
            @Valid @RequestBody ScheduleAssemblyRequest request) {
        ScheduleAssemblyResponse response = assemblyOrderService.scheduleAssembly(orderId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Update assembly progress through build stages
     * PUT /assembly-orders/{orderId}/progress
     */
    @PutMapping("/{orderId}/progress")
    public ResponseEntity<UpdateProgressResponse> updateProgress(
            @PathVariable String orderId,
            @Valid @RequestBody UpdateProgressRequest request) {
        UpdateProgressResponse response = assemblyOrderService.updateProgress(orderId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Track component consumption and assembly yield
     * POST /assembly-orders/{orderId}/consumption
     */
    @PostMapping("/{orderId}/consumption")
    public ResponseEntity<TrackConsumptionResponse> trackConsumption(
            @PathVariable String orderId,
            @Valid @RequestBody TrackConsumptionRequest request) {
        TrackConsumptionResponse response = assemblyOrderService.trackConsumption(orderId, request);
        return ResponseEntity.ok(response);
    }
}
