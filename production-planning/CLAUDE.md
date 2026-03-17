# ProductionPlanningService
Part of the **New Ecosystem** ecosystem.

## This service owns
Orchestrates production schedules, work orders, capacity planning, and material requirements planning across all manufacturing resources

## Tech stack
Spring Boot

## API contracts — implement exactly as specified
- `POST /production-schedules` — create production schedule from demand requirements
- `POST /work-orders` — generate work orders for workshops and assembly lines
- `POST /production-orders` — create production order with material reservations
- `GET /production-orders/{orderId}/feasibility` — check production feasibility against capacity and materials
- `PUT /production-schedules/{scheduleId}/reschedule` — reschedule production based on delays or constraints
- `GET /capacity/utilization` — query current capacity utilization across all resources
- `GET /production-orders/{orderId}/status` — get production order status and progress

## Event contracts — implement exactly as specified
- ▶ Produces `production.order.created` — new production order created with material requirements
- ▶ Produces `production.work.order.scheduled` — work order assigned to workshop or assembly line
- ▶ Produces `production.schedule.changed` — production schedule updated due to constraints or delays
- ▶ Produces `production.capacity.constraint` — capacity bottleneck detected requiring intervention
- ◀ Consumes `bom.version.published` — new BOM affects material requirements planning
- ◀ Consumes `inventory.stock.changed` — stock changes affect production feasibility
- ◀ Consumes `inventory.low.stock.alert` — material shortage may impact production schedules

## Service dependencies
- `bom-management`
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
