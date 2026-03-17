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
public class AcceptWorkOrderResponse {

    private String workOrderId;
    private String orderId;
    private String status; // ACCEPTED, PENDING_SCHEDULE
    private LocalDateTime acceptedAt;
    private String componentId;
    private String componentName;
    private Integer quantity;
    private String priority;
}
