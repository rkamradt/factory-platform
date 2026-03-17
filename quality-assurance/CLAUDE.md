# QualityAssuranceService
Part of the **New Ecosystem** ecosystem.

## This service owns
Manages quality standards, inspection processes, test execution, and quality certifications throughout the manufacturing lifecycle

## Tech stack
Spring Boot

## API contracts — implement exactly as specified
- `POST /inspections/schedule` — schedule quality inspections for materials, components, or products
- `POST /inspections/{inspectionId}/results` — record inspection results and quality measurements
- `GET /quality/history/{materialId}` — query quality history and defect patterns for a material
- `POST /quality/standards` — create or update quality standards and inspection procedures
- `GET /quality/certificates/{batchId}` — generate quality certificates for compliant batches
- `POST /quality/failures/{failureId}/escalate` — escalate quality failures and trigger corrective actions

## Event contracts — implement exactly as specified
- ▶ Produces `quality.inspection.completed` — inspection completed with pass/fail results and measurements
- ▶ Produces `quality.failure.detected` — quality failure detected requiring corrective action
- ▶ Produces `quality.certificate.issued` — quality certificate issued enabling material use or shipping
- ▶ Produces `quality.trend.alert` — statistical process control violation detected
- ◀ Consumes `procurement.goods.received` — schedule incoming inspection for received materials
- ◀ Consumes `warehouse.goods.received` — coordinate with physical receipt for inspection scheduling

## Service dependencies
- `procurement`
- `warehouse`

## Platform context
See @../ecosystem.json for the full ecosystem registry.
See @../CLAUDE.md for platform-wide architecture rules.

## Build & run
```bash
mvn spring-boot:run
mvn test
```

## Implementation notes
- Do not share a database with any other service
- All inter-service calls must go through the declared API or event contracts above
- Prefer async (Kafka) for cross-domain side effects; use sync API calls only for same-domain queries
