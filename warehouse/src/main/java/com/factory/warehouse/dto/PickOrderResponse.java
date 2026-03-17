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
public class PickOrderResponse {

    private String pickListId;
    private String productionOrderId;
    private String status; // PENDING, IN_PROGRESS, PICKED, DELIVERED
    private LocalDateTime createdAt;
    private LocalDateTime pickedAt;
    private String assignedOperatorId;
    private String destinationLocation;
    private List<PickedItemDetail> items;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PickedItemDetail {
        private String materialId;
        private Double requestedQuantity;
        private Double pickedQuantity;
        private String unit;
        private String sourceLocation;
    }
}
