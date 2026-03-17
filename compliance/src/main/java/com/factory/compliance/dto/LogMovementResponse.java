package com.factory.compliance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogMovementResponse {

    private String logId;
    private String materialId;
    private Double quantity;
    private String sourceLocation;
    private String destinationLocation;
    private String movementType;
    private String status; // "LOGGED", "FAILED"
    private LocalDateTime loggedAt;
    private String message;
}
