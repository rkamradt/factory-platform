package com.factory.quality.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class QualityEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(QualityEventProducer.class);

    private static final String INSPECTION_COMPLETED_TOPIC = "quality.inspection.completed";
    private static final String FAILURE_DETECTED_TOPIC = "quality.failure.detected";
    private static final String CERTIFICATE_ISSUED_TOPIC = "quality.certificate.issued";
    private static final String TREND_ALERT_TOPIC = "quality.trend.alert";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public QualityEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Publish event when inspection completed with pass/fail results and measurements
     */
    public void publishInspectionCompleted(String inspectionId, String materialId, String batchId,
                                          String result, Map<String, Object> measurements, List<String> defects) {
        logger.info("Publishing inspection completed event - inspectionId: {}, result: {}", inspectionId, result);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "INSPECTION_COMPLETED");
        event.put("inspectionId", inspectionId);
        event.put("materialId", materialId);
        event.put("batchId", batchId);
        event.put("result", result);
        event.put("measurements", measurements);
        event.put("defects", defects);
        event.put("completedAt", LocalDateTime.now().toString());

        kafkaTemplate.send(INSPECTION_COMPLETED_TOPIC, inspectionId, event);
    }

    /**
     * Publish event when quality failure detected requiring corrective action
     */
    public void publishQualityFailureDetected(String failureId, String inspectionId, String materialId,
                                             String batchId, List<String> defects, String correctionRequired) {
        logger.warn("Publishing quality failure detected event - failureId: {}, materialId: {}",
                failureId, materialId);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "QUALITY_FAILURE_DETECTED");
        event.put("failureId", failureId);
        event.put("inspectionId", inspectionId);
        event.put("materialId", materialId);
        event.put("batchId", batchId);
        event.put("defects", defects);
        event.put("correctionRequired", correctionRequired);
        event.put("severity", determineSeverity(correctionRequired));
        event.put("detectedAt", LocalDateTime.now().toString());

        kafkaTemplate.send(FAILURE_DETECTED_TOPIC, failureId, event);
    }

    /**
     * Publish event when quality certificate issued enabling material use or shipping
     */
    public void publishQualityCertificateIssued(String certificateId, String batchId, String materialId,
                                               String certificateNumber, Map<String, Object> specifications) {
        logger.info("Publishing quality certificate issued event - certificateId: {}, certificateNumber: {}",
                certificateId, certificateNumber);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "QUALITY_CERTIFICATE_ISSUED");
        event.put("certificateId", certificateId);
        event.put("batchId", batchId);
        event.put("materialId", materialId);
        event.put("certificateNumber", certificateNumber);
        event.put("certifiedSpecifications", specifications);
        event.put("issuedAt", LocalDateTime.now().toString());
        event.put("status", "APPROVED_FOR_USE");

        kafkaTemplate.send(CERTIFICATE_ISSUED_TOPIC, certificateId, event);
    }

    /**
     * Publish event when statistical process control violation detected
     */
    public void publishTrendAlert(String materialId, String trendType, Map<String, Object> analysis,
                                  String severity, String recommendation) {
        logger.warn("Publishing quality trend alert - materialId: {}, trendType: {}, severity: {}",
                materialId, trendType, severity);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "QUALITY_TREND_ALERT");
        event.put("materialId", materialId);
        event.put("trendType", trendType); // DECLINING_QUALITY, INCREASING_DEFECTS, PROCESS_DRIFT
        event.put("analysis", analysis);
        event.put("severity", severity); // HIGH, MEDIUM, LOW
        event.put("recommendation", recommendation);
        event.put("detectedAt", LocalDateTime.now().toString());

        String alertId = "ALERT-" + System.currentTimeMillis();
        kafkaTemplate.send(TREND_ALERT_TOPIC, alertId, event);
    }

    private String determineSeverity(String correctionRequired) {
        return switch (correctionRequired) {
            case "SCRAP", "RETURN_TO_SUPPLIER" -> "CRITICAL";
            case "REWORK" -> "HIGH";
            default -> "MEDIUM";
        };
    }
}
