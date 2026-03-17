# BOMManagementService
Part of the **New Ecosystem** ecosystem.

## This service owns
Owns versioned Bill of Materials, product definitions, component specifications, and material requirements calculations

## Tech stack
Spring Boot

## API contracts — implement exactly as specified
- `POST /boms` — create new BOM version for a product
- `GET /boms/{productId}/versions/{version}` — retrieve specific BOM version with full component hierarchy
- `GET /boms/{productId}/latest` — get latest BOM version for a product
- `POST /boms/{bomId}/explode` — calculate material requirements for given production quantity
- `GET /components/{componentId}/specifications` — get component specifications and requirements
- `POST /boms/{bomId}/validate` — validate BOM for circular dependencies and completeness

## Event contracts — implement exactly as specified
- ▶ Produces `bom.version.published` — new BOM version is published and ready for use
- ▶ Produces `bom.component.specification.changed` — component specifications or requirements have been updated

## Service dependencies
(none)

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
