package com.factory.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovementResponse {

    private String movementId;
    private String materialId;
    private RecordMovementRequest.MovementType movementType;
    private Double quantity;
    private String unit;
    private String fromLocationId;
    private String toLocationId;
    private LocalDateTime recordedAt;
    private String status;
    private String message;
}
