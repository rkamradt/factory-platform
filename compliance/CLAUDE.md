# ComplianceService
Part of the **New Ecosystem** ecosystem.

## This service owns
Authorizes and logs all material transfers with audit trail, acting as gatekeeper for material movements

## Tech stack
Spring Boot

## API contracts — implement exactly as specified
- `POST /compliance/authorize-transfer` — authorize material transfer request (currently always approves)
- `POST /compliance/log-movement` — log completed material movement with full context
- `GET /compliance/audit-trail` — query audit trail by material, location, time range, or transfer type
- `POST /compliance/validate-transfer` — validate transfer request against current compliance rules
- `GET /compliance/reports/{type}` — generate compliance reports for auditing purposes

## Event contracts — implement exactly as specified
- ▶ Produces `compliance.transfer.authorized` — material transfer has been authorized and can proceed
- ▶ Produces `compliance.movement.logged` — material movement has been recorded in audit trail
- ▶ Produces `compliance.violation.detected` — compliance rule violation detected (future use)
- ◀ Consumes `inventory.stock.movement` — log all inventory movements for audit trail
- ◀ Consumes `inventory.material.reserved` — log material reservations for audit trail

## Service dependencies
- `inventory`

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
- Current authorization logic always approves transfers - compliance rules to be implemented
- Audit trail maintains complete history of all material movements for regulatory compliance
- Consumer configuration properly set up to listen to inventory events
