package com.factory.productionplanning.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductionOrderRequest {

    @NotNull(message = "Product ID is required")
    private String productId;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotNull(message = "BOM ID is required")
    private String bomId;

    private String bomVersion;

    private LocalDateTime dueDate;

    private String priority; // HIGH, MEDIUM, LOW

    private String customerId;

    private String scheduleId;

    private boolean reserveMaterials; // Whether to reserve materials immediately
}
