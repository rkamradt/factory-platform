package com.factory.warehouse.service;

import com.factory.warehouse.dto.ReceiveGoodsRequest;
import com.factory.warehouse.dto.ReceiveGoodsResponse;
import com.factory.warehouse.event.WarehouseEventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReceivingService {

    private static final Logger logger = LoggerFactory.getLogger(ReceivingService.class);
    private final WarehouseEventProducer eventProducer;

    public ReceivingService(WarehouseEventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    public ReceiveGoodsResponse receiveGoods(ReceiveGoodsRequest request) {
        logger.info("Receiving goods for PO: {} from supplier: {}",
                request.getPurchaseOrderId(), request.getSupplierId());

        // TODO: Implement actual receiving logic
        // TODO: Perform quality verification
        // TODO: Update storage locations
        // TODO: Persist to database

        String receiptId = UUID.randomUUID().toString();

        List<ReceiveGoodsResponse.ReceivedItemDetail> items = request.getItems().stream()
                .map(item -> ReceiveGoodsResponse.ReceivedItemDetail.builder()
                        .materialId(item.getMaterialId())
                        .materialName(item.getMaterialName())
                        .quantity(item.getQuantity())
                        .unit(item.getUnit())
                        .storageLocation(item.getStorageLocation())
                        .qualityStatus(item.getQualityStatus() != null ? item.getQualityStatus() : "PASS")
                        .build())
                .collect(Collectors.toList());

        ReceiveGoodsResponse response = ReceiveGoodsResponse.builder()
                .receiptId(receiptId)
                .purchaseOrderId(request.getPurchaseOrderId())
                .supplierId(request.getSupplierId())
                .status("RECEIVED")
                .receivedAt(LocalDateTime.now())
                .dockLocation(request.getDockLocation())
                .items(items)
                .build();

        // Publish goods received event
        Map<String, Object> receiptDetails = new HashMap<>();
        receiptDetails.put("supplierId", request.getSupplierId());
        receiptDetails.put("dockLocation", request.getDockLocation());
        receiptDetails.put("itemCount", items.size());
        receiptDetails.put("items", items);

        eventProducer.publishGoodsReceived(receiptId, request.getPurchaseOrderId(), receiptDetails);

        return response;
    }
}
