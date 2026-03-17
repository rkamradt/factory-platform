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
public class ScheduleAssemblyResponse {

    private String orderId;
    private String lineId;
    private String workstationId;
    private String status;
    private LocalDateTime scheduledStartTime;
    private LocalDateTime estimatedCompletionTime;
}
