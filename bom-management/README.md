# BOM Management Service

A Spring Boot microservice for managing versioned Bill of Materials (BOM), product definitions, component specifications, and material requirements calculations.

## Overview

The BOM Management Service is a foundational service in the factory platform ecosystem that owns all BOM-related data and operations. It provides APIs for creating and retrieving BOMs, calculating material requirements, and managing component specifications.

## Features

- Versioned BOM management with full component hierarchy
- Material requirements calculation (BOM explosion)
- Component specification management
- BOM validation (circular dependencies, completeness checks)
- Event-driven architecture with Kafka integration

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
java -jar target/bom-management-1.0.0-SNAPSHOT.jar
```

The service will start on port 8080 by default.

## API Endpoints

### BOM Management

- **POST /boms** - Create new BOM version for a product
  - Request body: `CreateBomRequest`
  - Response: `BomResponse`

- **GET /boms/{productId}/versions/{version}** - Retrieve specific BOM version with full component hierarchy
  - Response: `BomResponse`

- **GET /boms/{productId}/latest** - Get latest BOM version for a product
  - Response: `BomResponse`

- **POST /boms/{bomId}/explode** - Calculate material requirements for given production quantity
  - Request body: `ExplodeBomRequest`
  - Response: `MaterialRequirementsResponse`

- **POST /boms/{bomId}/validate** - Validate BOM for circular dependencies and completeness
  - Response: `ValidationResponse`

### Component Management

- **GET /components/{componentId}/specifications** - Get component specifications and requirements
  - Response: `ComponentSpecificationResponse`

## Kafka Events

### Produced Events

- **bom.version.published** - Published when a new BOM version is created and ready for use
  - Key: bomId
  - Payload: { bomId, productId, version, publishedAt, status }

- **bom.component.specification.changed** - Published when component specifications are updated
  - Key: componentId
  - Payload: { componentId, specifications, changedAt }

## Configuration

Key configuration properties (see `application.properties`):

```properties
# Server
server.port=8080

# Kafka
spring.kafka.bootstrap-servers=localhost:9092

# Database (H2 in-memory for development)
spring.datasource.url=jdbc:h2:mem:bomdb
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

## Development

### H2 Console

Access the H2 database console at: http://localhost:8080/h2-console

- JDBC URL: `jdbc:h2:mem:bomdb`
- Username: `sa`
- Password: (leave blank)

### Health Check

Check service health: http://localhost:8080/actuator/health

### Logging

Logs are configured to show INFO level for application code and Kafka operations. Adjust in `application.properties`:

```properties
logging.level.com.factory.bom=INFO
logging.level.org.springframework.kafka=INFO
```

## Project Structure

```
bom-management/
├── src/
│   ├── main/
│   │   ├── java/com/factory/bom/
│   │   │   ├── config/          # Kafka and other configurations
│   │   │   ├── controller/      # REST controllers
│   │   │   ├── dto/             # Request/Response DTOs
│   │   │   ├── event/           # Kafka event producers
│   │   │   ├── service/         # Business logic
│   │   │   └── BomManagementApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       ├── java/com/factory/bom/
│       └── resources/
│           └── application-test.properties
├── pom.xml
├── CLAUDE.md
└── README.md
```

## Future Enhancements

- Implement database persistence layer with entity models
- Add BOM explosion algorithm for recursive material calculation
- Implement circular dependency detection algorithm
- Add authentication and authorization
- Configure production-ready database (PostgreSQL/MySQL)
- Add API documentation with Swagger/OpenAPI
- Implement caching for frequently accessed BOMs
- Add comprehensive integration tests

## Dependencies

This is a foundational service with no dependencies on other services.

## Related Services

Services that depend on BOM Management Service:
- Inventory Service
- Procurement Service
- Production Planning Service

## License

Proprietary - Factory Platform
