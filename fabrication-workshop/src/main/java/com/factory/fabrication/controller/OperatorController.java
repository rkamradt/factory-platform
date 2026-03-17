package com.factory.fabrication.controller;

import com.factory.fabrication.dto.OperatorAssignmentRequest;
import com.factory.fabrication.dto.OperatorAssignmentResponse;
import com.factory.fabrication.service.FabricationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operators")
public class OperatorController {

    private final FabricationService fabricationService;

    public OperatorController(FabricationService fabricationService) {
        this.fabricationService = fabricationService;
    }

    /**
     * Assign certified operators to welding or specialized fabrication processes
     * POST /operators/{operatorId}/assign
     */
    @PostMapping("/{operatorId}/assign")
    public ResponseEntity<OperatorAssignmentResponse> assignOperator(
            @PathVariable String operatorId,
            @Valid @RequestBody OperatorAssignmentRequest request) {
        OperatorAssignmentResponse response = fabricationService.assignOperator(operatorId, request);
        return ResponseEntity.ok(response);
    }
}
