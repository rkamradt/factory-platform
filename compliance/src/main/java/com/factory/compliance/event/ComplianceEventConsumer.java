package com.factory.compliance.event;

import com.factory.compliance.dto.LogMovementRequest;
import com.factory.compliance.service.ComplianceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class ComplianceEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ComplianceEventConsumer.class);

    private final ComplianceService complianceService;

    public ComplianceEventConsumer(ComplianceService complianceService) {
        this.complianceService = complianceService;
    }

    /**
     * Consume inventory.stock.movement events to log all inventory movements for audit trail
     */
    @KafkaListener(topics = "inventory.stock.movement", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeInventoryStockMovement(Map<String, Object> event) {
        logger.info("Received inventory.stock.movement event: {}", event);

        try {
            // Extract event data
            String materialId = (String) event.get("materialId");
            Object quantityObj = event.get("quantity");
            Double quantity = quantityObj instanceof Number ? ((Number) quantityObj).doubleValue() : null;
            String sourceLocation = (String) event.get("sourceLocation");
            String destinationLocation = (String) event.get("destinationLocation");
            String movementType = (String) event.get("movementType");
            String orderId = (String) event.get("orderId");
            String executedBy = (String) event.get("executedBy");

            // Create log movement request
            LogMovementRequest logRequest = LogMovementRequest.builder()
                    .materialId(materialId)
                    .quantity(quantity)
                    .sourceLocation(sourceLocation != null ? sourceLocation : "UNKNOWN")
                    .destinationLocation(destinationLocation != null ? destinationLocation : "UNKNOWN")
                    .movementType(movementType != null ? movementType : "STOCK_MOVEMENT")
                    .executedBy(executedBy)
                    .executedAt(LocalDateTime.now())
                    .orderId(orderId)
                    .additionalContext(event)
                    .build();

            // Log the movement in audit trail
            complianceService.logMovement(logRequest);

            logger.info("Successfully logged inventory stock movement for material: {}", materialId);
        } catch (Exception e) {
            logger.error("Error processing inventory.stock.movement event: {}", e.getMessage(), e);
            // TODO: Implement error handling and retry logic
        }
    }

    /**
     * Consume inventory.material.reserved events to log material reservations for audit trail
     */
    @KafkaListener(topics = "inventory.material.reserved", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeInventoryMaterialReserved(Map<String, Object> event) {
        logger.info("Received inventory.material.reserved event: {}", event);

        try {
            // Extract event data
            String materialId = (String) event.get("materialId");
            Object quantityObj = event.get("quantity");
            Double quantity = quantityObj instanceof Number ? ((Number) quantityObj).doubleValue() : null;
            String location = (String) event.get("location");
            String orderId = (String) event.get("orderId");
            String reservedBy = (String) event.get("reservedBy");

            // Create log movement request for reservation
            LogMovementRequest logRequest = LogMovementRequest.builder()
                    .materialId(materialId)
                    .quantity(quantity)
                    .sourceLocation(location != null ? location : "UNKNOWN")
                    .destinationLocation("RESERVED")
                    .movementType("RESERVATION")
                    .executedBy(reservedBy)
                    .executedAt(LocalDateTime.now())
                    .orderId(orderId)
                    .additionalContext(event)
                    .build();

            // Log the reservation in audit trail
            complianceService.logMovement(logRequest);

            logger.info("Successfully logged material reservation for material: {}, order: {}", materialId, orderId);
        } catch (Exception e) {
            logger.error("Error processing inventory.material.reserved event: {}", e.getMessage(), e);
            // TODO: Implement error handling and retry logic
        }
    }
}
