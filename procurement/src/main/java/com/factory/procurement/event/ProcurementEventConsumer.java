package com.factory.procurement.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProcurementEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ProcurementEventConsumer.class);

    /**
     * Consume inventory.low.stock.alert events
     * Triggered when material has fallen below reorder point - triggers purchasing
     */
    @KafkaListener(topics = "inventory.low.stock.alert", groupId = "procurement-service")
    public void handleLowStockAlert(Map<String, Object> event) {
        logger.info("Received low stock alert event: {}", event);

        // TODO: Implement low stock alert handling
        // TODO: Check if material has preferred supplier
        // TODO: Check reorder point and reorder quantity settings
        // TODO: Determine if automatic reorder should be triggered
        // TODO: Create purchase order if conditions are met
        // TODO: Or create notification for manual review

        String materialId = (String) event.get("materialId");
        Object currentStock = event.get("currentStock");
        Object reorderPoint = event.get("reorderPoint");

        logger.info("Material {} is below reorder point. Current: {}, Reorder point: {}",
                materialId, currentStock, reorderPoint);

        // Sample logic - in production, this would trigger actual reorder workflow
        logger.info("Automated reorder process should be triggered for material: {}", materialId);
    }

    /**
     * Consume bom.component.specification.changed events
     * Triggered when component specs changed - may affect supplier requirements
     */
    @KafkaListener(topics = "bom.component.specification.changed", groupId = "procurement-service")
    public void handleComponentSpecificationChanged(Map<String, Object> event) {
        logger.info("Received component specification changed event: {}", event);

        // TODO: Implement component specification change handling
        // TODO: Check if affected components are currently on order
        // TODO: Verify supplier can meet new specifications
        // TODO: Update purchase orders if needed
        // TODO: Notify procurement team of specification changes
        // TODO: May need to source alternative suppliers

        String componentId = (String) event.get("componentId");
        Object specifications = event.get("specifications");

        logger.info("Component {} specifications changed: {}", componentId, specifications);

        // Sample logic - in production, this would trigger supplier verification
        logger.info("Review open purchase orders for component: {}", componentId);
        logger.info("Verify suppliers can meet new specifications");
    }
}
