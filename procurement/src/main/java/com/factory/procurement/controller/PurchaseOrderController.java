package com.factory.procurement.controller;

import com.factory.procurement.dto.*;
import com.factory.procurement.service.ProcurementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase-orders")
public class PurchaseOrderController {

    private final ProcurementService procurementService;

    public PurchaseOrderController(ProcurementService procurementService) {
        this.procurementService = procurementService;
    }

    /**
     * Create new purchase order for materials
     * POST /purchase-orders
     */
    @PostMapping
    public ResponseEntity<PurchaseOrderResponse> createPurchaseOrder(
            @Valid @RequestBody CreatePurchaseOrderRequest request) {
        PurchaseOrderResponse response = procurementService.createPurchaseOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get purchase order status and details
     * GET /purchase-orders/{poId}
     */
    @GetMapping("/{poId}")
    public ResponseEntity<PurchaseOrderResponse> getPurchaseOrder(@PathVariable String poId) {
        PurchaseOrderResponse response = procurementService.getPurchaseOrder(poId);
        return ResponseEntity.ok(response);
    }

    /**
     * Record goods receipt and update PO status
     * POST /purchase-orders/{poId}/receive
     */
    @PostMapping("/{poId}/receive")
    public ResponseEntity<PurchaseOrderResponse> receiveGoods(
            @PathVariable String poId,
            @Valid @RequestBody GoodsReceiptRequest request) {
        PurchaseOrderResponse response = procurementService.receiveGoods(poId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get expected delivery schedule for planning
     * GET /purchase-orders/delivery-schedule
     */
    @GetMapping("/delivery-schedule")
    public ResponseEntity<DeliveryScheduleResponse> getDeliverySchedule() {
        DeliveryScheduleResponse response = procurementService.getDeliverySchedule();
        return ResponseEntity.ok(response);
    }
}
