package com.factory.procurement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePurchaseOrderRequest {

    @NotBlank(message = "Supplier ID is required")
    private String supplierId;

    @NotBlank(message = "Supplier name is required")
    private String supplierName;

    private LocalDateTime requestedDeliveryDate;

    @NotEmpty(message = "At least one material is required")
    private List<MaterialItem> materials;

    private String notes;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MaterialItem {
        @NotBlank(message = "Material ID is required")
        private String materialId;

        @NotBlank(message = "Material name is required")
        private String materialName;

        @NotNull(message = "Quantity is required")
        private Double quantity;

        @NotBlank(message = "Unit is required")
        private String unit;

        private Double unitPrice;

        private String specifications;
    }
}
