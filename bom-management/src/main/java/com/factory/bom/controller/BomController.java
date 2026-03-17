package com.factory.bom.controller;

import com.factory.bom.dto.*;
import com.factory.bom.service.BomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boms")
public class BomController {

    private final BomService bomService;

    public BomController(BomService bomService) {
        this.bomService = bomService;
    }

    /**
     * Create new BOM version for a product
     * POST /boms
     */
    @PostMapping
    public ResponseEntity<BomResponse> createBom(@Valid @RequestBody CreateBomRequest request) {
        BomResponse response = bomService.createBom(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieve specific BOM version with full component hierarchy
     * GET /boms/{productId}/versions/{version}
     */
    @GetMapping("/{productId}/versions/{version}")
    public ResponseEntity<BomResponse> getBomVersion(
            @PathVariable String productId,
            @PathVariable String version) {
        BomResponse response = bomService.getBomVersion(productId, version);
        return ResponseEntity.ok(response);
    }

    /**
     * Get latest BOM version for a product
     * GET /boms/{productId}/latest
     */
    @GetMapping("/{productId}/latest")
    public ResponseEntity<BomResponse> getLatestBom(@PathVariable String productId) {
        BomResponse response = bomService.getLatestBom(productId);
        return ResponseEntity.ok(response);
    }

    /**
     * Calculate material requirements for given production quantity
     * POST /boms/{bomId}/explode
     */
    @PostMapping("/{bomId}/explode")
    public ResponseEntity<MaterialRequirementsResponse> explodeBom(
            @PathVariable String bomId,
            @Valid @RequestBody ExplodeBomRequest request) {
        MaterialRequirementsResponse response = bomService.explodeBom(bomId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Validate BOM for circular dependencies and completeness
     * POST /boms/{bomId}/validate
     */
    @PostMapping("/{bomId}/validate")
    public ResponseEntity<ValidationResponse> validateBom(@PathVariable String bomId) {
        ValidationResponse response = bomService.validateBom(bomId);
        return ResponseEntity.ok(response);
    }
}
