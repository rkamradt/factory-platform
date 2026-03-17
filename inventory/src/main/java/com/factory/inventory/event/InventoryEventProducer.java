package com.factory.inventory.event;

import com.factory.inventory.dto.ReservationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InventoryEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(InventoryEventProducer.class);
    private static final String STOCK_CHANGED_TOPIC = "inventory.stock.changed";
    private static final String MATERIAL_RESERVED_TOPIC = "inventory.material.reserved";
    private static final String STOCK_MOVEMENT_TOPIC = "inventory.stock.movement";
    private static final String LOW_STOCK_ALERT_TOPIC = "inventory.low.stock.alert";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public InventoryEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Publish event when stock level changed for a material at a location
     */
    public void publishStockChanged(String materialId, String locationId, Double quantity) {
        logger.info("Publishing stock changed event - materialId: {}, locationId: {}, quantity: {}",
                materialId, locationId, quantity);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "STOCK_CHANGED");
        event.put("materialId", materialId);
        event.put("locationId", locationId);
        event.put("quantity", quantity);
        event.put("changedAt", LocalDateTime.now().toString());

        kafkaTemplate.send(STOCK_CHANGED_TOPIC, materialId, event);
    }

    /**
     * Publish event when materials are reserved for production order
     */
    public void publishMaterialReserved(String reservationId, String productionOrderId,
                                        List<ReservationResponse.ReservedMaterial> materials) {
        logger.info("Publishing material reserved event - reservationId: {}, productionOrderId: {}",
                reservationId, productionOrderId);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "MATERIAL_RESERVED");
        event.put("reservationId", reservationId);
        event.put("productionOrderId", productionOrderId);
        event.put("materials", materials);
        event.put("reservedAt", LocalDateTime.now().toString());

        kafkaTemplate.send(MATERIAL_RESERVED_TOPIC, reservationId, event);
    }

    /**
     * Publish event when material moved between locations or consumed/received
     */
    public void publishStockMovement(String movementId, String materialId, String movementType,
                                      Double quantity, String fromLocationId, String toLocationId) {
        logger.info("Publishing stock movement event - movementId: {}, materialId: {}, type: {}",
                movementId, materialId, movementType);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "STOCK_MOVEMENT");
        event.put("movementId", movementId);
        event.put("materialId", materialId);
        event.put("movementType", movementType);
        event.put("quantity", quantity);
        event.put("fromLocationId", fromLocationId);
        event.put("toLocationId", toLocationId);
        event.put("movedAt", LocalDateTime.now().toString());

        kafkaTemplate.send(STOCK_MOVEMENT_TOPIC, movementId, event);
    }

    /**
     * Publish event when material has fallen below reorder point
     */
    public void publishLowStockAlert(String materialId, String locationId, Double currentQuantity,
                                      Double reorderPoint) {
        logger.info("Publishing low stock alert - materialId: {}, locationId: {}, current: {}, reorder: {}",
                materialId, locationId, currentQuantity, reorderPoint);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "LOW_STOCK_ALERT");
        event.put("materialId", materialId);
        event.put("locationId", locationId);
        event.put("currentQuantity", currentQuantity);
        event.put("reorderPoint", reorderPoint);
        event.put("severity", "WARNING");
        event.put("alertedAt", LocalDateTime.now().toString());

        kafkaTemplate.send(LOW_STOCK_ALERT_TOPIC, materialId, event);
    }
}
