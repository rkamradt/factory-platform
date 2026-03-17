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
public class ShipProductsResponse {

    private String shipmentId;
    private String orderId;
    private String customerId;
    private String status; // PACKED, IN_TRANSIT, DELIVERED
    private LocalDateTime shippedAt;
    private String shippingAddress;
    private String carrier;
    private String trackingNumber;
    private List<ShippedProductDetail> products;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShippedProductDetail {
        private String productId;
        private String productName;
        private Integer quantity;
        private String serialNumbers;
    }
}
