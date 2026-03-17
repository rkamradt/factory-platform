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
public class LocationStockResponse {

    private String locationId;
    private String locationName;
    private List<MaterialStock> materials;
    private String status;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MaterialStock {
        private String materialId;
        private String materialName;
        private Double availableQuantity;
        private Double reservedQuantity;
        private Double totalQuantity;
        private String unit;
    }
}
