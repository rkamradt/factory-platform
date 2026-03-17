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
public class DeliveryScheduleResponse {

    private List<ScheduledDelivery> deliveries;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScheduledDelivery {
        private String purchaseOrderId;
        private String supplierId;
        private String supplierName;
        private LocalDateTime expectedDeliveryDate;
        private String status;
        private List<MaterialDelivery> materials;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MaterialDelivery {
        private String materialId;
        private String materialName;
        private Double quantity;
        private String unit;
    }
}
