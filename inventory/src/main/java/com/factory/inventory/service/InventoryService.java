package com.factory.inventory.service;

import com.factory.inventory.dto.*;
import com.factory.inventory.event.InventoryEventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);
    private final InventoryEventProducer eventProducer;

    public InventoryService(InventoryEventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    public AvailabilityResponse checkAvailability(CheckAvailabilityRequest request) {
        logger.info("Checking availability for {} materials", request.getMaterials().size());

        // TODO: Implement actual availability checking logic
        List<AvailabilityResponse.MaterialAvailability> availabilities = new ArrayList<>();
        boolean allAvailable = true;

        for (CheckAvailabilityRequest.MaterialQuantity material : request.getMaterials()) {
            // Stub implementation - always returns available
            availabilities.add(AvailabilityResponse.MaterialAvailability.builder()
                    .materialId(material.getMaterialId())
                    .requestedQuantity(material.getQuantity())
                    .availableQuantity(material.getQuantity() * 2) // Stub: double available
                    .available(true)
                    .unit(material.getUnit())
                    .build());
        }

        return AvailabilityResponse.builder()
                .allAvailable(allAvailable)
                .materialAvailabilities(availabilities)
                .message("Availability check completed")
                .build();
    }

    public ReservationResponse reserveMaterials(ReserveMaterialsRequest request) {
        logger.info("Reserving materials for production order: {}", request.getProductionOrderId());

        String reservationId = UUID.randomUUID().toString();

        // TODO: Implement actual reservation logic
        List<ReservationResponse.ReservedMaterial> reservedMaterials = new ArrayList<>();
        for (ReserveMaterialsRequest.MaterialQuantity material : request.getMaterials()) {
            reservedMaterials.add(ReservationResponse.ReservedMaterial.builder()
                    .materialId(material.getMaterialId())
                    .quantity(material.getQuantity())
                    .unit(material.getUnit())
                    .locationId(material.getPreferredLocationId() != null ?
                            material.getPreferredLocationId() : "DEFAULT_LOCATION")
                    .reservationItemId(UUID.randomUUID().toString())
                    .build());
        }

        // Publish event
        eventProducer.publishMaterialReserved(reservationId, request.getProductionOrderId(), reservedMaterials);

        return ReservationResponse.builder()
                .reservationId(reservationId)
                .productionOrderId(request.getProductionOrderId())
                .reservedMaterials(reservedMaterials)
                .reservedAt(LocalDateTime.now())
                .status("RESERVED")
                .message("Materials reserved successfully")
                .build();
    }

    public AllocationResponse allocateMaterials(AllocateMaterialsRequest request) {
        logger.info("Allocating materials for reservation: {}", request.getReservationId());

        String allocationId = UUID.randomUUID().toString();

        // TODO: Implement actual allocation logic
        List<AllocationResponse.AllocatedMaterial> allocatedMaterials = new ArrayList<>();
        // Stub implementation

        return AllocationResponse.builder()
                .allocationId(allocationId)
                .reservationId(request.getReservationId())
                .productionOrderId("PROD_ORDER_" + UUID.randomUUID().toString().substring(0, 8))
                .allocatedMaterials(allocatedMaterials)
                .allocatedAt(LocalDateTime.now())
                .status("ALLOCATED")
                .message("Materials allocated successfully")
                .build();
    }

    public MovementResponse recordMovement(RecordMovementRequest request) {
        logger.info("Recording {} movement for material: {}",
                request.getMovementType(), request.getMaterialId());

        String movementId = UUID.randomUUID().toString();

        // TODO: Implement actual movement recording logic

        // Publish events
        eventProducer.publishStockMovement(movementId, request.getMaterialId(),
                request.getMovementType().toString(), request.getQuantity(),
                request.getFromLocationId(), request.getToLocationId());

        eventProducer.publishStockChanged(request.getMaterialId(),
                request.getToLocationId() != null ? request.getToLocationId() : request.getFromLocationId(),
                request.getQuantity());

        return MovementResponse.builder()
                .movementId(movementId)
                .materialId(request.getMaterialId())
                .movementType(request.getMovementType())
                .quantity(request.getQuantity())
                .unit(request.getUnit())
                .fromLocationId(request.getFromLocationId())
                .toLocationId(request.getToLocationId())
                .recordedAt(LocalDateTime.now())
                .status("RECORDED")
                .message("Movement recorded successfully")
                .build();
    }

    public StockLevelResponse getStockLevel(String materialId) {
        logger.info("Getting stock level for material: {}", materialId);

        // TODO: Implement actual stock level retrieval logic
        return StockLevelResponse.builder()
                .materialId(materialId)
                .materialName("Material " + materialId)
                .totalAvailableQuantity(100.0)
                .totalReservedQuantity(20.0)
                .totalQuantity(120.0)
                .unit("units")
                .locations(new ArrayList<>())
                .status("OK")
                .build();
    }

    public ReleaseResponse releaseReservation(ReleaseReservationRequest request) {
        logger.info("Releasing reservation: {} - Reason: {}",
                request.getReservationId(), request.getReason());

        // TODO: Implement actual reservation release logic

        return ReleaseResponse.builder()
                .reservationId(request.getReservationId())
                .productionOrderId("PROD_ORDER_" + UUID.randomUUID().toString().substring(0, 8))
                .releasedAt(LocalDateTime.now())
                .reason(request.getReason())
                .status("RELEASED")
                .message("Reservation released successfully")
                .build();
    }

    public LocationStockResponse getLocationStock(String locationId) {
        logger.info("Getting stock for location: {}", locationId);

        // TODO: Implement actual location stock retrieval logic
        return LocationStockResponse.builder()
                .locationId(locationId)
                .locationName("Location " + locationId)
                .materials(new ArrayList<>())
                .status("OK")
                .build();
    }
}
