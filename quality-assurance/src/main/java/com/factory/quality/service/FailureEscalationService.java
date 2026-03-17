package com.factory.quality.service;

import com.factory.quality.dto.EscalateFailureRequest;
import com.factory.quality.dto.EscalateFailureResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class FailureEscalationService {

    private static final Logger logger = LoggerFactory.getLogger(FailureEscalationService.class);

    // In-memory storage for demo (replace with database in production)
    private final Map<String, EscalateFailureResponse> escalations = new HashMap<>();

    /**
     * Escalate quality failures and trigger corrective actions
     */
    public EscalateFailureResponse escalateFailure(String failureId, EscalateFailureRequest request) {
        logger.info("Escalating quality failure: {}, level: {}", failureId, request.getEscalationLevel());

        String escalationId = "ESC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // Determine who to notify based on escalation level
        List<String> notifiedParties = getNotificationList(request.getEscalationLevel());

        // Determine corrective actions based on impact
        List<String> correctiveActions = determineCorrectiveActions(request);

        EscalateFailureResponse response = new EscalateFailureResponse(
                escalationId,
                failureId,
                request.getEscalationLevel(),
                "ESCALATED",
                notifiedParties,
                correctiveActions,
                request.isProductionHold(),
                LocalDateTime.now(),
                "Quality Inspector" // In real system, get from security context
        );

        escalations.put(escalationId, response);

        // Log escalation
        logger.warn("QUALITY ESCALATION - Failure ID: {}, Level: {}, Production Hold: {}, Notified: {}",
                failureId, request.getEscalationLevel(), request.isProductionHold(), notifiedParties);

        if (request.isProductionHold()) {
            logger.error("PRODUCTION HOLD INITIATED for failure: {}", failureId);
            // In real system, would trigger production hold event
        }

        return response;
    }

    private List<String> getNotificationList(String escalationLevel) {
        return switch (escalationLevel) {
            case "SUPERVISOR" -> List.of("qa.supervisor@factory.com");
            case "QUALITY_MANAGER" -> List.of("qa.supervisor@factory.com", "qa.manager@factory.com");
            case "PRODUCTION_MANAGER" -> List.of("qa.supervisor@factory.com", "qa.manager@factory.com",
                    "production.manager@factory.com");
            case "EXECUTIVE" -> List.of("qa.supervisor@factory.com", "qa.manager@factory.com",
                    "production.manager@factory.com", "coo@factory.com");
            default -> List.of("qa.supervisor@factory.com");
        };
    }

    private List<String> determineCorrectiveActions(EscalateFailureRequest request) {
        List<String> actions = new ArrayList<>();

        if (request.getSuggestedActions() != null && !request.getSuggestedActions().isEmpty()) {
            actions.addAll(request.getSuggestedActions());
        } else {
            // Default actions based on impact
            switch (request.getImpact()) {
                case "CRITICAL" -> {
                    actions.add("Immediately halt production");
                    actions.add("Quarantine all affected materials");
                    actions.add("Conduct full root cause analysis");
                    actions.add("Implement emergency corrective measures");
                }
                case "HIGH" -> {
                    actions.add("Review affected batches");
                    actions.add("Increase inspection frequency");
                    actions.add("Investigate root cause");
                }
                case "MEDIUM" -> {
                    actions.add("Document the failure");
                    actions.add("Review with supervisor");
                    actions.add("Monitor for patterns");
                }
                default -> {
                    actions.add("Log the incident");
                    actions.add("Continue monitoring");
                }
            }
        }

        return actions;
    }

    /**
     * Get escalation by ID
     */
    public EscalateFailureResponse getEscalation(String escalationId) {
        EscalateFailureResponse escalation = escalations.get(escalationId);
        if (escalation == null) {
            throw new IllegalArgumentException("Escalation not found: " + escalationId);
        }
        return escalation;
    }
}
