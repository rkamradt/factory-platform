# FabricationWorkshopService
Part of the **New Ecosystem** ecosystem.

## This service owns
Manages welding stations, cutting equipment, forming machines scheduling and fabrication work order execution with operator certifications

## Tech stack
Spring Boot

## API contracts — implement exactly as specified
- `POST /work-orders/{orderId}/accept` — accept fabrication work order from production planning
- `POST /work-orders/{orderId}/schedule` — schedule work on specific equipment or general fabrication pool
- `PUT /work-orders/{orderId}/progress` — update work order progress through cutting, forming, welding, finishing stages
- `POST /equipment/{equipmentId}/status` — report equipment status, downtime, or maintenance requirements
- `POST /work-orders/{orderId}/consumption` — track material consumption, waste rates, and rework
- `POST /operators/{operatorId}/assign` — assign certified operators to welding or specialized fabrication processes

## Event contracts — implement exactly as specified
- ▶ Produces `fabrication.work.order.completed` — fabrication work completed and component ready for next production stage
- ▶ Produces `fabrication.equipment.maintenance.required` — equipment breakdown or scheduled maintenance affecting production capacity
- ▶ Produces `fabrication.quality.issue.detected` — fabrication quality problem detected requiring possible rework
- ▶ Produces `fabrication.material.consumed` — material consumed in fabrication process with waste tracking
- ◀ Consumes `production.work.order.scheduled` — work order assigned to fabrication workshop for execution
- ◀ Consumes `quality.inspection.completed` — quality results may require rework or process adjustments
- ◀ Consumes `inventory.material.reserved` — confirms materials available for fabrication work order

## Service dependencies
- `production-planning`
- `quality-assurance`
- `inventory`

## Platform context
See @../ecosystem.json for the full ecosystem registry.

## Build & run
```bash
mvn spring-boot:run
mvn test
```

## Port Configuration
This service runs on port **8088**

## Implementation notes
- Do not share a database with any other service
- All inter-service calls must go through the declared API or event contracts above
- Prefer async (Kafka) for cross-domain side effects; use sync API calls only for same-domain queries
- Operator certifications are validated for specialized processes, especially welding operations
- Tracks waste rates and material consumption through multiple fabrication stages
- Equipment status impacts capacity planning and production scheduling
