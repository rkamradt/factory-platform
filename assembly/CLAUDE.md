# AssemblyService
Part of the **New Ecosystem** ecosystem.

## This service owns
Manages final product assembly line operations, workstation scheduling, and component integration into finished products

## Tech stack
Spring Boot

## API contracts — implement exactly as specified
- `POST /assembly-orders/{orderId}/accept` — accept assembly order from production planning
- `POST /assembly-orders/{orderId}/schedule` — schedule work on assembly lines or workstations
- `PUT /assembly-orders/{orderId}/progress` — update assembly progress through build stages
- `POST /lines/{lineId}/status` — report line status, downtime, or bottlenecks
- `POST /assembly-orders/{orderId}/consumption` — track component consumption and assembly yield
- `POST /operators/{operatorId}/assign` — assign operators to specialized assembly tasks

## Event contracts — implement exactly as specified
- ▶ Produces `assembly.order.completed` — finished product ready for shipping or final inspection
- ▶ Produces `assembly.line.breakdown` — assembly line downtime affecting production capacity
- ▶ Produces `assembly.quality.issue.detected` — assembly problem detected requiring possible rework
- ▶ Produces `assembly.component.consumed` — component consumed in assembly process with yield tracking
- ◀ Consumes `production.work.order.scheduled` — work order assigned to assembly line for execution
- ◀ Consumes `machining.work.order.completed` — machined components available for assembly
- ◀ Consumes `fabrication.work.order.completed` — fabricated components available for assembly
- ◀ Consumes `quality.inspection.completed` — quality results may require rework or process adjustments
- ◀ Consumes `inventory.material.reserved` — confirms all components available for assembly

## Service dependencies
- `production-planning`
- `machining-workshop`
- `fabrication-workshop`
- `quality-assurance`
- `inventory`

## Platform context
See @../rkamradt-platform/ecosystem.json for the full ecosystem registry.
See @../rkamradt-platform/CLAUDE.md for platform-wide architecture rules.

## Build & run
```bash
mvn spring-boot:run
mvn test
```

## Implementation notes
- Do not share a database with any other service
- All inter-service calls must go through the declared API or event contracts above
- Prefer async (Kafka) for cross-domain side effects; use sync API calls only for same-domain queries
