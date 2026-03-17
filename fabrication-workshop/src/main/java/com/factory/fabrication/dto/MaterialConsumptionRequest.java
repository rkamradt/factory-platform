package com.factory.fabrication.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialConsumptionRequest {

    @NotEmpty(message = "At least one material consumption record is required")
    private List<MaterialItem> materials;

    private LocalDateTime consumedAt;

    private String stage; // CUTTING, FORMING, WELDING, FINISHING

    private String notes;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MaterialItem {
        @NotNull(message = "Material ID is required")
        private String materialId;

        private String materialName;

        @NotNull(message = "Consumed quantity is required")
        private Double consumedQuantity;

        private Double wasteQuantity;

        private Double reworkQuantity;

        @NotNull(message = "Unit is required")
        private String unit;

        private String wasteReason;
    }
}
