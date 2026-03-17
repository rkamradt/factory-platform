package com.factory.machining.service;

import com.factory.machining.dto.MachineStatusRequest;
import com.factory.machining.dto.MachineStatusResponse;
import com.factory.machining.event.MachiningEventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class MachineService {

    private static final Logger logger = LoggerFactory.getLogger(MachineService.class);
    private final MachiningEventProducer eventProducer;

    public MachineService(MachiningEventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    public MachineStatusResponse updateMachineStatus(String machineId, MachineStatusRequest request) {
        logger.info("Updating machine status: {} to {}", machineId, request.getStatus());

        // TODO: Implement actual machine status update logic
        // TODO: Validate status transitions
        // TODO: Track downtime and utilization
        // TODO: Persist to database

        MachineStatusResponse response = MachineStatusResponse.builder()
                .machineId(machineId)
                .status(request.getStatus())
                .machineType("CNC") // TODO: Get from database
                .statusChangedAt(LocalDateTime.now())
                .operatorId(request.getOperatorId())
                .currentWorkOrderId(request.getWorkOrderId())
                .estimatedDowntimeMinutes(request.getEstimatedDowntimeMinutes())
                .maintenanceRequired("MAINTENANCE".equals(request.getStatus()) || "BREAKDOWN".equals(request.getStatus()))
                .build();

        // If maintenance or breakdown, publish maintenance required event
        if ("MAINTENANCE".equals(request.getStatus()) || "BREAKDOWN".equals(request.getStatus())) {
            Map<String, Object> maintenanceDetails = new HashMap<>();
            maintenanceDetails.put("status", request.getStatus());
            maintenanceDetails.put("downtimeReason", request.getDowntimeReason());
            maintenanceDetails.put("estimatedDowntimeMinutes", request.getEstimatedDowntimeMinutes());
            maintenanceDetails.put("workOrderId", request.getWorkOrderId());
            maintenanceDetails.put("notes", request.getNotes());

            String maintenanceType = request.getMaintenanceType() != null
                    ? request.getMaintenanceType()
                    : "BREAKDOWN".equals(request.getStatus()) ? "EMERGENCY" : "PREVENTIVE";

            eventProducer.publishMachineMaintenanceRequired(machineId, maintenanceType, maintenanceDetails);
        }

        return response;
    }
}
