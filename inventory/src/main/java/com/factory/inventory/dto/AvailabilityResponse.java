package com.factory.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailabilityResponse {

    private boolean allAvailable;
    private List<MaterialAvailability> materialAvailabilities;
    private String message;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MaterialAvailability {
        private String materialId;
        private Double requestedQuantity;
        private Double availableQuantity;
        private boolean available;
        private String unit;
    }
}
