package com.factory.bom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialRequirementsResponse {

    private String bomId;
    private String productId;
    private Double productionQuantity;
    private List<MaterialRequirement> requirements;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MaterialRequirement {
        private String componentId;
        private String componentName;
        private Double requiredQuantity;
        private String unit;
        private Integer level;
        private List<String> usageLocations;
    }
}
