package com.factory.fabrication.event;

import com.factory.fabrication.dto.MaterialConsumptionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FabricationEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(FabricationEventProducer.class);

    private static final String WORK_ORDER_COMPLETED_TOPIC = "fabrication.work.order.completed";
    private static final String EQUIPMENT_MAINTENANCE_REQUIRED_TOPIC = "fabrication.equipment.maintenance.required";
    private static final String QUALITY_ISSUE_DETECTED_TOPIC = "fabrication.quality.issue.detected";
    private static final String MATERIAL_CONSUMED_TOPIC = "fabrication.material.consumed";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public FabricationEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Publish event when fabrication work is completed and component is ready for next production stage
     */
    public void publishWorkOrderCompleted(String workOrderId, String stage) {
        logger.info("Publishing work order completed event - workOrderId: {}, stage: {}", workOrderId, stage);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "WORK_ORDER_COMPLETED");
        event.put("workOrderId", workOrderId);
        event.put("completedStage", stage);
        event.put("completedAt", LocalDateTime.now().toString());
        event.put("componentStatus", "READY_FOR_NEXT_STAGE");

        kafkaTemplate.send(WORK_ORDER_COMPLETED_TOPIC, workOrderId, event);
    }

    /**
     * Publish event when equipment breakdown or scheduled maintenance affects production capacity
     */
    public void publishEquipmentMaintenanceRequired(String equipmentId, String equipmentType,
                                                   String maintenanceType, String reason,
                                                   LocalDateTime expectedAvailableAt) {
        logger.info("Publishing equipment maintenance required event - equipmentId: {}, type: {}, reason: {}",
                equipmentId, maintenanceType, reason);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "EQUIPMENT_MAINTENANCE_REQUIRED");
        event.put("equipmentId", equipmentId);
        event.put("equipmentType", equipmentType);
        event.put("maintenanceType", maintenanceType);
        event.put("reason", reason);
        event.put("reportedAt", LocalDateTime.now().toString());
        event.put("expectedAvailableAt", expectedAvailableAt != null ? expectedAvailableAt.toString() : null);
        event.put("impactsCapacity", true);

        kafkaTemplate.send(EQUIPMENT_MAINTENANCE_REQUIRED_TOPIC, equipmentId, event);
    }

    /**
     * Publish event when fabrication quality problem is detected requiring possible rework
     */
    public void publishQualityIssueDetected(String workOrderId, String stage, String issueDescription) {
        logger.info("Publishing quality issue detected event - workOrderId: {}, stage: {}", workOrderId, stage);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "QUALITY_ISSUE_DETECTED");
        event.put("workOrderId", workOrderId);
        event.put("stage", stage);
        event.put("issueDescription", issueDescription);
        event.put("detectedAt", LocalDateTime.now().toString());
        event.put("requiresRework", true);
        event.put("severity", "MEDIUM"); // TODO: Implement severity classification

        kafkaTemplate.send(QUALITY_ISSUE_DETECTED_TOPIC, workOrderId, event);
    }

    /**
     * Publish event when material is consumed in fabrication process with waste tracking
     */
    public void publishMaterialConsumed(String workOrderId, String stage,
                                       List<MaterialConsumptionRequest.MaterialItem> materials,
                                       Double totalWasteRate) {
        logger.info("Publishing material consumed event - workOrderId: {}, stage: {}, materials: {}, wasteRate: {}%",
                workOrderId, stage, materials.size(), totalWasteRate);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "MATERIAL_CONSUMED");
        event.put("workOrderId", workOrderId);
        event.put("stage", stage);
        event.put("materials", materials);
        event.put("totalWasteRate", totalWasteRate);
        event.put("consumedAt", LocalDateTime.now().toString());

        kafkaTemplate.send(MATERIAL_CONSUMED_TOPIC, workOrderId, event);
    }
}
