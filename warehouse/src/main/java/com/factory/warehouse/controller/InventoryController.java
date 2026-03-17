package com.factory.warehouse.controller;

import com.factory.warehouse.dto.ReconcileInventoryRequest;
import com.factory.warehouse.dto.ReconcileInventoryResponse;
import com.factory.warehouse.service.InventoryReconciliationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryReconciliationService reconciliationService;

    public InventoryController(InventoryReconciliationService reconciliationService) {
        this.reconciliationService = reconciliationService;
    }

    /**
     * Reconcile physical vs system inventory counts
     * POST /inventory/reconcile
     */
    @PostMapping("/reconcile")
    public ResponseEntity<ReconcileInventoryResponse> reconcileInventory(
            @Valid @RequestBody ReconcileInventoryRequest request) {
        ReconcileInventoryResponse response = reconciliationService.reconcileInventory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
