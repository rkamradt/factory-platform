package com.factory.inventory.controller;

import com.factory.inventory.dto.*;
import com.factory.inventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * Check availability for list of materials with quantities
     * POST /inventory/check-availability
     */
    @PostMapping("/check-availability")
    public ResponseEntity<AvailabilityResponse> checkAvailability(
            @Valid @RequestBody CheckAvailabilityRequest request) {
        AvailabilityResponse response = inventoryService.checkAvailability(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Reserve materials for a production order
     * POST /inventory/reserve
     */
    @PostMapping("/reserve")
    public ResponseEntity<ReservationResponse> reserveMaterials(
            @Valid @RequestBody ReserveMaterialsRequest request) {
        ReservationResponse response = inventoryService.reserveMaterials(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Allocate reserved materials for immediate use
     * POST /inventory/allocate
     */
    @PostMapping("/allocate")
    public ResponseEntity<AllocationResponse> allocateMaterials(
            @Valid @RequestBody AllocateMaterialsRequest request) {
        AllocationResponse response = inventoryService.allocateMaterials(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Record stock movements (receipts, consumption, transfers)
     * POST /inventory/movements
     */
    @PostMapping("/movements")
    public ResponseEntity<MovementResponse> recordMovement(
            @Valid @RequestBody RecordMovementRequest request) {
        MovementResponse response = inventoryService.recordMovement(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get current stock levels and locations for a material
     * GET /inventory/stock/{materialId}
     */
    @GetMapping("/stock/{materialId}")
    public ResponseEntity<StockLevelResponse> getStockLevel(@PathVariable String materialId) {
        StockLevelResponse response = inventoryService.getStockLevel(materialId);
        return ResponseEntity.ok(response);
    }

    /**
     * Release reserved materials when production completes or cancels
     * POST /inventory/release-reservation
     */
    @PostMapping("/release-reservation")
    public ResponseEntity<ReleaseResponse> releaseReservation(
            @Valid @RequestBody ReleaseReservationRequest request) {
        ReleaseResponse response = inventoryService.releaseReservation(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all stock at a specific location
     * GET /inventory/locations/{locationId}/stock
     */
    @GetMapping("/locations/{locationId}/stock")
    public ResponseEntity<LocationStockResponse> getLocationStock(@PathVariable String locationId) {
        LocationStockResponse response = inventoryService.getLocationStock(locationId);
        return ResponseEntity.ok(response);
    }
}
