package com.factory.warehouse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveGoodsRequest {

    @NotBlank(message = "Purchase order ID is required")
    private String purchaseOrderId;

    @NotBlank(message = "Supplier ID is required")
    private String supplierId;

    @NotNull(message = "Items list is required")
    private List<ReceivedItem> items;

    private String dockLocation;
    private String notes;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReceivedItem {
        @NotBlank(message = "Material ID is required")
        private String materialId;

        @NotBlank(message = "Material name is required")
        private String materialName;

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be positive")
        private Double quantity;

        @NotBlank(message = "Unit is required")
        private String unit;

        @NotBlank(message = "Storage location is required")
        private String storageLocation;

        private String qualityStatus; // PASS, FAIL, PENDING_INSPECTION
        private String notes;
    }
}
