package com.factory.quality.service;

import com.factory.quality.dto.InspectionResultResponse;
import com.factory.quality.dto.QualityCertificateResponse;
import com.factory.quality.event.QualityEventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CertificationService {

    private static final Logger logger = LoggerFactory.getLogger(CertificationService.class);
    private final InspectionService inspectionService;
    private final QualityEventProducer eventProducer;

    // In-memory storage for demo (replace with database in production)
    private final Map<String, QualityCertificateResponse> certificates = new HashMap<>();

    public CertificationService(InspectionService inspectionService, QualityEventProducer eventProducer) {
        this.inspectionService = inspectionService;
        this.eventProducer = eventProducer;
    }

    /**
     * Generate quality certificates for compliant batches
     */
    public QualityCertificateResponse generateCertificate(String batchId) {
        logger.info("Generating quality certificate for batch: {}", batchId);

        // Find all inspections for this batch
        List<InspectionResultResponse> batchInspections = inspectionService.getInspectionResultsForMaterial(batchId);

        if (batchInspections.isEmpty()) {
            throw new IllegalArgumentException("No inspections found for batch: " + batchId);
        }

        // Check if all inspections passed
        boolean allPassed = batchInspections.stream()
                .allMatch(r -> "PASS".equals(r.getResult()));

        if (!allPassed) {
            throw new IllegalStateException("Cannot issue certificate: batch has failed inspections");
        }

        // Generate certificate
        String certificateId = "CERT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String certificateNumber = "QC-" + LocalDateTime.now().getYear() + "-" +
                String.format("%06d", new Random().nextInt(999999));

        // Collect all measurements and test results
        Map<String, Object> certifiedSpecs = new HashMap<>();
        Map<String, Object> testResults = new HashMap<>();

        for (InspectionResultResponse inspection : batchInspections) {
            if (inspection.getMeasurements() != null) {
                certifiedSpecs.putAll(inspection.getMeasurements());
            }
        }

        String materialId = batchInspections.get(0).getMaterialId();
        List<String> inspectionIds = batchInspections.stream()
                .map(InspectionResultResponse::getInspectionId)
                .collect(Collectors.toList());

        QualityCertificateResponse certificate = new QualityCertificateResponse(
                certificateId,
                batchId,
                materialId,
                "Material Batch: " + batchId,
                certificateNumber,
                "ISSUED",
                LocalDateTime.now(),
                LocalDateTime.now().plusYears(1),
                "Quality Assurance Department",
                inspectionIds,
                certifiedSpecs,
                testResults,
                "ISO 9001:2015",
                "All quality inspections passed. Material meets specified requirements."
        );

        certificates.put(certificateId, certificate);

        // Publish certificate issued event
        eventProducer.publishQualityCertificateIssued(
                certificateId,
                batchId,
                materialId,
                certificateNumber,
                certifiedSpecs
        );

        logger.info("Quality certificate issued: {}", certificateNumber);
        return certificate;
    }

    /**
     * Get certificate by batch ID
     */
    public QualityCertificateResponse getCertificateForBatch(String batchId) {
        return certificates.values().stream()
                .filter(cert -> batchId.equals(cert.getBatchId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Certificate not found for batch: " + batchId));
    }
}
