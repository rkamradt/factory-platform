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
public class ScheduleWorkOrderRequest {

    private String equipmentId; // specific equipment or null for general pool

    @NotBlank(message = "Equipment type is required")
    private String equipmentType; // CUTTING, FORMING, WELDING, FINISHING

    private LocalDateTime scheduledStartTime;

    private LocalDateTime scheduledEndTime;

    private Integer priority; // 1-10, higher is more urgent

    private String notes;
}
