package com.factory.procurement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReorderRequest {

    private List<String> materialIds; // Optional - if empty, check all materials
    private Boolean autoApprove; // If true, automatically send PO to suppliers
}
