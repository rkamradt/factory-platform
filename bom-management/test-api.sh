#!/bin/bash

# BOM Management Service API Test Script
# Make sure the service is running before executing this script

BASE_URL="http://localhost:8080"

echo "=== BOM Management Service API Tests ==="
echo ""

# Test 1: Create a new BOM
echo "1. Creating a new BOM..."
curl -X POST "${BASE_URL}/boms" \
  -H "Content-Type: application/json" \
  -d '{
    "productId": "PROD-001",
    "productName": "Test Product",
    "version": "1.0",
    "description": "Test BOM for demonstration",
    "components": [
      {
        "componentId": "COMP-001",
        "componentName": "Steel Plate",
        "quantity": 2.0,
        "unit": "pieces",
        "specifications": "Grade A steel",
        "subComponents": []
      },
      {
        "componentId": "COMP-002",
        "componentName": "Bolt M8",
        "quantity": 8.0,
        "unit": "pieces",
        "specifications": "Stainless steel",
        "subComponents": []
      }
    ]
  }'
echo -e "\n"

# Test 2: Get specific BOM version
echo "2. Retrieving BOM version..."
curl -X GET "${BASE_URL}/boms/PROD-001/versions/1.0"
echo -e "\n"

# Test 3: Get latest BOM
echo "3. Retrieving latest BOM..."
curl -X GET "${BASE_URL}/boms/PROD-001/latest"
echo -e "\n"

# Test 4: Explode BOM
echo "4. Exploding BOM for production quantity..."
curl -X POST "${BASE_URL}/boms/test-bom-id/explode" \
  -H "Content-Type: application/json" \
  -d '{
    "productionQuantity": 100,
    "includeScrapFactor": true,
    "scrapPercentage": 5.0
  }'
echo -e "\n"

# Test 5: Validate BOM
echo "5. Validating BOM..."
curl -X POST "${BASE_URL}/boms/test-bom-id/validate"
echo -e "\n"

# Test 6: Get component specifications
echo "6. Retrieving component specifications..."
curl -X GET "${BASE_URL}/components/COMP-001/specifications"
echo -e "\n"

# Test 7: Health check
echo "7. Checking service health..."
curl -X GET "${BASE_URL}/actuator/health"
echo -e "\n"

echo ""
echo "=== Tests Complete ==="
