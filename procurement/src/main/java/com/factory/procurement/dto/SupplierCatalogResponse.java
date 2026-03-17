package com.factory.procurement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierCatalogResponse {

    private String supplierId;
    private String supplierName;
    private List<CatalogItem> items;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CatalogItem {
        private String materialId;
        private String materialName;
        private String description;
        private Double unitPrice;
        private String unit;
        private Integer leadTimeDays;
        private Integer minimumOrderQuantity;
        private String availability; // IN_STOCK, LIMITED, OUT_OF_STOCK
        private String specifications;
    }
}
