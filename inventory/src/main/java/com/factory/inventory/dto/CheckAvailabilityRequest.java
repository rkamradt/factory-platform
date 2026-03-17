package com.factory.inventory.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckAvailabilityRequest {

    @NotEmpty(message = "Materials list cannot be empty")
    private List<MaterialQuantity> materials;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MaterialQuantity {
        @NotNull(message = "Material ID is required")
        private String materialId;

        @NotNull(message = "Quantity is required")
        private Double quantity;

        private String unit;
    }
}
