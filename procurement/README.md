# ProcurementService

Part of the Factory Platform ecosystem - manages supplier relationships, purchase orders, and material purchasing workflows including automated reordering.

## Service Information

- **Service ID**: procurement
- **Port**: 8083
- **Tech Stack**: Spring Boot 3.2.0, Java 17, Kafka
- **Database**: H2 (in-memory for development)

## Purpose

Manages supplier relationships, purchase orders, and material purchasing workflows including automated reordering. This service handles all procurement operations from supplier catalog management to goods receipt and supplier performance tracking.

## API Endpoints

### Purchase Order Management

- **POST /purchase-orders** - Create new purchase order for materials
- **GET /purchase-orders/{poId}** - Get purchase order status and details
- **POST /purchase-orders/{poId}/receive** - Record goods receipt and update PO status
- **GET /purchase-orders/delivery-schedule** - Get expected delivery schedule for planning

### Supplier Management

- **GET /suppliers/{supplierId}/catalog** - Search supplier catalog for available materials
- **POST /suppliers/{supplierId}/performance** - Update supplier performance metrics

### Automated Reordering

- **POST /reorder** - Create purchase orders for materials below reorder points

## Event Architecture

### Events Produced

- **procurement.purchase.order.created** - New purchase order created and sent to supplier
- **procurement.materials.ordered** - Materials ordered with expected delivery date
- **procurement.goods.received** - Materials received from supplier and ready for inventory
- **procurement.supplier.performance.updated** - Supplier performance metrics have been updated

### Events Consumed

- **inventory.low.stock.alert** - Material has fallen below reorder point - triggers purchasing
- **bom.component.specification.changed** - Component specs changed - may affect supplier requirements

## Dependencies

- **inventory** - For stock level information and material data
- **bom-management** - For component specifications and material requirements

## Project Structure

```
procurement/
├── src/main/java/com/factory/procurement/
│   ├── ProcurementApplication.java           # Main Spring Boot application
│   ├── config/
│   │   └── KafkaConfig.java                  # Kafka producer/consumer configuration
│   ├── controller/
│   │   ├── PurchaseOrderController.java      # Purchase order REST endpoints
│   │   ├── SupplierController.java           # Supplier REST endpoints
│   │   └── ReorderController.java            # Reorder REST endpoints
│   ├── service/
│   │   ├── ProcurementService.java           # Purchase order business logic
│   │   └── SupplierService.java              # Supplier management logic
│   ├── event/
│   │   ├── ProcurementEventProducer.java     # Kafka event publisher
│   │   └── ProcurementEventConsumer.java     # Kafka event listener
│   └── dto/
│       ├── CreatePurchaseOrderRequest.java
│       ├── PurchaseOrderResponse.java
│       ├── GoodsReceiptRequest.java
│       ├── SupplierCatalogResponse.java
│       ├── SupplierPerformanceRequest.java
│       ├── ReorderRequest.java
│       ├── ReorderResponse.java
│       └── DeliveryScheduleResponse.java
└── src/main/resources/
    └── application.properties                # Service configuration
```

## Building and Running

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Kafka (running on localhost:9092)

### Build

```bash
cd procurement
mvn clean install
```

### Run

```bash
mvn spring-boot:run
```

The service will start on port 8083.

### Test

```bash
mvn test
```

## Configuration

Key configuration properties in `application.properties`:

- `server.port=8083` - Service port
- `spring.kafka.bootstrap-servers=localhost:9092` - Kafka connection
- `spring.datasource.url=jdbc:h2:mem:procurementdb` - Database connection

## Health Check

```bash
curl http://localhost:8083/actuator/health
```

## Sample Usage

### Create Purchase Order

```bash
curl -X POST http://localhost:8083/purchase-orders \
  -H "Content-Type: application/json" \
  -d '{
    "supplierId": "SUPP-001",
    "supplierName": "ABC Supplier",
    "requestedDeliveryDate": "2026-03-24T10:00:00",
    "materials": [
      {
        "materialId": "MAT-001",
        "materialName": "Steel Plate",
        "quantity": 100,
        "unit": "kg",
        "unitPrice": 25.50,
        "specifications": "Grade A36"
      }
    ],
    "notes": "Urgent order"
  }'
```

### Get Purchase Order

```bash
curl http://localhost:8083/purchase-orders/PO-12345678
```

### Record Goods Receipt

```bash
curl -X POST http://localhost:8083/purchase-orders/PO-12345678/receive \
  -H "Content-Type: application/json" \
  -d '{
    "materials": [
      {
        "materialId": "MAT-001",
        "receivedQuantity": 100,
        "condition": "GOOD",
        "batchNumber": "BATCH-001"
      }
    ],
    "receivedBy": "John Doe"
  }'
```

### Get Supplier Catalog

```bash
curl http://localhost:8083/suppliers/SUPP-001/catalog
```

### Trigger Automated Reorder

```bash
curl -X POST http://localhost:8083/reorder \
  -H "Content-Type: application/json" \
  -d '{
    "autoApprove": true
  }'
```

## Development Notes

- Database is currently in-memory (H2) for development - all data is lost on restart
- TODO: Implement database persistence for production use
- TODO: Add authentication and authorization
- TODO: Implement actual supplier integration (EDI, API, etc.)
- TODO: Add more sophisticated reorder algorithms
- TODO: Implement supplier selection logic (best price, fastest delivery, etc.)
- TODO: Add purchase approval workflows
- TODO: Implement purchase order lifecycle management

## Architecture Principles

- Each service has its own database (H2 for development)
- Inter-service communication via Kafka events for async operations
- RESTful APIs for synchronous queries
- Event-driven architecture for cross-domain operations
- All events follow the pattern: `{service}.{entity}.{action}`
