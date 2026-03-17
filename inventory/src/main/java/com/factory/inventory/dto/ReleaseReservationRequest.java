package com.factory.inventory.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseReservationRequest {

    @NotNull(message = "Reservation ID is required")
    private String reservationId;

    @NotNull(message = "Reason is required")
    private String reason;

    private String releasedBy;
}
