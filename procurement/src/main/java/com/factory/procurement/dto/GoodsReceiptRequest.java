package com.factory.procurement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsReceiptRequest {

    private LocalDateTime receivedAt;

    @NotEmpty(message = "At least one material receipt is required")
    private List<MaterialReceipt> materials;

    private String notes;
    private String receivedBy;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MaterialReceipt {
        @NotBlank(message = "Material ID is required")
        private String materialId;

        @NotNull(message = "Received quantity is required")
        private Double receivedQuantity;

        private String condition; // GOOD, DAMAGED, PARTIAL
        private String batchNumber;
        private String qualityNotes;
    }
}
