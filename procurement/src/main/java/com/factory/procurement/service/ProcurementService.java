package com.factory.procurement.service;

import com.factory.procurement.dto.*;
import com.factory.procurement.event.ProcurementEventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProcurementService {

    private static final Logger logger = LoggerFactory.getLogger(ProcurementService.class);
    private final ProcurementEventProducer eventProducer;

    public ProcurementService(ProcurementEventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    public PurchaseOrderResponse createPurchaseOrder(CreatePurchaseOrderRequest request) {
        logger.info("Creating purchase order for supplier: {}", request.getSupplierId());

        // TODO: Implement actual purchase order creation logic
        // TODO: Persist to database
        // TODO: Validate supplier exists
        // TODO: Validate materials

        String poId = "PO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        LocalDateTime now = LocalDateTime.now();

        List<PurchaseOrderResponse.MaterialItem> materials = request.getMaterials().stream()
                .map(m -> PurchaseOrderResponse.MaterialItem.builder()
                        .materialId(m.getMaterialId())
                        .materialName(m.getMaterialName())
                        .orderedQuantity(m.getQuantity())
                        .receivedQuantity(0.0)
                        .unit(m.getUnit())
                        .unitPrice(m.getUnitPrice())
                        .specifications(m.getSpecifications())
                        .status("PENDING")
                        .build())
                .collect(Collectors.toList());

        Double totalAmount = materials.stream()
                .filter(m -> m.getUnitPrice() != null)
                .mapToDouble(m -> m.getOrderedQuantity() * m.getUnitPrice())
                .sum();

        PurchaseOrderResponse response = PurchaseOrderResponse.builder()
                .poId(poId)
                .supplierId(request.getSupplierId())
                .supplierName(request.getSupplierName())
                .status("SENT")
                .createdAt(now)
                .sentAt(now)
                .requestedDeliveryDate(request.getRequestedDeliveryDate())
                .expectedDeliveryDate(request.getRequestedDeliveryDate() != null ?
                        request.getRequestedDeliveryDate() : now.plusDays(7))
                .materials(materials)
                .totalAmount(totalAmount)
                .notes(request.getNotes())
                .build();

        // Publish events
        eventProducer.publishPurchaseOrderCreated(poId, request.getSupplierId(), materials.size());
        eventProducer.publishMaterialsOrdered(poId, request.getMaterials(), response.getExpectedDeliveryDate());

        return response;
    }

    public PurchaseOrderResponse getPurchaseOrder(String poId) {
        logger.info("Retrieving purchase order: {}", poId);

        // TODO: Implement retrieval logic from database

        return PurchaseOrderResponse.builder()
                .poId(poId)
                .supplierId("SUPP-001")
                .supplierName("Sample Supplier")
                .status("SENT")
                .createdAt(LocalDateTime.now().minusDays(2))
                .sentAt(LocalDateTime.now().minusDays(2))
                .expectedDeliveryDate(LocalDateTime.now().plusDays(5))
                .materials(new ArrayList<>())
                .totalAmount(0.0)
                .build();
    }

    public PurchaseOrderResponse receiveGoods(String poId, GoodsReceiptRequest request) {
        logger.info("Recording goods receipt for PO: {}", poId);

        // TODO: Implement goods receipt logic
        // TODO: Update purchase order status
        // TODO: Update material received quantities
        // TODO: Trigger inventory update

        LocalDateTime receivedAt = request.getReceivedAt() != null ?
                request.getReceivedAt() : LocalDateTime.now();

        PurchaseOrderResponse response = getPurchaseOrder(poId);
        response.setStatus("RECEIVED");
        response.setActualDeliveryDate(receivedAt);

        // Update received quantities
        for (GoodsReceiptRequest.MaterialReceipt receipt : request.getMaterials()) {
            response.getMaterials().stream()
                    .filter(m -> m.getMaterialId().equals(receipt.getMaterialId()))
                    .forEach(m -> {
                        m.setReceivedQuantity(receipt.getReceivedQuantity());
                        m.setStatus("RECEIVED");
                    });
        }

        // Publish event
        eventProducer.publishGoodsReceived(poId, response.getSupplierId(), request.getMaterials());

        return response;
    }

    public DeliveryScheduleResponse getDeliverySchedule() {
        logger.info("Retrieving delivery schedule");

        // TODO: Implement logic to query all active purchase orders
        // TODO: Filter by status (SENT, CONFIRMED)
        // TODO: Sort by expected delivery date

        List<DeliveryScheduleResponse.ScheduledDelivery> deliveries = new ArrayList<>();

        // Sample data
        deliveries.add(DeliveryScheduleResponse.ScheduledDelivery.builder()
                .purchaseOrderId("PO-001")
                .supplierId("SUPP-001")
                .supplierName("Sample Supplier")
                .expectedDeliveryDate(LocalDateTime.now().plusDays(3))
                .status("CONFIRMED")
                .materials(new ArrayList<>())
                .build());

        return DeliveryScheduleResponse.builder()
                .deliveries(deliveries)
                .build();
    }

    public ReorderResponse triggerReorder(ReorderRequest request) {
        logger.info("Triggering reorder process");

        // TODO: Implement reorder logic
        // TODO: Query inventory for materials below reorder point
        // TODO: Determine preferred suppliers
        // TODO: Calculate reorder quantities
        // TODO: Create purchase orders

        List<ReorderResponse.ReorderItem> reorderItems = new ArrayList<>();
        List<String> createdPOs = new ArrayList<>();

        // Sample reorder logic
        ReorderResponse.ReorderItem item = ReorderResponse.ReorderItem.builder()
                .materialId("MAT-001")
                .materialName("Sample Material")
                .currentStock(50.0)
                .reorderPoint(100.0)
                .reorderQuantity(200.0)
                .preferredSupplierId("SUPP-001")
                .supplierName("Sample Supplier")
                .purchaseOrderId("PO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .status("CREATED")
                .build();

        reorderItems.add(item);
        createdPOs.add(item.getPurchaseOrderId());

        return ReorderResponse.builder()
                .totalMaterialsChecked(request.getMaterialIds() != null ?
                        request.getMaterialIds().size() : 0)
                .materialsNeedingReorder(reorderItems.size())
                .createdPurchaseOrderIds(createdPOs)
                .reorderItems(reorderItems)
                .build();
    }
}
