# Quality Assurance Service

A Spring Boot microservice that manages quality standards, inspection processes, test execution, and quality certifications throughout the manufacturing lifecycle.

## Overview

The Quality Assurance Service is responsible for:
- Scheduling and managing quality inspections
- Recording inspection results and measurements
- Tracking quality history and defect patterns
- Managing quality standards and procedures
- Issuing quality certificates for compliant batches
- Escalating quality failures and triggering corrective actions

## Features

### Inspection Management
- Schedule inspections for incoming materials, in-process components, and finished products
- Record detailed inspection results with measurements and defect tracking
- Support for various inspection types (INCOMING, IN_PROCESS, FINAL)
- Inspector assignment and tracking

### Quality Standards
- Define and maintain quality standards for different material types
- Specify inspection procedures and acceptance criteria
- Version control for standards
- Material-specific specifications and test methods

### Quality History & Analytics
- Track complete quality history for materials
- Analyze defect patterns and trends
- Calculate pass rates and quality metrics
- Identify common defects and quality issues

### Certification
- Generate quality certificates for compliant batches
- Track certificate validity and expiry
- Link certificates to inspection results
- Support compliance standards (ISO 9001:2015)

### Failure Management
- Detect and flag quality failures
- Multi-level escalation system (Supervisor, Manager, Executive)
- Automated corrective action recommendations
- Production hold capabilities for critical failures

## Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Messaging**: Apache Kafka (Spring Kafka)
- **Database**: H2 (development), JPA/Hibernate
- **Build Tool**: Maven
- **Validation**: Jakarta Validation

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Apache Kafka (running on localhost:9092)

### Building the Service
```bash
mvn clean install
```

### Running the Service
```bash
mvn spring-boot:run
```

The service will start on port **8086**.

### Running Tests
```bash
mvn test
```

## API Endpoints

### Inspection Management

#### Schedule Inspection
```http
POST /inspections/schedule
Content-Type: application/json

{
  "materialId": "MAT-001",
  "inspectionType": "INCOMING",
  "standardId": "STD-001",
  "batchId": "BATCH-001",
  "supplierId": "SUP-001",
  "scheduledDate": "2026-03-17T14:00:00",
  "priority": "HIGH",
  "notes": "First article inspection"
}
```

#### Record Inspection Results
```http
POST /inspections/{inspectionId}/results
Content-Type: application/json

{
  "result": "PASS",
  "inspectorId": "INSP-001",
  "measurements": {
    "dimension1": 10.5,
    "hardness": 45,
    "surfaceFinish": "A"
  },
  "defects": [],
  "correctionRequired": "NONE",
  "notes": "All measurements within tolerance"
}
```

### Quality History

#### Get Quality History
```http
GET /quality/history/{materialId}
```

### Quality Standards

#### Create Quality Standard
```http
POST /quality/standards
Content-Type: application/json

{
  "standardName": "Steel Bar Incoming Inspection",
  "materialType": "STEEL_BAR",
  "specifications": {
    "minTensileStrength": 400,
    "maxCarbonContent": 0.25,
    "surfaceQuality": "Grade A"
  },
  "procedures": [
    {
      "procedureName": "Dimensional Check",
      "description": "Verify dimensions using calibrated tools",
      "acceptanceCriteria": {
        "tolerance": "±0.1mm"
      },
      "requiredEquipment": ["Caliper", "Micrometer"],
      "testMethod": "ASTM E8"
    }
  ],
  "version": "1.0"
}
```

### Quality Certificates

#### Issue Certificate
```http
POST /quality/certificates/{batchId}/issue
```

#### Get Certificate
```http
GET /quality/certificates/{batchId}
```

### Failure Escalation

#### Escalate Quality Failure
```http
POST /quality/failures/{failureId}/escalate
Content-Type: application/json

{
  "escalationLevel": "QUALITY_MANAGER",
  "reason": "Repeated defects in supplier batch",
  "impact": "HIGH",
  "suggestedActions": [
    "Quarantine all affected materials",
    "Contact supplier for investigation",
    "Increase inspection frequency"
  ],
  "rootCauseAnalysis": "Supplier process drift detected",
  "productionHold": false
}
```

## Event Integration

### Produced Events

1. **quality.inspection.completed**
   - Triggered when an inspection is completed
   - Contains pass/fail results and measurements

2. **quality.failure.detected**
   - Triggered when quality failure is detected
   - Requires corrective action

3. **quality.certificate.issued**
   - Triggered when quality certificate is issued
   - Enables material use or shipping

4. **quality.trend.alert**
   - Triggered when statistical process control violation detected
   - Indicates quality trends requiring attention

### Consumed Events

1. **procurement.goods.received**
   - Automatically schedules incoming inspection for received materials

2. **warehouse.goods.received**
   - Coordinates with physical receipt for inspection scheduling

## Configuration

Key configuration properties in `application.properties`:

```properties
# Server Configuration
server.port=8086

# Kafka Configuration
spring.kafka.bootstrap-servers=localhost:9092

# Database Configuration
spring.datasource.url=jdbc:h2:mem:qualitydb
```

## Architecture Notes

- **Microservice Architecture**: Independent service with its own database
- **Event-Driven**: Integrates with other services via Kafka events
- **RESTful APIs**: Synchronous communication for queries
- **Domain Isolation**: No direct database access from other services

## Quality Metrics

The service tracks:
- Total inspections performed
- Pass/fail rates
- Defect patterns and frequencies
- Inspection turnaround time
- Certificate issuance rate
- Escalation rates by severity

## Future Enhancements

- Statistical Process Control (SPC) charts
- Machine learning for defect prediction
- Integration with laboratory information systems
- Mobile app for inspectors
- Automated measurement device integration
- Advanced analytics and dashboards

## Support

For issues or questions, contact the Quality Assurance team.
