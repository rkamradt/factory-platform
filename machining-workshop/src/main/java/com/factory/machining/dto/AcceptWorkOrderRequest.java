package com.factory.machining.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcceptWorkOrderRequest {

    @NotBlank(message = "Production order ID is required")
    private String productionOrderId;

    private String componentId;
    private String componentName;
    private Integer quantity;
    private String priority; // HIGH, MEDIUM, LOW
    private String notes;
}
