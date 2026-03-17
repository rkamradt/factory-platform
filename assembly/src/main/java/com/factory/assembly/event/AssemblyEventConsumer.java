package com.factory.assembly.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AssemblyEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(AssemblyEventConsumer.class);

    /**
     * Consume production.work.order.scheduled events
     * Work order assigned to assembly line for execution
     */
    @KafkaListener(topics = "production.work.order.scheduled", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeProductionWorkOrderScheduled(Map<String, Object> event) {
        logger.info("Received production.work.order.scheduled event: {}", event);

        try {
            String orderId = (String) event.get("orderId");
            String workOrderType = (String) event.get("workOrderType");

            // Only process assembly work orders
            if ("ASSEMBLY".equalsIgnoreCase(workOrderType)) {
                logger.info("Processing assembly work order: {}", orderId);
                // TODO: Automatically accept and queue assembly order
                // TODO: Check component availability
                // TODO: Schedule on available assembly line
            } else {
                logger.debug("Ignoring non-assembly work order: {}", orderId);
            }
        } catch (Exception e) {
            logger.error("Error processing production.work.order.scheduled event: {}", e.getMessage(), e);
        }
    }

    /**
     * Consume machining.work.order.completed events
     * Machined components available for assembly
     */
    @KafkaListener(topics = "machining.work.order.completed", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMachiningWorkOrderCompleted(Map<String, Object> event) {
        logger.info("Received machining.work.order.completed event: {}", event);

        try {
            String orderId = (String) event.get("orderId");
            String componentId = (String) event.get("componentId");

            logger.info("Machined component {} is now available from order: {}", componentId, orderId);
            // TODO: Update component availability tracking
            // TODO: Check if all components are ready for assembly
            // TODO: Trigger assembly scheduling if all components ready
        } catch (Exception e) {
            logger.error("Error processing machining.work.order.completed event: {}", e.getMessage(), e);
        }
    }

    /**
     * Consume fabrication.work.order.completed events
     * Fabricated components available for assembly
     */
    @KafkaListener(topics = "fabrication.work.order.completed", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeFabricationWorkOrderCompleted(Map<String, Object> event) {
        logger.info("Received fabrication.work.order.completed event: {}", event);

        try {
            String orderId = (String) event.get("orderId");
            String componentId = (String) event.get("componentId");

            logger.info("Fabricated component {} is now available from order: {}", componentId, orderId);
            // TODO: Update component availability tracking
            // TODO: Check if all components are ready for assembly
            // TODO: Trigger assembly scheduling if all components ready
        } catch (Exception e) {
            logger.error("Error processing fabrication.work.order.completed event: {}", e.getMessage(), e);
        }
    }

    /**
     * Consume quality.inspection.completed events
     * Quality results may require rework or process adjustments
     */
    @KafkaListener(topics = "quality.inspection.completed", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeQualityInspectionCompleted(Map<String, Object> event) {
        logger.info("Received quality.inspection.completed event: {}", event);

        try {
            String orderId = (String) event.get("orderId");
            String result = (String) event.get("result");

            if ("FAIL".equalsIgnoreCase(result) || "REJECTED".equalsIgnoreCase(result)) {
                logger.warn("Quality inspection failed for order: {} - may require rework", orderId);
                // TODO: Mark order for rework
                // TODO: Update assembly schedule
                // TODO: Notify line supervisor
            } else {
                logger.info("Quality inspection passed for order: {}", orderId);
                // TODO: Continue with assembly process
            }
        } catch (Exception e) {
            logger.error("Error processing quality.inspection.completed event: {}", e.getMessage(), e);
        }
    }

    /**
     * Consume inventory.material.reserved events
     * Confirms all components available for assembly
     */
    @KafkaListener(topics = "inventory.material.reserved", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeInventoryMaterialReserved(Map<String, Object> event) {
        logger.info("Received inventory.material.reserved event: {}", event);

        try {
            String orderId = (String) event.get("orderId");
            String materialId = (String) event.get("materialId");

            logger.info("Material {} reserved for order: {}", materialId, orderId);
            // TODO: Track component reservations
            // TODO: Check if all required components are reserved
            // TODO: Mark order as ready to start assembly
        } catch (Exception e) {
            logger.error("Error processing inventory.material.reserved event: {}", e.getMessage(), e);
        }
    }
}
