# WarehouseService
Part of the **New Ecosystem** ecosystem.

## This service owns
Manages physical receiving, storage, picking, packing, and shipping operations for all materials and products

## Tech stack
Spring Boot

## API contracts — implement exactly as specified
- `POST /receiving/goods` — receive goods from suppliers with quality verification
- `POST /picking/orders/{orderId}` — pick materials for production orders
- `POST /shipping/products` — pack and ship finished products to customers
- `POST /transfers` — transfer materials between storage locations
- `POST /inventory/reconcile` — reconcile physical vs system inventory counts
- `GET /locations/{locationId}/capacity` — query storage location capacity and utilization
- `POST /dock/schedule` — schedule dock appointments for receiving/shipping

## Event contracts — implement exactly as specified
- ▶ Produces `warehouse.goods.received` — materials physically received and stored
- ▶ Produces `warehouse.materials.picked` — materials picked and ready for production
- ▶ Produces `warehouse.products.shipped` — finished products shipped to customer
- ▶ Produces `warehouse.inventory.discrepancy` — physical count differs from system inventory
- ◀ Consumes `procurement.goods.received` — schedule physical receiving of purchased materials
- ◀ Consumes `inventory.material.reserved` — schedule picking for reserved materials
- ◀ Consumes `compliance.transfer.authorized` — execute authorized material transfers

## Service dependencies
- `procurement`
- `inventory`
- `compliance`

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
