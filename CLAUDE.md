
# New Ecosystem — Platform Architecture Context

## Ecosystem registry
See @ecosystem.json for the full service registry (APIs, event contracts, dependencies).

## Services in this platform
- **BOMManagementService** (`bom-management`) — Owns versioned Bill of Materials, product definitions, component specifications, and material requirements calculations
- **InventoryService** (`inventory`) — Tracks stock levels, locations, reservations, and movements for raw materials, components, and finished goods
- **ComplianceService** (`compliance`) — Authorizes and logs all material transfers with audit trail, acting as gatekeeper for material movements
- **ProcurementService** (`procurement`) — Manages supplier relationships, purchase orders, and material purchasing workflows including automated reordering
- **WarehouseService** (`warehouse`) — Manages physical receiving, storage, picking, packing, and shipping operations for all materials and products
- **ProductionPlanningService** (`production-planning`) — Orchestrates production schedules, work orders, capacity planning, and material requirements planning across all manufacturing resources
- **QualityAssuranceService** (`quality-assurance`) — Manages quality standards, inspection processes, test execution, and quality certifications throughout the manufacturing lifecycle
- **MachiningWorkshopService** (`machining-workshop`) — Manages CNC machines, lathes, mills scheduling and machining work order execution with operator assignments
- **FabricationWorkshopService** (`fabrication-workshop`) — Manages welding stations, cutting equipment, forming machines scheduling and fabrication work order execution with operator certifications
- **AssemblyService** (`assembly`) — Manages final product assembly line operations, workstation scheduling, and component integration into finished products

## Architecture principles
- Each service owns exactly one bounded context — no shared databases
- Cross-domain communication via Kafka events; same-domain via direct API calls
- New services must declare their full API surface and event contracts before implementation
- Refer to @ecosystem.json for authoritative contract definitions

## Scaffold workflow
When creating a new service:
1. Confirm service spec matches @ecosystem.json entry for that service id
2. Generate Spring Boot project using the service's tech stack
3. Stub all API endpoints listed in the spec with proper request/response models
4. Wire Kafka producer/consumer for all events in the spec
5. Add a CLAUDE.md in the new service repo (use the template below)

## Per-service CLAUDE.md template
```markdown
# <ServiceName>
Part of the New Ecosystem ecosystem. See @../rkamradt-platform/ecosystem.json.

## This service owns
<purpose from spec>

## API contracts (implement exactly)
<list from spec>

## Event contracts (implement exactly)
<list from spec>

## Dependencies
<list from spec>

## Build & run
mvn spring-boot:run
mvn test
```
