package com.factory.inventory.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordMovementRequest {

    @NotNull(message = "Material ID is required")
    private String materialId;

    @NotNull(message = "Movement type is required")
    private MovementType movementType;

    @NotNull(message = "Quantity is required")
    private Double quantity;

    private String unit;
    private String fromLocationId;
    private String toLocationId;
    private String referenceId;
    private String performedBy;
    private String notes;

    public enum MovementType {
        RECEIPT,
        CONSUMPTION,
        TRANSFER,
        ADJUSTMENT
    }
}
