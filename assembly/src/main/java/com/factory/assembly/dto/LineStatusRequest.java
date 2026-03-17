package com.factory.assembly.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LineStatusRequest {

    @NotBlank(message = "Status is required")
    private String status;

    private String downtimeReason;

    private String bottleneckDescription;

    private Integer estimatedDowntimeMinutes;

    private String reportedBy;
}
