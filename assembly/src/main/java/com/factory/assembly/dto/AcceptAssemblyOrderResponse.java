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
public class AcceptAssemblyOrderResponse {

    private String orderId;
    private String assemblyOrderId;
    private String status;
    private String message;
    private LocalDateTime acceptedAt;
}
