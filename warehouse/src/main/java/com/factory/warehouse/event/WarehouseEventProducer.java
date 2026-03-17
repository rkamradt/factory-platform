package com.factory.warehouse.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class WarehouseEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(WarehouseEventProducer.class);

    private static final String GOODS_RECEIVED_TOPIC = "warehouse.goods.received";
    private static final String MATERIALS_PICKED_TOPIC = "warehouse.materials.picked";
    private static final String PRODUCTS_SHIPPED_TOPIC = "warehouse.products.shipped";
    private static final String INVENTORY_DISCREPANCY_TOPIC = "warehouse.inventory.discrepancy";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public WarehouseEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Publish event when materials are physically received and stored
     */
    public void publishGoodsReceived(String receiptId, String purchaseOrderId, Map<String, Object> receiptDetails) {
        logger.info("Publishing goods received event - receiptId: {}, poId: {}", receiptId, purchaseOrderId);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "GOODS_RECEIVED");
        event.put("receiptId", receiptId);
        event.put("purchaseOrderId", purchaseOrderId);
        event.put("receivedAt", LocalDateTime.now().toString());
        event.put("details", receiptDetails);

        kafkaTemplate.send(GOODS_RECEIVED_TOPIC, receiptId, event);
    }

    /**
     * Publish event when materials are picked and ready for production
     */
    public void publishMaterialsPicked(String pickListId, String productionOrderId, Map<String, Object> pickDetails) {
        logger.info("Publishing materials picked event - pickListId: {}, orderId: {}", pickListId, productionOrderId);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "MATERIALS_PICKED");
        event.put("pickListId", pickListId);
        event.put("productionOrderId", productionOrderId);
        event.put("pickedAt", LocalDateTime.now().toString());
        event.put("details", pickDetails);

        kafkaTemplate.send(MATERIALS_PICKED_TOPIC, pickListId, event);
    }

    /**
     * Publish event when finished products are shipped to customer
     */
    public void publishProductsShipped(String shipmentId, String orderId, Map<String, Object> shipmentDetails) {
        logger.info("Publishing products shipped event - shipmentId: {}, orderId: {}", shipmentId, orderId);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "PRODUCTS_SHIPPED");
        event.put("shipmentId", shipmentId);
        event.put("orderId", orderId);
        event.put("shippedAt", LocalDateTime.now().toString());
        event.put("details", shipmentDetails);

        kafkaTemplate.send(PRODUCTS_SHIPPED_TOPIC, shipmentId, event);
    }

    /**
     * Publish event when physical count differs from system inventory
     */
    public void publishInventoryDiscrepancy(String reconciliationId, String locationId, Map<String, Object> discrepancyDetails) {
        logger.info("Publishing inventory discrepancy event - reconciliationId: {}, locationId: {}",
                reconciliationId, locationId);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "INVENTORY_DISCREPANCY");
        event.put("reconciliationId", reconciliationId);
        event.put("locationId", locationId);
        event.put("detectedAt", LocalDateTime.now().toString());
        event.put("discrepancies", discrepancyDetails);

        kafkaTemplate.send(INVENTORY_DISCREPANCY_TOPIC, reconciliationId, event);
    }
}
