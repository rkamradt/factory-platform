package com.factory.compliance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizeTransferResponse {

    private String authorizationId;
    private String materialId;
    private Double quantity;
    private String sourceLocation;
    private String destinationLocation;
    private String transferType;
    private String status; // "AUTHORIZED", "DENIED"
    private String reason;
    private LocalDateTime authorizedAt;
    private LocalDateTime expiresAt;
}
