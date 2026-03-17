package com.factory.assembly.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignOperatorRequest {

    @NotBlank(message = "Workstation ID is required")
    private String workstationId;

    @NotBlank(message = "Task type is required")
    private String taskType;

    private String orderId;

    private List<String> requiredCertifications;
}
