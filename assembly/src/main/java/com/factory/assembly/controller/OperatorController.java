package com.factory.assembly.controller;

import com.factory.assembly.dto.AssignOperatorRequest;
import com.factory.assembly.dto.AssignOperatorResponse;
import com.factory.assembly.service.OperatorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operators")
public class OperatorController {

    private final OperatorService operatorService;

    public OperatorController(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    /**
     * Assign operators to specialized assembly tasks
     * POST /operators/{operatorId}/assign
     */
    @PostMapping("/{operatorId}/assign")
    public ResponseEntity<AssignOperatorResponse> assignOperator(
            @PathVariable String operatorId,
            @Valid @RequestBody AssignOperatorRequest request) {
        AssignOperatorResponse response = operatorService.assignOperator(operatorId, request);
        return ResponseEntity.ok(response);
    }
}
