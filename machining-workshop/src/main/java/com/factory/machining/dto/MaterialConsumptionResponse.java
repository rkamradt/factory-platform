package com.factory.machining.dto;

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
public class MaterialConsumptionResponse {

    private String consumptionId;
    private String workOrderId;
    private String orderId;
    private LocalDateTime recordedAt;
    private List<MaterialUsageDetail> materials;
    private Double totalScrapRate;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MaterialUsageDetail {
        private String materialId;
        private String materialName;
        private Double consumedQuantity;
        private Double scrapQuantity;
        private String unit;
        private Double scrapRate;
    }
}
