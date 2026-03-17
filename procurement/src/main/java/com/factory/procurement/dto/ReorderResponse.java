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
public class ReorderResponse {

    private Integer totalMaterialsChecked;
    private Integer materialsNeedingReorder;
    private List<String> createdPurchaseOrderIds;
    private List<ReorderItem> reorderItems;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReorderItem {
        private String materialId;
        private String materialName;
        private Double currentStock;
        private Double reorderPoint;
        private Double reorderQuantity;
        private String preferredSupplierId;
        private String supplierName;
        private String purchaseOrderId;
        private String status; // CREATED, SKIPPED, FAILED
    }
}
