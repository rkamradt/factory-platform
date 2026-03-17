# ProcurementService
Part of the **New Ecosystem** ecosystem.

## This service owns
Manages supplier relationships, purchase orders, and material purchasing workflows including automated reordering

## Tech stack
Spring Boot

## API contracts — implement exactly as specified
- `POST /purchase-orders` — create new purchase order for materials
- `GET /purchase-orders/{poId}` — get purchase order status and details
- `POST /purchase-orders/{poId}/receive` — record goods receipt and update PO status
- `GET /suppliers/{supplierId}/catalog` — search supplier catalog for available materials
- `POST /suppliers/{supplierId}/performance` — update supplier performance metrics
- `POST /reorder` — create purchase orders for materials below reorder points
- `GET /purchase-orders/delivery-schedule` — get expected delivery schedule for planning

## Event contracts — implement exactly as specified
- ▶ Produces `procurement.purchase.order.created` — new purchase order created and sent to supplier
- ▶ Produces `procurement.materials.ordered` — materials ordered with expected delivery date
- ▶ Produces `procurement.goods.received` — materials received from supplier and ready for inventory
- ▶ Produces `procurement.supplier.performance.updated` — supplier performance metrics have been updated
- ◀ Consumes `inventory.low.stock.alert` — material has fallen below reorder point - triggers purchasing
- ◀ Consumes `bom.component.specification.changed` — component specs changed - may affect supplier requirements

## Service dependencies
- `inventory`
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
