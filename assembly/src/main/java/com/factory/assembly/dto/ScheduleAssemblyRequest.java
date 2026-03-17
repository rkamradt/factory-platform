package com.factory.assembly.dto;

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
public class ScheduleAssemblyRequest {

    @NotBlank(message = "Line ID or workstation ID is required")
    private String lineId;

    private String workstationId;

    private LocalDateTime scheduledStartTime;

    private LocalDateTime estimatedCompletionTime;

    private String assemblySequence;
}
