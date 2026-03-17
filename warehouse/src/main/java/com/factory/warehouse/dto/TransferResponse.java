package com.factory.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponse {

    private String transferId;
    private String materialId;
    private Double quantity;
    private String unit;
    private String sourceLocation;
    private String destinationLocation;
    private String status; // PENDING_AUTHORIZATION, AUTHORIZED, IN_PROGRESS, COMPLETED, REJECTED
    private String authorizationId;
    private LocalDateTime requestedAt;
    private LocalDateTime completedAt;
    private String requestedBy;
}
