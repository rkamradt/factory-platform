package com.factory.procurement.dto;

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
public class PurchaseOrderResponse {

    private String poId;
    private String supplierId;
    private String supplierName;
    private String status; // DRAFT, SENT, CONFIRMED, PARTIALLY_RECEIVED, RECEIVED, CANCELLED
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
    private LocalDateTime confirmedAt;
    private LocalDateTime requestedDeliveryDate;
    private LocalDateTime expectedDeliveryDate;
    private LocalDateTime actualDeliveryDate;
    private List<MaterialItem> materials;
    private Double totalAmount;
    private String notes;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MaterialItem {
        private String materialId;
        private String materialName;
        private Double orderedQuantity;
        private Double receivedQuantity;
        private String unit;
        private Double unitPrice;
        private String specifications;
        private String status; // PENDING, RECEIVED, PARTIALLY_RECEIVED
    }
}
