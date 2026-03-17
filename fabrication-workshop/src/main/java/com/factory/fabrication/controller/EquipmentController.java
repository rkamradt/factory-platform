package com.factory.fabrication.controller;

import com.factory.fabrication.dto.EquipmentStatusRequest;
import com.factory.fabrication.dto.EquipmentStatusResponse;
import com.factory.fabrication.service.FabricationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    private final FabricationService fabricationService;

    public EquipmentController(FabricationService fabricationService) {
        this.fabricationService = fabricationService;
    }

    /**
     * Report equipment status, downtime, or maintenance requirements
     * POST /equipment/{equipmentId}/status
     */
    @PostMapping("/{equipmentId}/status")
    public ResponseEntity<EquipmentStatusResponse> reportEquipmentStatus(
            @PathVariable String equipmentId,
            @Valid @RequestBody EquipmentStatusRequest request) {
        EquipmentStatusResponse response = fabricationService.reportEquipmentStatus(equipmentId, request);
        return ResponseEntity.ok(response);
    }
}
