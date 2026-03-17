package com.factory.compliance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateTransferResponse {

    private String materialId;
    private Boolean isValid;
    private String validationStatus; // "VALID", "INVALID", "WARNING"
    private List<String> violations;
    private List<String> warnings;
    private String message;
}
