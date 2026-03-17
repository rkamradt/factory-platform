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
public class StockLevelResponse {

    private String materialId;
    private String materialName;
    private Double totalAvailableQuantity;
    private Double totalReservedQuantity;
    private Double totalQuantity;
    private String unit;
    private List<LocationStock> locations;
    private String status;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LocationStock {
        private String locationId;
        private String locationName;
        private Double availableQuantity;
        private Double reservedQuantity;
        private Double totalQuantity;
    }
}
