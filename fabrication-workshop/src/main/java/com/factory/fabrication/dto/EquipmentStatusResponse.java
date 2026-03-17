package com.factory.fabrication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentStatusResponse {

    private String equipmentId;
    private String equipmentType;
    private String status;
    private LocalDateTime statusChangedAt;
    private String reason;
    private LocalDateTime expectedAvailableAt;
    private String currentWorkOrderId;
    private Double utilizationPercentage;
    private String notes;
}
