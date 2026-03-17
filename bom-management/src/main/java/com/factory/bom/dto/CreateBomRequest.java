package com.factory.bom.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class CreateBomRequest {

    @NotBlank(message = "Product ID is required")
    private String productId;

    @NotBlank(message = "Product name is required")
    private String productName;

    @NotBlank(message = "Version is required")
    private String version;

    private String description;

    @NotEmpty(message = "At least one component is required")
    private List<ComponentItem> components;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ComponentItem {
        @NotBlank(message = "Component ID is required")
        private String componentId;

        @NotBlank(message = "Component name is required")
        private String componentName;

        @NotNull(message = "Quantity is required")
        private Double quantity;

        @NotBlank(message = "Unit is required")
        private String unit;

        private String specifications;

        private List<ComponentItem> subComponents;
    }
}
