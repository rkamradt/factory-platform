package com.factory.assembly.dto;

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
public class TrackConsumptionRequest {

    @NotNull(message = "Component consumption list is required")
    private List<ComponentConsumption> components;

    private Integer unitsProduced;

    private Double yieldPercentage;

    private String notes;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ComponentConsumption {
        @NotBlank(message = "Component ID is required")
        private String componentId;

        @NotNull(message = "Quantity consumed is required")
        private Integer quantityConsumed;

        private String batchNumber;
    }
}
