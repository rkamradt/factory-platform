package com.factory.bom.dto;

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
public class BomResponse {

    private String bomId;
    private String productId;
    private String productName;
    private String version;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime publishedAt;
    private List<ComponentHierarchy> components;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ComponentHierarchy {
        private String componentId;
        private String componentName;
        private Double quantity;
        private String unit;
        private String specifications;
        private Integer level;
        private List<ComponentHierarchy> subComponents;
    }
}
