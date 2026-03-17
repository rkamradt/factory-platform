package com.factory.machining.dto;

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

    private String workOrderId;
    private String orderId;
    private String stage;
    private String status;
    private Integer completedQuantity;
    private Integer rejectedQuantity;
    private Integer remainingQuantity;
    private LocalDateTime updatedAt;
    private String qualityStatus;
}
