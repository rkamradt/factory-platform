package com.factory.assembly.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackConsumptionResponse {

    private String orderId;
    private Integer unitsProduced;
    private Double yieldPercentage;
    private String status;
    private LocalDateTime trackedAt;
}
