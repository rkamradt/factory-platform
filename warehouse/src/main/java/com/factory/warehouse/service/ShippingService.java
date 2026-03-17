package com.factory.warehouse.service;

import com.factory.warehouse.dto.ShipProductsRequest;
import com.factory.warehouse.dto.ShipProductsResponse;
import com.factory.warehouse.event.WarehouseEventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShippingService {

    private static final Logger logger = LoggerFactory.getLogger(ShippingService.class);
    private final WarehouseEventProducer eventProducer;

    public ShippingService(WarehouseEventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    public ShipProductsResponse shipProducts(ShipProductsRequest request) {
        logger.info("Shipping products for order: {} to customer: {}",
                request.getOrderId(), request.getCustomerId());

        // TODO: Implement actual shipping logic
        // TODO: Pack products
        // TODO: Generate shipping labels
        // TODO: Update inventory
        // TODO: Integrate with carrier systems
        // TODO: Persist to database

        String shipmentId = UUID.randomUUID().toString();

        List<ShipProductsResponse.ShippedProductDetail> products = request.getProducts().stream()
                .map(product -> ShipProductsResponse.ShippedProductDetail.builder()
                        .productId(product.getProductId())
                        .productName(product.getProductName())
                        .quantity(product.getQuantity())
                        .serialNumbers(product.getSerialNumbers())
                        .build())
                .collect(Collectors.toList());

        ShipProductsResponse response = ShipProductsResponse.builder()
                .shipmentId(shipmentId)
                .orderId(request.getOrderId())
                .customerId(request.getCustomerId())
                .status("IN_TRANSIT")
                .shippedAt(LocalDateTime.now())
                .shippingAddress(request.getShippingAddress())
                .carrier(request.getCarrier())
                .trackingNumber(request.getTrackingNumber())
                .products(products)
                .build();

        // Publish products shipped event
        Map<String, Object> shipmentDetails = new HashMap<>();
        shipmentDetails.put("customerId", request.getCustomerId());
        shipmentDetails.put("shippingAddress", request.getShippingAddress());
        shipmentDetails.put("carrier", request.getCarrier());
        shipmentDetails.put("trackingNumber", request.getTrackingNumber());
        shipmentDetails.put("productCount", products.size());
        shipmentDetails.put("products", products);

        eventProducer.publishProductsShipped(shipmentId, request.getOrderId(), shipmentDetails);

        return response;
    }
}
