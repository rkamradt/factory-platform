package com.factory.compliance.event;

import com.factory.compliance.dto.AuditTrailResponse;
import com.factory.compliance.dto.AuthorizeTransferResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class ComplianceEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(ComplianceEventProducer.class);

    private static final String TRANSFER_AUTHORIZED_TOPIC = "compliance.transfer.authorized";
    private static final String MOVEMENT_LOGGED_TOPIC = "compliance.movement.logged";
    private static final String VIOLATION_DETECTED_TOPIC = "compliance.violation.detected";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ComplianceEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Publish event when material transfer has been authorized and can proceed
     */
    public void publishTransferAuthorized(AuthorizeTransferResponse authorization) {
        logger.info("Publishing transfer authorized event - authorizationId: {}, materialId: {}",
                authorization.getAuthorizationId(), authorization.getMaterialId());

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "TRANSFER_AUTHORIZED");
        event.put("authorizationId", authorization.getAuthorizationId());
        event.put("materialId", authorization.getMaterialId());
        event.put("quantity", authorization.getQuantity());
        event.put("sourceLocation", authorization.getSourceLocation());
        event.put("destinationLocation", authorization.getDestinationLocation());
        event.put("transferType", authorization.getTransferType());
        event.put("status", authorization.getStatus());
        event.put("authorizedAt", authorization.getAuthorizedAt().toString());
        event.put("expiresAt", authorization.getExpiresAt().toString());

        kafkaTemplate.send(TRANSFER_AUTHORIZED_TOPIC, authorization.getAuthorizationId(), event);
    }

    /**
     * Publish event when material movement has been recorded in audit trail
     */
    public void publishMovementLogged(AuditTrailResponse.AuditEntry auditEntry) {
        logger.info("Publishing movement logged event - logId: {}, materialId: {}",
                auditEntry.getLogId(), auditEntry.getMaterialId());

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "MOVEMENT_LOGGED");
        event.put("logId", auditEntry.getLogId());
        event.put("materialId", auditEntry.getMaterialId());
        event.put("quantity", auditEntry.getQuantity());
        event.put("sourceLocation", auditEntry.getSourceLocation());
        event.put("destinationLocation", auditEntry.getDestinationLocation());
        event.put("movementType", auditEntry.getMovementType());
        event.put("executedBy", auditEntry.getExecutedBy());
        event.put("executedAt", auditEntry.getExecutedAt() != null ? auditEntry.getExecutedAt().toString() : null);
        event.put("loggedAt", auditEntry.getLoggedAt().toString());
        event.put("authorizationId", auditEntry.getAuthorizationId());
        event.put("orderId", auditEntry.getOrderId());
        event.put("additionalContext", auditEntry.getAdditionalContext());

        kafkaTemplate.send(MOVEMENT_LOGGED_TOPIC, auditEntry.getLogId(), event);
    }

    /**
     * Publish event when compliance rule violation detected (future use)
     */
    public void publishViolationDetected(String violationType, String materialId, String location, Map<String, Object> details) {
        logger.warn("Publishing violation detected event - type: {}, materialId: {}, location: {}",
                violationType, materialId, location);

        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "VIOLATION_DETECTED");
        event.put("violationType", violationType);
        event.put("materialId", materialId);
        event.put("location", location);
        event.put("detectedAt", LocalDateTime.now().toString());
        event.put("severity", "HIGH");
        event.put("details", details);
        event.put("requiresAction", true);

        kafkaTemplate.send(VIOLATION_DETECTED_TOPIC, materialId, event);
    }
}
