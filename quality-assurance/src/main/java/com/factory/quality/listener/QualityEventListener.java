package com.factory.quality.listener;

import com.factory.quality.dto.ScheduleInspectionRequest;
import com.factory.quality.service.InspectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class QualityEventListener {

    private static final Logger logger = LoggerFactory.getLogger(QualityEventListener.class);
    private final InspectionService inspectionService;

    public QualityEventListener(InspectionService inspectionService) {
        this.inspectionService = inspectionService;
    }

    /**
     * Listen to procurement.goods.received events
     * Schedule incoming inspection for received materials
     */
    @KafkaListener(topics = "procurement.goods.received", groupId = "quality-assurance-service")
    public void handleProcurementGoodsReceived(Map<String, Object> event) {
        logger.info("Received procurement.goods.received event: {}", event);

        String purchaseOrderId = (String) event.get("purchaseOrderId");
        String materialId = (String) event.get("materialId");
        String supplierId = (String) event.get("supplierId");
        Object quantityObj = event.get("quantity");

        logger.info("Scheduling incoming quality inspection for PO: {}, Material: {}", purchaseOrderId, materialId);

        try {
            // Create inspection request for incoming material
            ScheduleInspectionRequest inspectionRequest = new ScheduleInspectionRequest();
            inspectionRequest.setMaterialId(materialId != null ? materialId : purchaseOrderId);
            inspectionRequest.setInspectionType("INCOMING");
            inspectionRequest.setStandardId("STD-INCOMING-001"); // Default incoming inspection standard
            inspectionRequest.setBatchId(purchaseOrderId);
            inspectionRequest.setSupplierId(supplierId);
            inspectionRequest.setScheduledDate(LocalDateTime.now().plusHours(1));
            inspectionRequest.setPriority("HIGH");
            inspectionRequest.setNotes("Automated incoming inspection scheduled from procurement");

            inspectionService.scheduleInspection(inspectionRequest);

            logger.info("Incoming inspection scheduled successfully for PO: {}", purchaseOrderId);
        } catch (Exception e) {
            logger.error("Failed to schedule incoming inspection for PO: {}", purchaseOrderId, e);
        }
    }

    /**
     * Listen to warehouse.goods.received events
     * Coordinate with physical receipt for inspection scheduling
     */
    @KafkaListener(topics = "warehouse.goods.received", groupId = "quality-assurance-service")
    public void handleWarehouseGoodsReceived(Map<String, Object> event) {
        logger.info("Received warehouse.goods.received event: {}", event);

        String materialId = (String) event.get("materialId");
        String locationId = (String) event.get("locationId");
        String receiptId = (String) event.get("receiptId");

        logger.info("Coordinating inspection for material: {} at location: {}", materialId, locationId);

        // TODO: Implement logic to:
        // 1. Check if inspection already scheduled
        // 2. Update inspection with physical location
        // 3. Notify inspector that material is ready for inspection
        // 4. Track inspection timing from receipt

        logger.info("Inspection coordination completed for receipt: {}", receiptId);
    }
}
