package com.factory.machining.service;

import com.factory.machining.dto.*;
import com.factory.machining.event.MachiningEventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class WorkOrderService {

    private static final Logger logger = LoggerFactory.getLogger(WorkOrderService.class);
    private final MachiningEventProducer eventProducer;

    public WorkOrderService(MachiningEventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    public AcceptWorkOrderResponse acceptWorkOrder(String orderId, AcceptWorkOrderRequest request) {
        logger.info("Accepting work order: {} from production planning", orderId);

        // TODO: Implement actual acceptance logic
        // TODO: Validate work order requirements
        // TODO: Check capacity and capability
        // TODO: Persist to database

        String workOrderId = UUID.randomUUID().toString();

        AcceptWorkOrderResponse response = AcceptWorkOrderResponse.builder()
                .workOrderId(workOrderId)
                .orderId(orderId)
                .status("ACCEPTED")
                .acceptedAt(LocalDateTime.now())
                .componentId(request.getComponentId())
                .componentName(request.getComponentName())
                .quantity(request.getQuantity())
                .priority(request.getPriority())
                .build();

        return response;
    }

    public ScheduleWorkOrderResponse scheduleWorkOrder(String orderId, ScheduleWorkOrderRequest request) {
        logger.info("Scheduling work order: {} on machine: {}", orderId, request.getMachineId());

        // TODO: Implement actual scheduling logic
        // TODO: Check machine availability
        // TODO: Optimize scheduling based on priority and capacity
        // TODO: Assign to machine or general pool
        // TODO: Persist to database

        LocalDateTime startTime = request.getScheduledStartTime() != null
                ? request.getScheduledStartTime()
                : LocalDateTime.now().plusHours(1);

        LocalDateTime endTime = startTime.plusMinutes(
                request.getEstimatedDurationMinutes() != null
                        ? request.getEstimatedDurationMinutes()
                        : 120
        );

        ScheduleWorkOrderResponse response = ScheduleWorkOrderResponse.builder()
                .workOrderId(UUID.randomUUID().toString())
                .orderId(orderId)
                .status("SCHEDULED")
                .machineId(request.getMachineId())
                .machineType(request.getMachineType())
                .scheduledStartTime(startTime)
                .scheduledEndTime(endTime)
                .estimatedDurationMinutes(request.getEstimatedDurationMinutes())
                .build();

        return response;
    }

    public UpdateProgressResponse updateProgress(String orderId, UpdateProgressRequest request) {
        logger.info("Updating work order progress: {} to stage: {}", orderId, request.getStage());

        // TODO: Implement actual progress update logic
        // TODO: Validate stage transitions
        // TODO: Update work order status
        // TODO: Track completion and rejection quantities
        // TODO: Persist to database

        boolean isCompleted = "COMPLETED".equals(request.getStage());
        int remainingQuantity = 100 - (request.getCompletedQuantity() != null ? request.getCompletedQuantity() : 0);

        UpdateProgressResponse response = UpdateProgressResponse.builder()
                .workOrderId(UUID.randomUUID().toString())
                .orderId(orderId)
                .stage(request.getStage())
                .status(isCompleted ? "COMPLETED" : "IN_PROGRESS")
                .completedQuantity(request.getCompletedQuantity())
                .rejectedQuantity(request.getRejectedQuantity())
                .remainingQuantity(remainingQuantity)
                .updatedAt(LocalDateTime.now())
                .qualityStatus(request.getQualityStatus())
                .build();

        // If work order is completed, publish completion event
        if (isCompleted) {
            Map<String, Object> completionDetails = new HashMap<>();
            completionDetails.put("completedQuantity", request.getCompletedQuantity());
            completionDetails.put("rejectedQuantity", request.getRejectedQuantity());
            completionDetails.put("qualityStatus", request.getQualityStatus());
            completionDetails.put("operatorId", request.getOperatorId());
            completionDetails.put("machineId", request.getMachineId());

            eventProducer.publishWorkOrderCompleted(response.getWorkOrderId(), orderId, completionDetails);
        }

        // If quality issue detected, publish quality issue event
        if ("FAIL".equals(request.getQualityStatus())) {
            Map<String, Object> issueDetails = new HashMap<>();
            issueDetails.put("stage", request.getStage());
            issueDetails.put("rejectedQuantity", request.getRejectedQuantity());
            issueDetails.put("notes", request.getNotes());

            eventProducer.publishQualityIssueDetected(response.getWorkOrderId(), "QUALITY_FAILURE", issueDetails);
        }

        return response;
    }
}
