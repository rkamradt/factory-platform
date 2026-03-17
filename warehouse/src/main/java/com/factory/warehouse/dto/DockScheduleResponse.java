package com.factory.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DockScheduleResponse {

    private String appointmentId;
    private String appointmentType; // RECEIVING, SHIPPING
    private LocalDateTime scheduledTime;
    private LocalDateTime actualArrivalTime;
    private LocalDateTime completedTime;
    private String status; // SCHEDULED, CHECKED_IN, IN_PROGRESS, COMPLETED, CANCELLED
    private String companyName;
    private String referenceId;
    private String dockDoor;
    private String carrierName;
}
