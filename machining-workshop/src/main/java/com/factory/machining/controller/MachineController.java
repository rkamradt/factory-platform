package com.factory.machining.controller;

import com.factory.machining.dto.MachineStatusRequest;
import com.factory.machining.dto.MachineStatusResponse;
import com.factory.machining.service.MachineService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/machines")
public class MachineController {

    private final MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    /**
     * Report machine status, downtime, or maintenance requirements
     * POST /machines/{machineId}/status
     */
    @PostMapping("/{machineId}/status")
    public ResponseEntity<MachineStatusResponse> updateMachineStatus(
            @PathVariable String machineId,
            @Valid @RequestBody MachineStatusRequest request) {
        MachineStatusResponse response = machineService.updateMachineStatus(machineId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
