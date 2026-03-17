# AssemblyService

Manages final product assembly line operations, workstation scheduling, and component integration into finished products.

## Overview

The AssemblyService is responsible for:
- Accepting and scheduling assembly orders from production planning
- Managing assembly line and workstation operations
- Tracking assembly progress through build stages
- Monitoring line status and downtime
- Recording component consumption and assembly yield
- Assigning operators to specialized assembly tasks

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Kafka (running on localhost:9092)

## Build

```bash
mvn clean install
```

## Run

```bash
mvn spring-boot:run
```

The service will start on port **8089**.

## Test

```bash
mvn test
```

## API Endpoints

### Assembly Orders
- `POST /assembly-orders/{orderId}/accept` - Accept assembly order from production planning
- `POST /assembly-orders/{orderId}/schedule` - Schedule work on assembly lines or workstations
- `PUT /assembly-orders/{orderId}/progress` - Update assembly progress through build stages
- `POST /assembly-orders/{orderId}/consumption` - Track component consumption and assembly yield

### Assembly Lines
- `POST /lines/{lineId}/status` - Report line status, downtime, or bottlenecks

### Operators
- `POST /operators/{operatorId}/assign` - Assign operators to specialized assembly tasks

## Kafka Events

### Produces
- `assembly.order.completed` - Finished product ready for shipping or final inspection
- `assembly.line.breakdown` - Assembly line downtime affecting production capacity
- `assembly.quality.issue.detected` - Assembly problem detected requiring possible rework
- `assembly.component.consumed` - Component consumed in assembly process with yield tracking

### Consumes
- `production.work.order.scheduled` - Work order assigned to assembly line for execution
- `machining.work.order.completed` - Machined components available for assembly
- `fabrication.work.order.completed` - Fabricated components available for assembly
- `quality.inspection.completed` - Quality results may require rework or process adjustments
- `inventory.material.reserved` - Confirms all components available for assembly

## Configuration

Key configuration properties in `application.properties`:
- `server.port=8089` - HTTP server port
- `spring.kafka.bootstrap-servers=localhost:9092` - Kafka broker address
- `spring.datasource.url=jdbc:h2:mem:assemblydb` - H2 in-memory database

## Health Check

```bash
curl http://localhost:8089/actuator/health
```

## H2 Console

Access the H2 database console at: http://localhost:8089/h2-console

- JDBC URL: `jdbc:h2:mem:assemblydb`
- Username: `sa`
- Password: (empty)

## Architecture

The service follows a standard Spring Boot layered architecture:
- **Controllers**: REST API endpoints
- **Services**: Business logic
- **DTOs**: Request/response data transfer objects
- **Events**: Kafka producers and consumers
- **Config**: Kafka and application configuration
