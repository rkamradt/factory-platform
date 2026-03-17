package com.factory.procurement.controller;

import com.factory.procurement.dto.ReorderRequest;
import com.factory.procurement.dto.ReorderResponse;
import com.factory.procurement.service.ProcurementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reorder")
public class ReorderController {

    private final ProcurementService procurementService;

    public ReorderController(ProcurementService procurementService) {
        this.procurementService = procurementService;
    }

    /**
     * Create purchase orders for materials below reorder points
     * POST /reorder
     */
    @PostMapping
    public ResponseEntity<ReorderResponse> triggerReorder(@Valid @RequestBody ReorderRequest request) {
        ReorderResponse response = procurementService.triggerReorder(request);
        return ResponseEntity.ok(response);
    }
}
