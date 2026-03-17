package com.factory.productionplanning.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateWorkOrderRequest {

    @NotNull(message = "Production order ID is required")
    private String productionOrderId;

    @NotNull(message = "Workshop type is required")
    private String workshopType; // MACHINING, FABRICATION, ASSEMBLY

    @NotNull(message = "Operation type is required")
    private String operationType;

    private String componentId;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    private LocalDateTime dueDate;

    private List<String> requiredMaterials;

    private String priority;
}
