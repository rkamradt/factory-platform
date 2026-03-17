package com.factory.warehouse.controller;

import com.factory.warehouse.dto.ShipProductsRequest;
import com.factory.warehouse.dto.ShipProductsResponse;
import com.factory.warehouse.service.ShippingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    private final ShippingService shippingService;

    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    /**
     * Pack and ship finished products to customers
     * POST /shipping/products
     */
    @PostMapping("/products")
    public ResponseEntity<ShipProductsResponse> shipProducts(@Valid @RequestBody ShipProductsRequest request) {
        ShipProductsResponse response = shippingService.shipProducts(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
