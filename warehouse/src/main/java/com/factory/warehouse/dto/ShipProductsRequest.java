package com.factory.warehouse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipProductsRequest {

    @NotBlank(message = "Customer ID is required")
    private String customerId;

    @NotBlank(message = "Order ID is required")
    private String orderId;

    @NotNull(message = "Products to ship are required")
    private List<ShipItem> products;

    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;

    private String carrier;
    private String trackingNumber;
    private String notes;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShipItem {
        @NotBlank(message = "Product ID is required")
        private String productId;

        @NotBlank(message = "Product name is required")
        private String productName;

        @NotNull(message = "Quantity is required")
        private Integer quantity;

        private String serialNumbers;
        private String sourceLocation;
    }
}
