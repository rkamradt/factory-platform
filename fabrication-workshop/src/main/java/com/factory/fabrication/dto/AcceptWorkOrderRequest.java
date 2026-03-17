package com.factory.fabrication.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcceptWorkOrderRequest {

    @NotBlank(message = "Work order ID is required")
    private String workOrderId;

    private String assignedTeam;

    private LocalDateTime acceptedAt;

    private String notes;
}
