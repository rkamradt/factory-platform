package com.factory.warehouse.service;

import com.factory.warehouse.dto.LocationCapacityResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {

    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);

    public LocationCapacityResponse getLocationCapacity(String locationId) {
        logger.info("Querying capacity for location: {}", locationId);

        // TODO: Implement actual location capacity logic
        // TODO: Fetch from database
        // TODO: Calculate current utilization
        // TODO: Get list of stored materials

        // Simulate location data
        List<LocationCapacityResponse.StoredMaterial> storedMaterials = new ArrayList<>();
        storedMaterials.add(LocationCapacityResponse.StoredMaterial.builder()
                .materialId("MAT-001")
                .materialName("Steel Plates")
                .quantity(150.0)
                .unit("PIECES")
                .spaceOccupied(12.5)
                .build());
        storedMaterials.add(LocationCapacityResponse.StoredMaterial.builder()
                .materialId("MAT-002")
                .materialName("Aluminum Rods")
                .quantity(200.0)
                .unit("PIECES")
                .spaceOccupied(8.3)
                .build());

        Double totalCapacity = 100.0;
        Double usedCapacity = 20.8;
        Double availableCapacity = totalCapacity - usedCapacity;
        Double utilizationPercent = (usedCapacity / totalCapacity) * 100;

        return LocationCapacityResponse.builder()
                .locationId(locationId)
                .locationName("Warehouse A - Zone 1")
                .locationType("PALLET_RACK")
                .totalCapacity(totalCapacity)
                .usedCapacity(usedCapacity)
                .availableCapacity(availableCapacity)
                .unit("CUBIC_METERS")
                .utilizationPercent(utilizationPercent)
                .storedMaterials(storedMaterials)
                .build();
    }
}
