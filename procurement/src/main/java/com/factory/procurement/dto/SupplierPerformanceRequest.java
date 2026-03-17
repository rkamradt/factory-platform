package com.factory.procurement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierPerformanceRequest {

    @NotBlank(message = "Purchase order ID is required")
    private String purchaseOrderId;

    private Double qualityScore; // 0-100
    private Double deliveryScore; // 0-100
    private Double pricingScore; // 0-100
    private Integer daysEarlyOrLate; // positive = late, negative = early
    private String feedback;
}
