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
public class ValidateTransferRequest {

    @NotBlank(message = "Material ID is required")
    private String materialId;

    @NotNull(message = "Quantity is required")
    private Double quantity;

    @NotBlank(message = "Source location is required")
    private String sourceLocation;

    @NotBlank(message = "Destination location is required")
    private String destinationLocation;

    @NotBlank(message = "Transfer type is required")
    private String transferType;

    private String orderId;
}
