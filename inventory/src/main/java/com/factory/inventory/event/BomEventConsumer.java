package com.factory.inventory.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BomEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(BomEventConsumer.class);

    /**
     * Consume event when new BOM is published - may need to track new materials
     */
    @KafkaListener(topics = "bom.version.published", groupId = "inventory-service")
    public void consumeBomVersionPublished(Map<String, Object> event) {
        logger.info("Received BOM version published event: {}", event);

        String bomId = (String) event.get("bomId");
        String productId = (String) event.get("productId");
        String version = (String) event.get("version");

        logger.info("Processing BOM publication - bomId: {}, productId: {}, version: {}",
                bomId, productId, version);

        // TODO: Implement logic to track new materials from BOM
        // This could involve:
        // 1. Fetching BOM details from BOM Management Service
        // 2. Identifying new materials not yet in inventory system
        // 3. Creating inventory records for new materials
        // 4. Setting up initial stock levels and locations

        logger.info("BOM version published event processed successfully");
    }
}
