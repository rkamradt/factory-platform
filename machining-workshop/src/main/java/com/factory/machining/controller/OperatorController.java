package com.factory.machining.controller;

import com.factory.machining.dto.OperatorAssignmentRequest;
import com.factory.machining.dto.OperatorAssignmentResponse;
import com.factory.machining.service.OperatorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
     * Assign qualified operators to machines and work orders
     * POST /operators/{operatorId}/assign
     */
    @PostMapping("/{operatorId}/assign")
    public ResponseEntity<OperatorAssignmentResponse> assignOperator(
            @PathVariable String operatorId,
            @Valid @RequestBody OperatorAssignmentRequest request) {
        OperatorAssignmentResponse response = operatorService.assignOperator(operatorId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
