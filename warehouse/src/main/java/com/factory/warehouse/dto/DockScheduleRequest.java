package com.factory.warehouse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DockScheduleRequest {

    @NotBlank(message = "Appointment type is required")
    private String appointmentType; // RECEIVING, SHIPPING

    @NotNull(message = "Scheduled time is required")
    private LocalDateTime scheduledTime;

    @NotBlank(message = "Company name is required")
    private String companyName; // Supplier or Customer

    private String referenceId; // PO ID or Shipment ID
    private String dockDoor;
    private String carrierName;
    private String truckNumber;
    private String driverName;
    private String driverPhone;
    private String notes;
}
