# MachiningWorkshopService
Part of the **New Ecosystem** ecosystem.

## This service owns
Manages CNC machines, lathes, mills scheduling and machining work order execution with operator assignments

## Tech stack
Spring Boot

## API contracts — implement exactly as specified
- `POST /work-orders/{orderId}/accept` — accept machining work order from production planning
- `POST /work-orders/{orderId}/schedule` — schedule work on specific machines or general machining pool
- `PUT /work-orders/{orderId}/progress` — update work order progress through setup, machining, inspection stages
- `POST /machines/{machineId}/status` — report machine status, downtime, or maintenance requirements
- `POST /work-orders/{orderId}/consumption` — track material consumption, scrap rates, and yield
- `POST /operators/{operatorId}/assign` — assign qualified operators to machines and work orders

## Event contracts — implement exactly as specified
- ▶ Produces `machining.work.order.completed` — machining work completed and component ready for next production stage
- ▶ Produces `machining.machine.maintenance.required` — machine breakdown or scheduled maintenance affecting production capacity
- ▶ Produces `machining.quality.issue.detected` — machining quality problem detected requiring possible rework
- ▶ Produces `machining.material.consumed` — raw material consumed in machining process with scrap tracking
- ◀ Consumes `production.work.order.scheduled` — work order assigned to machining workshop for execution
- ◀ Consumes `quality.inspection.completed` — quality results may require rework or process adjustments
- ◀ Consumes `inventory.material.reserved` — confirms raw materials available for machining work order

## Service dependencies
- `production-planning`
- `quality-assurance`
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
