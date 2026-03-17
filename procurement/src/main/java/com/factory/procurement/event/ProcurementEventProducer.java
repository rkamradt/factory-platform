package com.factory.procurement.event;

import com.factory.procurement.dto.CreatePurchaseOrderRequest;
import com.factory.procurement.dto.GoodsReceiptRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProcurementEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(ProcurementEventProducer.class);

    private static final String PURCHASE_ORDER_CREATED_TOPIC = "procurement.purchase.order.created";
    private static final String MATERIALS_ORDERED_TOPIC = "procurement.materials.ordered";
    private static final String GOODS_RECEIVED_TOPIC = "procurement.goods.received";
    private static final String SUPPLIER_PERFORMANCE_UPDATED_TOPIC = "procurement.supplier.performance.updated";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ProcurementEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Publish event when new purchase order is created and sent to supplier
     */
    public void publishPurchaseOrderCreated(String poId, String supplierId, Integer itemCount) {
        logger.info("Publishing purchase order created event - poId: {}, supplierId: {}", poId, supplierId);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "PURCHASE_ORDER_CREATED");
        event.put("poId", poId);
        event.put("supplierId", supplierId);
        event.put("itemCount", itemCount);
        event.put("createdAt", LocalDateTime.now().toString());
        event.put("status", "SENT");

        kafkaTemplate.send(PURCHASE_ORDER_CREATED_TOPIC, poId, event);
    }

    /**
     * Publish event when materials are ordered with expected delivery date
     */
    public void publishMaterialsOrdered(String poId, List<CreatePurchaseOrderRequest.MaterialItem> materials,
                                       LocalDateTime expectedDeliveryDate) {
        logger.info("Publishing materials ordered event - poId: {}, materialCount: {}", poId, materials.size());

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "MATERIALS_ORDERED");
        event.put("poId", poId);
        event.put("materials", materials);
        event.put("expectedDeliveryDate", expectedDeliveryDate != null ? expectedDeliveryDate.toString() : null);
        event.put("orderedAt", LocalDateTime.now().toString());

        kafkaTemplate.send(MATERIALS_ORDERED_TOPIC, poId, event);
    }

    /**
     * Publish event when materials are received from supplier and ready for inventory
     */
    public void publishGoodsReceived(String poId, String supplierId,
                                    List<GoodsReceiptRequest.MaterialReceipt> materials) {
        logger.info("Publishing goods received event - poId: {}, supplierId: {}", poId, supplierId);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "GOODS_RECEIVED");
        event.put("poId", poId);
        event.put("supplierId", supplierId);
        event.put("materials", materials);
        event.put("receivedAt", LocalDateTime.now().toString());

        kafkaTemplate.send(GOODS_RECEIVED_TOPIC, poId, event);
    }

    /**
     * Publish event when supplier performance metrics have been updated
     */
    public void publishSupplierPerformanceUpdated(String supplierId, String poId, Double overallScore) {
        logger.info("Publishing supplier performance updated event - supplierId: {}, poId: {}, score: {}",
                supplierId, poId, overallScore);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "SUPPLIER_PERFORMANCE_UPDATED");
        event.put("supplierId", supplierId);
        event.put("purchaseOrderId", poId);
        event.put("overallScore", overallScore);
        event.put("updatedAt", LocalDateTime.now().toString());

        kafkaTemplate.send(SUPPLIER_PERFORMANCE_UPDATED_TOPIC, supplierId, event);
    }
}
