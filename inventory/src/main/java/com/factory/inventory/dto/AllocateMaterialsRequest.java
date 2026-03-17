package com.factory.inventory.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllocateMaterialsRequest {

    @NotNull(message = "Reservation ID is required")
    private String reservationId;

    private String allocatedBy;
    private String notes;
}
