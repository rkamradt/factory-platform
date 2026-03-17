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
public class UpdateProgressResponse {

    private String orderId;
    private String buildStage;
    private Integer completedUnits;
    private Double progressPercentage;
    private String status;
    private LocalDateTime updatedAt;
}
