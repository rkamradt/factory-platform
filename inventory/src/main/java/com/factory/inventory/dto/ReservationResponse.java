package com.factory.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponse {

    private String reservationId;
    private String productionOrderId;
    private List<ReservedMaterial> reservedMaterials;
    private LocalDateTime reservedAt;
    private String status;
    private String message;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReservedMaterial {
        private String materialId;
        private Double quantity;
        private String unit;
        private String locationId;
        private String reservationItemId;
    }
}
