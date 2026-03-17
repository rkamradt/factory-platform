package com.factory.warehouse.dto;

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
public class ReceiveGoodsResponse {

    private String receiptId;
    private String purchaseOrderId;
    private String supplierId;
    private String status; // RECEIVED, PENDING_QUALITY, STORED
    private LocalDateTime receivedAt;
    private String dockLocation;
    private List<ReceivedItemDetail> items;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReceivedItemDetail {
        private String materialId;
        private String materialName;
        private Double quantity;
        private String unit;
        private String storageLocation;
        private String qualityStatus;
    }
}
