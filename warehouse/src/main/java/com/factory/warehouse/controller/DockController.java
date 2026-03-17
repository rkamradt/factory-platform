package com.factory.warehouse.controller;

import com.factory.warehouse.dto.DockScheduleRequest;
import com.factory.warehouse.dto.DockScheduleResponse;
import com.factory.warehouse.service.DockSchedulingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dock")
public class DockController {

    private final DockSchedulingService dockSchedulingService;

    public DockController(DockSchedulingService dockSchedulingService) {
        this.dockSchedulingService = dockSchedulingService;
    }

    /**
     * Schedule dock appointments for receiving/shipping
     * POST /dock/schedule
     */
    @PostMapping("/schedule")
    public ResponseEntity<DockScheduleResponse> scheduleDockAppointment(
            @Valid @RequestBody DockScheduleRequest request) {
        DockScheduleResponse response = dockSchedulingService.scheduleDockAppointment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
