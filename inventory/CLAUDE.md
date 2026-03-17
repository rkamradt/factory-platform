# InventoryService
Part of the **New Ecosystem** ecosystem.

## This service owns
Tracks stock levels, locations, reservations, and movements for raw materials, components, and finished goods

## Tech stack
Spring Boot

## API contracts — implement exactly as specified
- `POST /inventory/check-availability` — check availability for list of materials with quantities
- `POST /inventory/reserve` — reserve materials for a production order
- `POST /inventory/allocate` — allocate reserved materials for immediate use
- `POST /inventory/movements` — record stock movements (receipts, consumption, transfers)
- `GET /inventory/stock/{materialId}` — get current stock levels and locations for a material
- `POST /inventory/release-reservation` — release reserved materials when production completes or cancels
- `GET /inventory/locations/{locationId}/stock` — get all stock at a specific location

## Event contracts — implement exactly as specified
- ▶ Produces `inventory.stock.changed` — stock level changed for a material at a location
- ▶ Produces `inventory.material.reserved` — materials reserved for production order
- ▶ Produces `inventory.stock.movement` — material moved between locations or consumed/received
- ▶ Produces `inventory.low.stock.alert` — material has fallen below reorder point
- ◀ Consumes `bom.version.published` — new BOM published - may need to track new materials

## Service dependencies
- `bom-management`

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
