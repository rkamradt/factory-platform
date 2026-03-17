package com.factory.assembly.service;

import com.factory.assembly.dto.LineStatusRequest;
import com.factory.assembly.dto.LineStatusResponse;
import com.factory.assembly.event.AssemblyEventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AssemblyLineService {

    private static final Logger logger = LoggerFactory.getLogger(AssemblyLineService.class);
    private final AssemblyEventProducer eventProducer;

    public AssemblyLineService(AssemblyEventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    public LineStatusResponse reportLineStatus(String lineId, LineStatusRequest request) {
        logger.info("Reporting status for assembly line: {} - status: {}", lineId, request.getStatus());

        // TODO: Implement line status tracking logic
        // TODO: Update line status in database
        // TODO: Notify production planning of capacity changes
        // TODO: Schedule maintenance if needed

        LineStatusResponse response = LineStatusResponse.builder()
                .lineId(lineId)
                .status(request.getStatus())
                .downtimeReason(request.getDowntimeReason())
                .reportedAt(LocalDateTime.now())
                .message("Line status updated successfully")
                .build();

        // Publish line breakdown event if line is down
        if ("DOWN".equalsIgnoreCase(request.getStatus()) || "BREAKDOWN".equalsIgnoreCase(request.getStatus())) {
            logger.warn("Assembly line {} is down: {}", lineId, request.getDowntimeReason());
            eventProducer.publishAssemblyLineBreakdown(
                lineId,
                request.getDowntimeReason(),
                request.getEstimatedDowntimeMinutes()
            );
        }

        return response;
    }
}
