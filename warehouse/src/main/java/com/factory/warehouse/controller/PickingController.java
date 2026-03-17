package com.factory.warehouse.controller;

import com.factory.warehouse.dto.PickOrderRequest;
import com.factory.warehouse.dto.PickOrderResponse;
import com.factory.warehouse.service.PickingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/picking")
public class PickingController {

    private final PickingService pickingService;

    public PickingController(PickingService pickingService) {
        this.pickingService = pickingService;
    }

    /**
     * Pick materials for production orders
     * POST /picking/orders/{orderId}
     */
    @PostMapping("/orders/{orderId}")
    public ResponseEntity<PickOrderResponse> pickOrder(
            @PathVariable String orderId,
            @Valid @RequestBody PickOrderRequest request) {
        PickOrderResponse response = pickingService.pickMaterials(orderId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
