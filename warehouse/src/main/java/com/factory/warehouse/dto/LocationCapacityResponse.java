package com.factory.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationCapacityResponse {

    private String locationId;
    private String locationName;
    private String locationType; // PALLET_RACK, FLOOR, BULK, COLD_STORAGE, etc.
    private Double totalCapacity;
    private Double usedCapacity;
    private Double availableCapacity;
    private String unit; // CUBIC_METERS, PALLETS, etc.
    private Double utilizationPercent;
    private List<StoredMaterial> storedMaterials;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StoredMaterial {
        private String materialId;
        private String materialName;
        private Double quantity;
        private String unit;
        private Double spaceOccupied;
    }
}
