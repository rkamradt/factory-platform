package com.factory.bom.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExplodeBomRequest {

    @NotNull(message = "Production quantity is required")
    @Positive(message = "Production quantity must be positive")
    private Double productionQuantity;

    private Boolean includeScrapFactor;
    private Double scrapPercentage;
}
