package com.factory.compliance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizeTransferRequest {

    @NotBlank(message = "Material ID is required")
    private String materialId;

    @NotNull(message = "Quantity is required")
    private Double quantity;

    @NotBlank(message = "Source location is required")
    private String sourceLocation;

    @NotBlank(message = "Destination location is required")
    private String destinationLocation;

    @NotBlank(message = "Transfer type is required")
    private String transferType; // e.g., "PRODUCTION", "TRANSFER", "RETURN", "SHIPMENT"

    private String requesterId;
    private String reason;
    private String orderId; // Optional reference to production/work order
}
