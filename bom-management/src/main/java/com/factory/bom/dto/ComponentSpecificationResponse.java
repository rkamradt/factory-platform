package com.factory.bom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComponentSpecificationResponse {

    private String componentId;
    private String componentName;
    private String componentType;
    private String description;
    private Map<String, Object> specifications;
    private Map<String, String> requirements;
    private String unit;
    private Double standardQuantity;
}
