package com.factory.assembly.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class AssemblyEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(AssemblyEventProducer.class);

    private static final String ASSEMBLY_ORDER_COMPLETED_TOPIC = "assembly.order.completed";
    private static final String ASSEMBLY_LINE_BREAKDOWN_TOPIC = "assembly.line.breakdown";
    private static final String ASSEMBLY_QUALITY_ISSUE_DETECTED_TOPIC = "assembly.quality.issue.detected";
    private static final String ASSEMBLY_COMPONENT_CONSUMED_TOPIC = "assembly.component.consumed";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public AssemblyEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Publish event when finished product is ready for shipping or final inspection
     */
    public void publishAssemblyOrderCompleted(String orderId, Integer unitsProduced) {
        logger.info("Publishing assembly order completed event - orderId: {}, units: {}", orderId, unitsProduced);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "ASSEMBLY_ORDER_COMPLETED");
        event.put("orderId", orderId);
        event.put("unitsProduced", unitsProduced);
        event.put("completedAt", LocalDateTime.now().toString());
        event.put("status", "READY_FOR_INSPECTION");

        kafkaTemplate.send(ASSEMBLY_ORDER_COMPLETED_TOPIC, orderId, event);
    }

    /**
     * Publish event when assembly line experiences downtime affecting production capacity
     */
    public void publishAssemblyLineBreakdown(String lineId, String downtimeReason, Integer estimatedDowntimeMinutes) {
        logger.warn("Publishing assembly line breakdown event - lineId: {}, reason: {}", lineId, downtimeReason);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "ASSEMBLY_LINE_BREAKDOWN");
        event.put("lineId", lineId);
        event.put("downtimeReason", downtimeReason);
        event.put("estimatedDowntimeMinutes", estimatedDowntimeMinutes);
        event.put("reportedAt", LocalDateTime.now().toString());
        event.put("impactedCapacity", true);

        kafkaTemplate.send(ASSEMBLY_LINE_BREAKDOWN_TOPIC, lineId, event);
    }

    /**
     * Publish event when assembly problem is detected requiring possible rework
     */
    public void publishAssemblyQualityIssueDetected(String orderId, String issueDescription) {
        logger.warn("Publishing assembly quality issue detected event - orderId: {}, issue: {}", orderId, issueDescription);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "ASSEMBLY_QUALITY_ISSUE_DETECTED");
        event.put("orderId", orderId);
        event.put("issueDescription", issueDescription);
        event.put("detectedAt", LocalDateTime.now().toString());
        event.put("requiresRework", true);

        kafkaTemplate.send(ASSEMBLY_QUALITY_ISSUE_DETECTED_TOPIC, orderId, event);
    }

    /**
     * Publish event when component is consumed in assembly process with yield tracking
     */
    public void publishAssemblyComponentConsumed(String orderId, String componentId,
                                                 Integer quantityConsumed, Double yieldPercentage) {
        logger.info("Publishing assembly component consumed event - orderId: {}, componentId: {}, quantity: {}",
                orderId, componentId, quantityConsumed);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "ASSEMBLY_COMPONENT_CONSUMED");
        event.put("orderId", orderId);
        event.put("componentId", componentId);
        event.put("quantityConsumed", quantityConsumed);
        event.put("yieldPercentage", yieldPercentage);
        event.put("consumedAt", LocalDateTime.now().toString());

        kafkaTemplate.send(ASSEMBLY_COMPONENT_CONSUMED_TOPIC, componentId, event);
    }
}
