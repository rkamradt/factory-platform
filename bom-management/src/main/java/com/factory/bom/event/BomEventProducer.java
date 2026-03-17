package com.factory.bom.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class BomEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(BomEventProducer.class);
    private static final String BOM_VERSION_PUBLISHED_TOPIC = "bom.version.published";
    private static final String COMPONENT_SPECIFICATION_CHANGED_TOPIC = "bom.component.specification.changed";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public BomEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Publish event when new BOM version is published and ready for use
     */
    public void publishBomVersionPublished(String bomId, String productId, String version) {
        logger.info("Publishing BOM version published event - bomId: {}, productId: {}, version: {}",
                bomId, productId, version);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "BOM_VERSION_PUBLISHED");
        event.put("bomId", bomId);
        event.put("productId", productId);
        event.put("version", version);
        event.put("publishedAt", LocalDateTime.now().toString());
        event.put("status", "PUBLISHED");

        kafkaTemplate.send(BOM_VERSION_PUBLISHED_TOPIC, bomId, event);
    }

    /**
     * Publish event when component specifications or requirements have been updated
     */
    public void publishComponentSpecificationChanged(String componentId, Map<String, Object> specifications) {
        logger.info("Publishing component specification changed event - componentId: {}", componentId);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "COMPONENT_SPECIFICATION_CHANGED");
        event.put("componentId", componentId);
        event.put("specifications", specifications);
        event.put("changedAt", LocalDateTime.now().toString());

        kafkaTemplate.send(COMPONENT_SPECIFICATION_CHANGED_TOPIC, componentId, event);
    }
}
