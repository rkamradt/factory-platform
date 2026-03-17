package com.factory.warehouse.service;

import com.factory.warehouse.dto.PickOrderRequest;
import com.factory.warehouse.dto.PickOrderResponse;
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
public class PickingService {

    private static final Logger logger = LoggerFactory.getLogger(PickingService.class);
    private final WarehouseEventProducer eventProducer;

    public PickingService(WarehouseEventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    public PickOrderResponse pickMaterials(String orderId, PickOrderRequest request) {
        logger.info("Picking materials for production order: {}", orderId);

        // TODO: Implement actual picking logic
        // TODO: Optimize picking route
        // TODO: Assign to picker
        // TODO: Update inventory locations
        // TODO: Persist to database

        String pickListId = UUID.randomUUID().toString();

        List<PickOrderResponse.PickedItemDetail> items = request.getItems().stream()
                .map(item -> PickOrderResponse.PickedItemDetail.builder()
                        .materialId(item.getMaterialId())
                        .requestedQuantity(item.getQuantity())
                        .pickedQuantity(item.getQuantity()) // Assume full pick for now
                        .unit(item.getUnit())
                        .sourceLocation(item.getSourceLocation())
                        .build())
                .collect(Collectors.toList());

        PickOrderResponse response = PickOrderResponse.builder()
                .pickListId(pickListId)
                .productionOrderId(request.getProductionOrderId())
                .status("PICKED")
                .createdAt(LocalDateTime.now())
                .pickedAt(LocalDateTime.now())
                .assignedOperatorId(request.getAssignedOperatorId())
                .destinationLocation(request.getDestinationLocation())
                .items(items)
                .build();

        // Publish materials picked event
        Map<String, Object> pickDetails = new HashMap<>();
        pickDetails.put("destinationLocation", request.getDestinationLocation());
        pickDetails.put("assignedOperatorId", request.getAssignedOperatorId());
        pickDetails.put("itemCount", items.size());
        pickDetails.put("items", items);

        eventProducer.publishMaterialsPicked(pickListId, request.getProductionOrderId(), pickDetails);

        return response;
    }
}
