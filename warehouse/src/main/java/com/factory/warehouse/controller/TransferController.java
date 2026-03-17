package com.factory.warehouse.controller;

import com.factory.warehouse.dto.TransferRequest;
import com.factory.warehouse.dto.TransferResponse;
import com.factory.warehouse.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    /**
     * Transfer materials between storage locations
     * POST /transfers
     */
    @PostMapping
    public ResponseEntity<TransferResponse> transferMaterial(@Valid @RequestBody TransferRequest request) {
        TransferResponse response = transferService.transferMaterial(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
