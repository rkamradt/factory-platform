# Compliance Service

A Spring Boot microservice for authorizing and logging all material transfers with audit trail, acting as gatekeeper for material movements in the factory platform.

## Overview

The Compliance Service is a critical service that monitors, authorizes, and logs all material movements throughout the factory. It maintains a complete audit trail for regulatory compliance and acts as a gatekeeper for material transfers between locations.

## Features

- Material transfer authorization (currently always approves, rules to be implemented)
- Comprehensive movement logging with full context
- Audit trail query capabilities with multiple filter options
- Transfer validation against compliance rules
- Compliance report generation for auditing purposes
- Event-driven architecture with Kafka integration
- Automatic logging of all inventory movements via event consumption

## Tech Stack

- Java 17
- Spring Boot 3.2.0
- Spring Web
- Spring Data JPA
- Spring Kafka (Producer and Consumer)
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
java -jar target/compliance-1.0.0-SNAPSHOT.jar
```

The service will start on port 8082.

## API Endpoints

### Transfer Authorization

- **POST /compliance/authorize-transfer** - Authorize material transfer request
  - Request body: `AuthorizeTransferRequest`
  - Response: `AuthorizeTransferResponse`
  - Current behavior: Always approves (compliance rules to be implemented)

### Movement Logging

- **POST /compliance/log-movement** - Log completed material movement with full context
  - Request body: `LogMovementRequest`
  - Response: `LogMovementResponse`

### Audit Trail

- **GET /compliance/audit-trail** - Query audit trail with filters
  - Query parameters: materialId, location, movementType, transferType, startTime, endTime, orderId, page, size
  - Response: `AuditTrailResponse`

### Transfer Validation

- **POST /compliance/validate-transfer** - Validate transfer request against compliance rules
  - Request body: `ValidateTransferRequest`
  - Response: `ValidateTransferResponse`

### Reports

- **GET /compliance/reports/{type}** - Generate compliance reports for auditing
  - Path parameter: type (e.g., DAILY, WEEKLY, MONTHLY, TRANSFER_SUMMARY)
  - Query parameters: startDate, endDate
  - Response: `ComplianceReportResponse`

## Kafka Events

### Produced Events

- **compliance.transfer.authorized** - Published when material transfer is authorized
  - Key: authorizationId
  - Payload: { authorizationId, materialId, quantity, sourceLocation, destinationLocation, transferType, status, authorizedAt, expiresAt }

- **compliance.movement.logged** - Published when material movement is logged
  - Key: logId
  - Payload: { logId, materialId, quantity, sourceLocation, destinationLocation, movementType, executedBy, executedAt, loggedAt, authorizationId, orderId, additionalContext }

- **compliance.violation.detected** - Published when compliance rule violation detected (future use)
  - Key: materialId
  - Payload: { violationType, materialId, location, detectedAt, severity, details, requiresAction }

### Consumed Events

- **inventory.stock.movement** - Automatically logs all inventory movements
  - Creates audit trail entries for all stock movements

- **inventory.material.reserved** - Automatically logs material reservations
  - Creates audit trail entries for material reservations

## Configuration

Key configuration properties (see `application.properties`):

```properties
# Server
server.port=8082

# Kafka
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=compliance-service-group

# Database (H2 in-memory for development)
spring.datasource.url=jdbc:h2:mem:compliancedb
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

## Development

### H2 Console

Access the H2 database console at: http://localhost:8082/h2-console

- JDBC URL: `jdbc:h2:mem:compliancedb`
- Username: `sa`
- Password: (leave blank)

### Health Check

Check service health: http://localhost:8082/actuator/health

### Logging

Logs are configured to show INFO level for application code and Kafka operations. Adjust in `application.properties`:

```properties
logging.level.com.factory.compliance=INFO
logging.level.org.springframework.kafka=INFO
```

## Project Structure

```
compliance/
├── src/
│   ├── main/
│   │   ├── java/com/factory/compliance/
│   │   │   ├── config/          # Kafka producer and consumer configurations
│   │   │   ├── controller/      # REST controllers
│   │   │   ├── dto/             # Request/Response DTOs
│   │   │   ├── event/           # Kafka event producers and consumers
│   │   │   ├── service/         # Business logic
│   │   │   └── ComplianceApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       ├── java/com/factory/compliance/
│       └── resources/
│           └── application-test.properties
├── pom.xml
├── CLAUDE.md
└── README.md
```

## Implementation Notes

- Current authorization logic always approves transfers - comprehensive compliance rules to be implemented
- Audit trail maintains complete history of all material movements for regulatory compliance
- Event consumers automatically capture inventory movements and reservations
- In-memory storage used for demonstration - database persistence layer to be implemented
- Authorization tokens expire after 24 hours

## Future Enhancements

- Implement comprehensive compliance rule engine
- Add database persistence layer with entity models
- Implement authorization validation for movements
- Add role-based access control for authorizations
- Configure production-ready database (PostgreSQL/MySQL)
- Add API documentation with Swagger/OpenAPI
- Implement advanced reporting with data analytics
- Add compliance rule versioning and audit
- Implement alerting for policy violations
- Add comprehensive integration tests
- Implement retry and dead letter queue for event processing

## Dependencies

This service depends on:
- Inventory Service (via Kafka events)

## Related Services

Services that depend on Compliance Service:
- Warehouse Service (consumes compliance.transfer.authorized)
- Any service requiring material movement authorization

## License

Proprietary - Factory Platform
