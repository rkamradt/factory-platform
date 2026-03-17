package com.factory.quality.service;

import com.factory.quality.dto.QualityStandardRequest;
import com.factory.quality.dto.QualityStandardResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class QualityStandardService {

    private static final Logger logger = LoggerFactory.getLogger(QualityStandardService.class);

    // In-memory storage for demo (replace with database in production)
    private final Map<String, QualityStandardResponse> standards = new HashMap<>();

    /**
     * Create or update quality standards and inspection procedures
     */
    public QualityStandardResponse createOrUpdateStandard(QualityStandardRequest request) {
        logger.info("Creating/updating quality standard: {}", request.getStandardName());

        String standardId = "STD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String version = request.getVersion() != null ? request.getVersion() : "1.0";

        QualityStandardResponse response = new QualityStandardResponse(
                standardId,
                request.getStandardName(),
                request.getMaterialType(),
                version,
                request.getSpecifications(),
                request.getProcedures(),
                "ACTIVE",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        standards.put(standardId, response);

        logger.info("Quality standard created with ID: {}", standardId);
        return response;
    }

    /**
     * Get standard by ID
     */
    public QualityStandardResponse getStandard(String standardId) {
        QualityStandardResponse standard = standards.get(standardId);
        if (standard == null) {
            throw new IllegalArgumentException("Quality standard not found: " + standardId);
        }
        return standard;
    }

    /**
     * Get all standards for a material type
     */
    public Map<String, QualityStandardResponse> getStandardsForMaterialType(String materialType) {
        Map<String, QualityStandardResponse> result = new HashMap<>();
        standards.forEach((id, standard) -> {
            if (materialType.equals(standard.getMaterialType())) {
                result.put(id, standard);
            }
        });
        return result;
    }
}
