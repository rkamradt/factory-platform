package com.factory.warehouse.service;

import com.factory.warehouse.dto.ReconcileInventoryRequest;
import com.factory.warehouse.dto.ReconcileInventoryResponse;
import com.factory.warehouse.event.WarehouseEventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InventoryReconciliationService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryReconciliationService.class);
    private final WarehouseEventProducer eventProducer;

    public InventoryReconciliationService(WarehouseEventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    public ReconcileInventoryResponse reconcileInventory(ReconcileInventoryRequest request) {
        logger.info("Reconciling inventory for location: {}", request.getLocationId());

        // TODO: Implement actual reconciliation logic
        // TODO: Compare physical counts with system inventory
        // TODO: Calculate variances
        // TODO: Adjust inventory if needed
        // TODO: Persist to database

        String reconciliationId = UUID.randomUUID().toString();

        // Simulate system counts and calculate discrepancies
        List<ReconcileInventoryResponse.DiscrepancyDetail> discrepancies = request.getCounts().stream()
                .map(count -> {
                    // Simulate system count (in real implementation, fetch from inventory service)
                    Double systemCount = count.getPhysicalCount() + (Math.random() > 0.7 ? (Math.random() - 0.5) * 10 : 0);
                    Double variance = count.getPhysicalCount() - systemCount;

                    return ReconcileInventoryResponse.DiscrepancyDetail.builder()
                            .materialId(count.getMaterialId())
                            .systemCount(systemCount)
                            .physicalCount(count.getPhysicalCount())
                            .variance(variance)
                            .unit(count.getUnit())
                            .action(Math.abs(variance) > 0.1 ? "ADJUSTED" : "IGNORED")
                            .build();
                })
                .filter(d -> Math.abs(d.getVariance()) > 0.1) // Only include significant variances
                .collect(Collectors.toList());

        ReconcileInventoryResponse response = ReconcileInventoryResponse.builder()
                .reconciliationId(reconciliationId)
                .locationId(request.getLocationId())
                .reconciledAt(LocalDateTime.now())
                .countedBy(request.getCountedBy())
                .totalDiscrepancies(discrepancies.size())
                .discrepancies(discrepancies)
                .build();

        // Publish inventory discrepancy event if there are significant discrepancies
        if (!discrepancies.isEmpty()) {
            Map<String, Object> discrepancyDetails = new HashMap<>();
            discrepancyDetails.put("countedBy", request.getCountedBy());
            discrepancyDetails.put("totalDiscrepancies", discrepancies.size());
            discrepancyDetails.put("discrepancies", discrepancies);

            eventProducer.publishInventoryDiscrepancy(reconciliationId, request.getLocationId(), discrepancyDetails);
        }

        return response;
    }
}
