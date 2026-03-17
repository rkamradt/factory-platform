package com.factory.procurement.service;

import com.factory.procurement.dto.SupplierCatalogResponse;
import com.factory.procurement.dto.SupplierPerformanceRequest;
import com.factory.procurement.event.ProcurementEventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SupplierService {

    private static final Logger logger = LoggerFactory.getLogger(SupplierService.class);
    private final ProcurementEventProducer eventProducer;

    public SupplierService(ProcurementEventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    public SupplierCatalogResponse getSupplierCatalog(String supplierId, String searchTerm) {
        logger.info("Retrieving catalog for supplier: {}, searchTerm: {}", supplierId, searchTerm);

        // TODO: Implement catalog retrieval logic
        // TODO: Query supplier's available materials
        // TODO: Apply search filter if provided
        // TODO: Include pricing and availability

        List<SupplierCatalogResponse.CatalogItem> items = new ArrayList<>();

        // Sample catalog items
        items.add(SupplierCatalogResponse.CatalogItem.builder()
                .materialId("MAT-001")
                .materialName("Steel Plate")
                .description("High-grade steel plate for manufacturing")
                .unitPrice(25.50)
                .unit("kg")
                .leadTimeDays(5)
                .minimumOrderQuantity(100)
                .availability("IN_STOCK")
                .specifications("Grade A36, 10mm thickness")
                .build());

        items.add(SupplierCatalogResponse.CatalogItem.builder()
                .materialId("MAT-002")
                .materialName("Aluminum Rod")
                .description("6061 aluminum rod")
                .unitPrice(18.75)
                .unit("meter")
                .leadTimeDays(3)
                .minimumOrderQuantity(50)
                .availability("IN_STOCK")
                .specifications("6061-T6, 25mm diameter")
                .build());

        return SupplierCatalogResponse.builder()
                .supplierId(supplierId)
                .supplierName("Sample Supplier")
                .items(items)
                .build();
    }

    public Map<String, Object> updateSupplierPerformance(String supplierId, SupplierPerformanceRequest request) {
        logger.info("Updating performance metrics for supplier: {}, PO: {}",
                supplierId, request.getPurchaseOrderId());

        // TODO: Implement performance tracking logic
        // TODO: Store performance metrics in database
        // TODO: Calculate overall supplier rating
        // TODO: Update supplier status based on performance

        Double overallScore = 0.0;
        int scoreCount = 0;

        if (request.getQualityScore() != null) {
            overallScore += request.getQualityScore();
            scoreCount++;
        }
        if (request.getDeliveryScore() != null) {
            overallScore += request.getDeliveryScore();
            scoreCount++;
        }
        if (request.getPricingScore() != null) {
            overallScore += request.getPricingScore();
            scoreCount++;
        }

        if (scoreCount > 0) {
            overallScore = overallScore / scoreCount;
        }

        Map<String, Object> response = new HashMap<>();
        response.put("supplierId", supplierId);
        response.put("purchaseOrderId", request.getPurchaseOrderId());
        response.put("qualityScore", request.getQualityScore());
        response.put("deliveryScore", request.getDeliveryScore());
        response.put("pricingScore", request.getPricingScore());
        response.put("overallScore", overallScore);
        response.put("daysEarlyOrLate", request.getDaysEarlyOrLate());
        response.put("feedback", request.getFeedback());
        response.put("updatedAt", LocalDateTime.now());

        // Publish event
        eventProducer.publishSupplierPerformanceUpdated(supplierId, request.getPurchaseOrderId(), overallScore);

        return response;
    }
}
