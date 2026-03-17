package com.factory.productionplanning.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CapacityUtilizationResponse {

    private Double overallUtilizationPercentage;
    private List<ResourceUtilization> resourceUtilizations;
    private List<String> bottlenecks;
    private String status;
    private String message;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ResourceUtilization {
        private String resourceId;
        private String resourceName;
        private String resourceType; // MACHINING, FABRICATION, ASSEMBLY
        private Double utilizationPercentage;
        private Integer activeWorkOrders;
        private Integer queuedWorkOrders;
        private Double availableCapacity;
        private String status;
    }
}
