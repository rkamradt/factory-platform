package com.factory.compliance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogMovementRequest {

    private String authorizationId; // Optional - reference to authorization if used

    @NotBlank(message = "Material ID is required")
    private String materialId;

    @NotNull(message = "Quantity is required")
    private Double quantity;

    @NotBlank(message = "Source location is required")
    private String sourceLocation;

    @NotBlank(message = "Destination location is required")
    private String destinationLocation;

    @NotBlank(message = "Movement type is required")
    private String movementType; // e.g., "TRANSFER", "RECEIPT", "CONSUMPTION", "RETURN"

    private String executedBy;
    private LocalDateTime executedAt;
    private String orderId; // Optional reference to production/work order
    private Map<String, Object> additionalContext;
}
