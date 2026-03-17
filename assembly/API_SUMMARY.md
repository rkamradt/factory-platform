# AssemblyService API Summary

## Service Information
- **Service ID**: assembly
- **Service Name**: AssemblyService
- **Port**: 8089
- **Base URL**: http://localhost:8089

## REST API Endpoints

### Assembly Order Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/assembly-orders/{orderId}/accept` | Accept assembly order from production planning |
| POST | `/assembly-orders/{orderId}/schedule` | Schedule work on assembly lines or workstations |
| PUT | `/assembly-orders/{orderId}/progress` | Update assembly progress through build stages |
| POST | `/assembly-orders/{orderId}/consumption` | Track component consumption and assembly yield |

### Assembly Line Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/lines/{lineId}/status` | Report line status, downtime, or bottlenecks |

### Operator Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/operators/{operatorId}/assign` | Assign operators to specialized assembly tasks |

## Kafka Event Topics

### Produced Events (Published by AssemblyService)
| Topic | Description |
|-------|-------------|
| `assembly.order.completed` | Finished product ready for shipping or final inspection |
| `assembly.line.breakdown` | Assembly line downtime affecting production capacity |
| `assembly.quality.issue.detected` | Assembly problem detected requiring possible rework |
| `assembly.component.consumed` | Component consumed in assembly process with yield tracking |

### Consumed Events (Subscribed by AssemblyService)
| Topic | Description |
|-------|-------------|
| `production.work.order.scheduled` | Work order assigned to assembly line for execution |
| `machining.work.order.completed` | Machined components available for assembly |
| `fabrication.work.order.completed` | Fabricated components available for assembly |
| `quality.inspection.completed` | Quality results may require rework or process adjustments |
| `inventory.material.reserved` | Confirms all components available for assembly |

## Service Dependencies
- production-planning
- machining-workshop
- fabrication-workshop
- quality-assurance
- inventory

## Example API Calls

### Accept Assembly Order
```bash
curl -X POST http://localhost:8089/assembly-orders/ORD-001/accept \
  -H "Content-Type: application/json" \
  -d '{
    "orderId": "ORD-001",
    "productId": "PROD-123",
    "quantity": 10,
    "priority": "HIGH"
  }'
```

### Schedule Assembly
```bash
curl -X POST http://localhost:8089/assembly-orders/ORD-001/schedule \
  -H "Content-Type: application/json" \
  -d '{
    "lineId": "LINE-A1",
    "workstationId": "WS-101"
  }'
```

### Update Progress
```bash
curl -X PUT http://localhost:8089/assembly-orders/ORD-001/progress \
  -H "Content-Type: application/json" \
  -d '{
    "buildStage": "FINAL_ASSEMBLY",
    "completedUnits": 5,
    "progressPercentage": 50.0
  }'
```

### Report Line Status
```bash
curl -X POST http://localhost:8089/lines/LINE-A1/status \
  -H "Content-Type: application/json" \
  -d '{
    "status": "OPERATIONAL",
    "reportedBy": "supervisor-01"
  }'
```

### Track Component Consumption
```bash
curl -X POST http://localhost:8089/assembly-orders/ORD-001/consumption \
  -H "Content-Type: application/json" \
  -d '{
    "components": [
      {
        "componentId": "COMP-123",
        "quantityConsumed": 10
      }
    ],
    "unitsProduced": 5,
    "yieldPercentage": 98.5
  }'
```

### Assign Operator
```bash
curl -X POST http://localhost:8089/operators/OP-001/assign \
  -H "Content-Type: application/json" \
  -d '{
    "workstationId": "WS-101",
    "taskType": "WELDING",
    "orderId": "ORD-001"
  }'
```
