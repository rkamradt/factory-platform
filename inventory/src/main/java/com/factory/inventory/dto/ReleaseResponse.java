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
public class ReleaseResponse {

    private String reservationId;
    private String productionOrderId;
    private LocalDateTime releasedAt;
    private String reason;
    private String status;
    private String message;
}
