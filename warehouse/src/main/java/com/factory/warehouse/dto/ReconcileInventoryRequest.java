package com.factory.warehouse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReconcileInventoryRequest {

    @NotBlank(message = "Location ID is required")
    private String locationId;

    @NotNull(message = "Counts are required")
    private List<CountItem> counts;

    private String countedBy;
    private String notes;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CountItem {
        @NotBlank(message = "Material ID is required")
        private String materialId;

        @NotNull(message = "Physical count is required")
        private Double physicalCount;

        @NotBlank(message = "Unit is required")
        private String unit;

        private String notes;
    }
}
