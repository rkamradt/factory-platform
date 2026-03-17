package com.factory.machining.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MachiningEventListener {

    private static final Logger logger = LoggerFactory.getLogger(MachiningEventListener.class);

    /**
     * Listen to production.work.order.scheduled events
     * Work order assigned to machining workshop for execution
     */
    @KafkaListener(topics = "production.work.order.scheduled", groupId = "machining-workshop-service")
    public void handleProductionWorkOrderScheduled(Map<String, Object> event) {
        logger.info("Received production.work.order.scheduled event: {}", event);

        String workOrderId = (String) event.get("workOrderId");
        String workshopType = (String) event.get("workshopType");

        // Only process if this work order is for machining workshop
        if ("MACHINING".equals(workshopType)) {
            logger.info("Processing work order for machining workshop: {}", workOrderId);

            // TODO: Implement logic to:
            // 1. Validate work order requirements
            // 2. Check machine availability and capacity
            // 3. Verify material availability
            // 4. Create internal machining work order
            // 5. Queue for scheduling on appropriate machines
        } else {
            logger.debug("Ignoring work order for workshop: {}", workshopType);
        }
    }

    /**
     * Listen to quality.inspection.completed events
     * Quality results may require rework or process adjustments
     */
    @KafkaListener(topics = "quality.inspection.completed", groupId = "machining-workshop-service")
    public void handleQualityInspectionCompleted(Map<String, Object> event) {
        logger.info("Received quality.inspection.completed event: {}", event);

        String inspectionId = (String) event.get("inspectionId");
        String workOrderId = (String) event.get("workOrderId");
        String result = (String) event.get("result"); // PASS, FAIL

        logger.info("Quality inspection {} for work order {} - Result: {}", inspectionId, workOrderId, result);

        // TODO: Implement logic to:
        // 1. Update work order quality status
        // 2. If FAIL, determine if rework is needed
        // 3. If rework required, create rework task
        // 4. Analyze failure patterns for process improvement
        // 5. Update machine/operator performance metrics
        // 6. If PASS, proceed to completion or next stage
    }

    /**
     * Listen to inventory.material.reserved events
     * Confirms raw materials available for machining work order
     */
    @KafkaListener(topics = "inventory.material.reserved", groupId = "machining-workshop-service")
    public void handleMaterialReserved(Map<String, Object> event) {
        logger.info("Received inventory.material.reserved event: {}", event);

        String orderId = (String) event.get("orderId");
        String reservationId = (String) event.get("reservationId");

        logger.info("Materials reserved for order: {} with reservation: {}", orderId, reservationId);

        // TODO: Implement logic to:
        // 1. Link reservation to machining work order
        // 2. Update work order status to MATERIALS_READY
        // 3. Prioritize work order for scheduling
        // 4. Notify scheduling system that materials are available
        // 5. Coordinate with warehouse for material pickup
    }
}
