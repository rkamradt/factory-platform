package com.factory.procurement.controller;

import com.factory.procurement.dto.SupplierCatalogResponse;
import com.factory.procurement.dto.SupplierPerformanceRequest;
import com.factory.procurement.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    /**
     * Search supplier catalog for available materials
     * GET /suppliers/{supplierId}/catalog
     */
    @GetMapping("/{supplierId}/catalog")
    public ResponseEntity<SupplierCatalogResponse> getSupplierCatalog(
            @PathVariable String supplierId,
            @RequestParam(required = false) String searchTerm) {
        SupplierCatalogResponse response = supplierService.getSupplierCatalog(supplierId, searchTerm);
        return ResponseEntity.ok(response);
    }

    /**
     * Update supplier performance metrics
     * POST /suppliers/{supplierId}/performance
     */
    @PostMapping("/{supplierId}/performance")
    public ResponseEntity<Map<String, Object>> updateSupplierPerformance(
            @PathVariable String supplierId,
            @Valid @RequestBody SupplierPerformanceRequest request) {
        Map<String, Object> response = supplierService.updateSupplierPerformance(supplierId, request);
        return ResponseEntity.ok(response);
    }
}
