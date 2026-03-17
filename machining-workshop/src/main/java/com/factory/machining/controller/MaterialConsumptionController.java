package com.factory.machining.controller;

import com.factory.machining.dto.MaterialConsumptionRequest;
import com.factory.machining.dto.MaterialConsumptionResponse;
import com.factory.machining.service.MaterialConsumptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/work-orders")
public class MaterialConsumptionController {

    private final MaterialConsumptionService materialConsumptionService;

    public MaterialConsumptionController(MaterialConsumptionService materialConsumptionService) {
        this.materialConsumptionService = materialConsumptionService;
    }

    /**
     * Track material consumption, scrap rates, and yield
     * POST /work-orders/{orderId}/consumption
     */
    @PostMapping("/{orderId}/consumption")
    public ResponseEntity<MaterialConsumptionResponse> trackConsumption(
            @PathVariable String orderId,
            @Valid @RequestBody MaterialConsumptionRequest request) {
        MaterialConsumptionResponse response = materialConsumptionService.trackConsumption(orderId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
