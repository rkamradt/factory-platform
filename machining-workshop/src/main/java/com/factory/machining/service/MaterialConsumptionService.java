package com.factory.machining.service;

import com.factory.machining.dto.MaterialConsumptionRequest;
import com.factory.machining.dto.MaterialConsumptionResponse;
import com.factory.machining.event.MachiningEventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MaterialConsumptionService {

    private static final Logger logger = LoggerFactory.getLogger(MaterialConsumptionService.class);
    private final MachiningEventProducer eventProducer;

    public MaterialConsumptionService(MachiningEventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    public MaterialConsumptionResponse trackConsumption(String orderId, MaterialConsumptionRequest request) {
        logger.info("Tracking material consumption for work order: {}", orderId);

        // TODO: Implement actual material consumption tracking
        // TODO: Calculate scrap rates and yield
        // TODO: Update inventory consumption records
        // TODO: Persist to database

        String consumptionId = UUID.randomUUID().toString();

        List<MaterialConsumptionResponse.MaterialUsageDetail> usageDetails = request.getMaterials().stream()
                .map(material -> {
                    double scrapRate = 0.0;
                    if (material.getScrapQuantity() != null && material.getConsumedQuantity() != null) {
                        scrapRate = (material.getScrapQuantity() / material.getConsumedQuantity()) * 100;
                    }

                    return MaterialConsumptionResponse.MaterialUsageDetail.builder()
                            .materialId(material.getMaterialId())
                            .materialName(material.getMaterialName())
                            .consumedQuantity(material.getConsumedQuantity())
                            .scrapQuantity(material.getScrapQuantity())
                            .unit(material.getUnit())
                            .scrapRate(scrapRate)
                            .build();
                })
                .collect(Collectors.toList());

        // Calculate total scrap rate
        double totalConsumed = usageDetails.stream()
                .mapToDouble(d -> d.getConsumedQuantity() != null ? d.getConsumedQuantity() : 0.0)
                .sum();
        double totalScrap = usageDetails.stream()
                .mapToDouble(d -> d.getScrapQuantity() != null ? d.getScrapQuantity() : 0.0)
                .sum();
        double totalScrapRate = totalConsumed > 0 ? (totalScrap / totalConsumed) * 100 : 0.0;

        MaterialConsumptionResponse response = MaterialConsumptionResponse.builder()
                .consumptionId(consumptionId)
                .workOrderId(UUID.randomUUID().toString())
                .orderId(orderId)
                .recordedAt(LocalDateTime.now())
                .materials(usageDetails)
                .totalScrapRate(totalScrapRate)
                .build();

        // Publish material consumed event
        Map<String, Object> consumptionDetails = new HashMap<>();
        consumptionDetails.put("materials", usageDetails);
        consumptionDetails.put("totalScrapRate", totalScrapRate);
        consumptionDetails.put("operatorId", request.getOperatorId());
        consumptionDetails.put("machineId", request.getMachineId());

        eventProducer.publishMaterialConsumed(response.getWorkOrderId(), consumptionId, consumptionDetails);

        return response;
    }
}
