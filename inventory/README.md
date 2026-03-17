# Inventory Service

A Spring Boot microservice for tracking stock levels, locations, reservations, and movements for raw materials, components, and finished goods in the factory platform ecosystem.

## Overview

The Inventory Service is responsible for managing all inventory-related operations including stock tracking, material reservations for production orders, stock movements, and location management. It provides real-time visibility into material availability and generates alerts when stock levels fall below reorder points.

## Features

- Real-time stock level tracking across multiple locations
- Material availability checking for production planning
- Reservation system for production orders
- Stock movement recording (receipts, consumption, transfers, adjustments)
- Location-based inventory management
- Low stock alerting
- Event-driven architecture with Kafka integration
- Integration with BOM Management Service for material tracking

## Tech Stack

- Java 17
- Spring Boot 3.2.0
- Spring Web
- Spring Data JPA
- Spring Kafka
- H2 Database (development)
- Maven
- Lombok

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Apache Kafka (running on localhost:9092)

## Building the Application

```bash
# Clean and build
mvn clean install

# Build without tests
mvn clean install -DskipTests

# Run tests only
mvn test
```

## Running the Application

```bash
# Run with Maven
mvn spring-boot:run

# Run the JAR directly
java -jar target/inventory-1.0.0-SNAPSHOT.jar
```

The service will start on port 8081 by default.

## API Endpoints

### Availability Check

- **POST /inventory/check-availability** - Check availability for list of materials with quantities
  - Request body: `CheckAvailabilityRequest`
  - Response: `AvailabilityResponse`

### Reservation Management

- **POST /inventory/reserve** - Reserve materials for a production order
  - Request body: `ReserveMaterialsRequest`
  - Response: `ReservationResponse`

- **POST /inventory/release-reservation** - Release reserved materials when production completes or cancels
  - Request body: `ReleaseReservationRequest`
  - Response: `ReleaseResponse`

### Allocation

- **POST /inventory/allocate** - Allocate reserved materials for immediate use
  - Request body: `AllocateMaterialsRequest`
  - Response: `AllocationResponse`

### Stock Movements

- **POST /inventory/movements** - Record stock movements (receipts, consumption, transfers)
  - Request body: `RecordMovementRequest`
  - Response: `MovementResponse`

### Stock Queries

- **GET /inventory/stock/{materialId}** - Get current stock levels and locations for a material
  - Response: `StockLevelResponse`

- **GET /inventory/locations/{locationId}/stock** - Get all stock at a specific location
  - Response: `LocationStockResponse`

## Kafka Events

### Produced Events

- **inventory.stock.changed** - Published when stock level changes for a material at a location
  - Key: materialId
  - Payload: { materialId, locationId, quantity, changedAt }

- **inventory.material.reserved** - Published when materials are reserved for production order
  - Key: reservationId
  - Payload: { reservationId, productionOrderId, materials, reservedAt }

- **inventory.stock.movement** - Published when material moves between locations or consumed/received
  - Key: movementId
  - Payload: { movementId, materialId, movementType, quantity, fromLocationId, toLocationId, movedAt }

- **inventory.low.stock.alert** - Published when material has fallen below reorder point
  - Key: materialId
  - Payload: { materialId, locationId, currentQuantity, reorderPoint, severity, alertedAt }

### Consumed Events

- **bom.version.published** - Consumed when a new BOM is published - tracks new materials
  - Processes new BOM versions to identify and track new materials in the inventory system

## Configuration

Key configuration properties (see `application.properties`):

```properties
# Server
server.port=8081

# Kafka
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=inventory-service

# Database (H2 in-memory for development)
spring.datasource.url=jdbc:h2:mem:inventorydb
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

## Development

### H2 Console

Access the H2 database console at: http://localhost:8081/h2-console

- JDBC URL: `jdbc:h2:mem:inventorydb`
- Username: `sa`
- Password: (leave blank)

### Health Check

Check service health: http://localhost:8081/actuator/health

### Logging

Logs are configured to show INFO level for application code and Kafka operations. Adjust in `application.properties`:

```properties
logging.level.com.factory.inventory=INFO
logging.level.org.springframework.kafka=INFO
```

## Project Structure

```
inventory/
├── src/
│   ├── main/
│   │   ├── java/com/factory/inventory/
│   │   │   ├── config/          # Kafka producer and consumer configurations
│   │   │   ├── controller/      # REST controllers
│   │   │   ├── dto/             # Request/Response DTOs
│   │   │   ├── event/           # Kafka event producers and consumers
│   │   │   ├── service/         # Business logic
│   │   │   └── InventoryApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       ├── java/com/factory/inventory/
│       └── resources/
│           └── application-test.properties
├── pom.xml
├── CLAUDE.md
└── README.md
```

## API Request Examples

### Check Availability

```json
POST /inventory/check-availability
{
  "materials": [
    {
      "materialId": "MAT001",
      "quantity": 100.0,
      "unit": "kg"
    }
  ]
}
```

### Reserve Materials

```json
POST /inventory/reserve
{
  "productionOrderId": "PO-2024-001",
  "materials": [
    {
      "materialId": "MAT001",
      "quantity": 50.0,
      "unit": "kg",
      "preferredLocationId": "WH-A1"
    }
  ],
  "reservedBy": "production-planner",
  "notes": "For production order PO-2024-001"
}
```

### Record Movement

```json
POST /inventory/movements
{
  "materialId": "MAT001",
  "movementType": "RECEIPT",
  "quantity": 500.0,
  "unit": "kg",
  "toLocationId": "WH-A1",
  "referenceId": "PO-SUPPLIER-123",
  "performedBy": "warehouse-manager",
  "notes": "Received from supplier XYZ"
}
```

## Future Enhancements

- Implement database persistence layer with entity models
- Add batch processing for large inventory operations
- Implement advanced reservation algorithms (FIFO, LIFO, location optimization)
- Add support for lot tracking and serial numbers
- Implement cycle counting and physical inventory features
- Add authentication and authorization
- Configure production-ready database (PostgreSQL/MySQL)
- Add API documentation with Swagger/OpenAPI
- Implement caching for frequently accessed stock data
- Add comprehensive integration tests
- Implement stock forecasting and predictive analytics
- Add support for multi-warehouse routing

## Dependencies

This service depends on:
- BOM Management Service (consumes bom.version.published events)

## Related Services

Services that depend on Inventory Service:
- Production Planning Service
- Procurement Service
- Warehouse Service

## License

Proprietary - Factory Platform
