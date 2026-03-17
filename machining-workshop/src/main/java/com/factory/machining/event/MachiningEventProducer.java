package com.factory.machining.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class MachiningEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(MachiningEventProducer.class);

    private static final String WORK_ORDER_COMPLETED_TOPIC = "machining.work.order.completed";
    private static final String MACHINE_MAINTENANCE_REQUIRED_TOPIC = "machining.machine.maintenance.required";
    private static final String QUALITY_ISSUE_DETECTED_TOPIC = "machining.quality.issue.detected";
    private static final String MATERIAL_CONSUMED_TOPIC = "machining.material.consumed";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public MachiningEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Publish event when machining work is completed and component is ready for next production stage
     */
    public void publishWorkOrderCompleted(String workOrderId, String orderId, Map<String, Object> completionDetails) {
        logger.info("Publishing work order completed event - workOrderId: {}, orderId: {}", workOrderId, orderId);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "WORK_ORDER_COMPLETED");
        event.put("workOrderId", workOrderId);
        event.put("orderId", orderId);
        event.put("completedAt", LocalDateTime.now().toString());
        event.put("details", completionDetails);

        kafkaTemplate.send(WORK_ORDER_COMPLETED_TOPIC, workOrderId, event);
    }

    /**
     * Publish event when machine breakdown or scheduled maintenance affects production capacity
     */
    public void publishMachineMaintenanceRequired(String machineId, String maintenanceType, Map<String, Object> maintenanceDetails) {
        logger.info("Publishing machine maintenance required event - machineId: {}, type: {}", machineId, maintenanceType);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "MACHINE_MAINTENANCE_REQUIRED");
        event.put("machineId", machineId);
        event.put("maintenanceType", maintenanceType); // PREVENTIVE, CORRECTIVE, EMERGENCY
        event.put("detectedAt", LocalDateTime.now().toString());
        event.put("details", maintenanceDetails);

        kafkaTemplate.send(MACHINE_MAINTENANCE_REQUIRED_TOPIC, machineId, event);
    }

    /**
     * Publish event when machining quality problem is detected requiring possible rework
     */
    public void publishQualityIssueDetected(String workOrderId, String issueType, Map<String, Object> issueDetails) {
        logger.info("Publishing quality issue detected event - workOrderId: {}, issueType: {}", workOrderId, issueType);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "QUALITY_ISSUE_DETECTED");
        event.put("workOrderId", workOrderId);
        event.put("issueType", issueType);
        event.put("detectedAt", LocalDateTime.now().toString());
        event.put("details", issueDetails);

        kafkaTemplate.send(QUALITY_ISSUE_DETECTED_TOPIC, workOrderId, event);
    }

    /**
     * Publish event when raw material is consumed in machining process with scrap tracking
     */
    public void publishMaterialConsumed(String workOrderId, String consumptionId, Map<String, Object> consumptionDetails) {
        logger.info("Publishing material consumed event - workOrderId: {}, consumptionId: {}", workOrderId, consumptionId);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "MATERIAL_CONSUMED");
        event.put("workOrderId", workOrderId);
        event.put("consumptionId", consumptionId);
        event.put("consumedAt", LocalDateTime.now().toString());
        event.put("details", consumptionDetails);

        kafkaTemplate.send(MATERIAL_CONSUMED_TOPIC, consumptionId, event);
    }
}
