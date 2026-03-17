package com.factory.warehouse.controller;

import com.factory.warehouse.dto.ReceiveGoodsRequest;
import com.factory.warehouse.dto.ReceiveGoodsResponse;
import com.factory.warehouse.service.ReceivingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receiving")
public class ReceivingController {

    private final ReceivingService receivingService;

    public ReceivingController(ReceivingService receivingService) {
        this.receivingService = receivingService;
    }

    /**
     * Receive goods from suppliers with quality verification
     * POST /receiving/goods
     */
    @PostMapping("/goods")
    public ResponseEntity<ReceiveGoodsResponse> receiveGoods(@Valid @RequestBody ReceiveGoodsRequest request) {
        ReceiveGoodsResponse response = receivingService.receiveGoods(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
