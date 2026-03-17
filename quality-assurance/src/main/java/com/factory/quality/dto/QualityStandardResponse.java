package com.factory.quality.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QualityStandardResponse {

    private String standardId;
    private String standardName;
    private String materialType;
    private String version;
    private Map<String, Object> specifications;
    private List<QualityStandardRequest.InspectionProcedure> procedures;
    private String status; // ACTIVE, DRAFT, RETIRED
    private LocalDateTime createdAt;
    private LocalDateTime lastModified;
}
