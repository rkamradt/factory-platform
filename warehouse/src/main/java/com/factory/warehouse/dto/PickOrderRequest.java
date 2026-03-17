package com.factory.warehouse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PickOrderRequest {

    @NotBlank(message = "Production order ID is required")
    private String productionOrderId;

    @NotNull(message = "Items to pick are required")
    private List<PickItem> items;

    private String destinationLocation;
    private String assignedOperatorId;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PickItem {
        @NotBlank(message = "Material ID is required")
        private String materialId;

        @NotNull(message = "Quantity is required")
        private Double quantity;

        @NotBlank(message = "Unit is required")
        private String unit;

        private String sourceLocation;
    }
}
