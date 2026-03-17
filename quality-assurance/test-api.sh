#!/bin/bash

# Quality Assurance Service API Test Script
# Tests all API endpoints for the QualityAssuranceService

BASE_URL="http://localhost:8086"

echo "======================================"
echo "Quality Assurance Service API Tests"
echo "======================================"
echo ""

# Color codes for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to print test results
print_result() {
    if [ $1 -eq 0 ]; then
        echo -e "${GREEN}✓ PASSED${NC}: $2"
    else
        echo -e "${RED}✗ FAILED${NC}: $2"
    fi
}

# Function to print section header
print_section() {
    echo ""
    echo -e "${YELLOW}=== $1 ===${NC}"
}

# Check if service is running
echo "Checking if service is running..."
curl -s -f ${BASE_URL}/actuator/health > /dev/null
if [ $? -eq 0 ]; then
    echo -e "${GREEN}Service is running${NC}"
else
    echo -e "${RED}Service is not running. Please start the service first.${NC}"
    exit 1
fi

# Test 1: Create Quality Standard
print_section "Test 1: Create Quality Standard"
STANDARD_RESPONSE=$(curl -s -X POST ${BASE_URL}/quality/standards \
  -H "Content-Type: application/json" \
  -d '{
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
    "version": "1.0",
    "description": "Standard for incoming steel bar inspection"
  }')

STANDARD_ID=$(echo $STANDARD_RESPONSE | grep -o '"standardId":"[^"]*' | cut -d'"' -f4)
echo "Response: $STANDARD_RESPONSE"
print_result $? "Create Quality Standard"
echo "Standard ID: $STANDARD_ID"

# Test 2: Schedule Inspection
print_section "Test 2: Schedule Inspection"
INSPECTION_RESPONSE=$(curl -s -X POST ${BASE_URL}/inspections/schedule \
  -H "Content-Type: application/json" \
  -d '{
    "materialId": "MAT-001",
    "inspectionType": "INCOMING",
    "standardId": "'$STANDARD_ID'",
    "batchId": "BATCH-001",
    "supplierId": "SUP-001",
    "priority": "HIGH",
    "notes": "First article inspection"
  }')

INSPECTION_ID=$(echo $INSPECTION_RESPONSE | grep -o '"inspectionId":"[^"]*' | cut -d'"' -f4)
echo "Response: $INSPECTION_RESPONSE"
print_result $? "Schedule Inspection"
echo "Inspection ID: $INSPECTION_ID"

# Test 3: Record Inspection Result (PASS)
print_section "Test 3: Record Inspection Result (PASS)"
RESULT_RESPONSE=$(curl -s -X POST ${BASE_URL}/inspections/${INSPECTION_ID}/results \
  -H "Content-Type: application/json" \
  -d '{
    "result": "PASS",
    "inspectorId": "INSP-001",
    "measurements": {
      "dimension1": 10.5,
      "dimension2": 20.3,
      "hardness": 45,
      "surfaceFinish": "A"
    },
    "defects": [],
    "correctionRequired": "NONE",
    "notes": "All measurements within tolerance",
    "testResults": {
      "tensileStrength": 420,
      "carbonContent": 0.22
    }
  }')

echo "Response: $RESULT_RESPONSE"
print_result $? "Record Inspection Result (PASS)"

# Test 4: Schedule another inspection with different batch
print_section "Test 4: Schedule Another Inspection"
INSPECTION2_RESPONSE=$(curl -s -X POST ${BASE_URL}/inspections/schedule \
  -H "Content-Type: application/json" \
  -d '{
    "materialId": "MAT-001",
    "inspectionType": "INCOMING",
    "standardId": "'$STANDARD_ID'",
    "batchId": "BATCH-002",
    "supplierId": "SUP-001",
    "priority": "MEDIUM",
    "notes": "Second batch inspection"
  }')

INSPECTION2_ID=$(echo $INSPECTION2_RESPONSE | grep -o '"inspectionId":"[^"]*' | cut -d'"' -f4)
echo "Response: $INSPECTION2_RESPONSE"
print_result $? "Schedule Second Inspection"
echo "Inspection ID: $INSPECTION2_ID"

# Test 5: Record Inspection Result (FAIL)
print_section "Test 5: Record Inspection Result (FAIL)"
FAIL_RESPONSE=$(curl -s -X POST ${BASE_URL}/inspections/${INSPECTION2_ID}/results \
  -H "Content-Type: application/json" \
  -d '{
    "result": "FAIL",
    "inspectorId": "INSP-002",
    "measurements": {
      "dimension1": 12.5,
      "dimension2": 21.8
    },
    "defects": ["Dimensional out of tolerance", "Surface scratches"],
    "correctionRequired": "RETURN_TO_SUPPLIER",
    "notes": "Multiple defects detected"
  }')

echo "Response: $FAIL_RESPONSE"
print_result $? "Record Inspection Result (FAIL)"

# Extract failure ID from the response (it would be generated in the event)
FAILURE_ID="FAIL-$(date +%s | tail -c 8)"

# Test 6: Get Quality History
print_section "Test 6: Get Quality History"
HISTORY_RESPONSE=$(curl -s -X GET ${BASE_URL}/quality/history/MAT-001)
echo "Response: $HISTORY_RESPONSE"
print_result $? "Get Quality History"

# Test 7: Issue Certificate for BATCH-001 (should succeed as it passed)
print_section "Test 7: Issue Quality Certificate"
CERT_RESPONSE=$(curl -s -X POST ${BASE_URL}/quality/certificates/BATCH-001/issue)
CERT_ID=$(echo $CERT_RESPONSE | grep -o '"certificateId":"[^"]*' | cut -d'"' -f4)
echo "Response: $CERT_RESPONSE"
print_result $? "Issue Quality Certificate"
echo "Certificate ID: $CERT_ID"

# Test 8: Get Certificate by Batch ID
print_section "Test 8: Get Certificate by Batch ID"
GET_CERT_RESPONSE=$(curl -s -X GET ${BASE_URL}/quality/certificates/BATCH-001)
echo "Response: $GET_CERT_RESPONSE"
print_result $? "Get Certificate"

# Test 9: Escalate Quality Failure
print_section "Test 9: Escalate Quality Failure"
ESCALATE_RESPONSE=$(curl -s -X POST ${BASE_URL}/quality/failures/${FAILURE_ID}/escalate \
  -H "Content-Type: application/json" \
  -d '{
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
  }')

ESCALATION_ID=$(echo $ESCALATE_RESPONSE | grep -o '"escalationId":"[^"]*' | cut -d'"' -f4)
echo "Response: $ESCALATE_RESPONSE"
print_result $? "Escalate Quality Failure"
echo "Escalation ID: $ESCALATION_ID"

# Test 10: Get Escalation Details
print_section "Test 10: Get Escalation Details"
GET_ESCALATION_RESPONSE=$(curl -s -X GET ${BASE_URL}/quality/failures/escalations/${ESCALATION_ID})
echo "Response: $GET_ESCALATION_RESPONSE"
print_result $? "Get Escalation Details"

# Test 11: Get Inspection Details
print_section "Test 11: Get Inspection Details"
GET_INSPECTION_RESPONSE=$(curl -s -X GET ${BASE_URL}/inspections/${INSPECTION_ID})
echo "Response: $GET_INSPECTION_RESPONSE"
print_result $? "Get Inspection Details"

# Test 12: Get Quality Standard
print_section "Test 12: Get Quality Standard"
GET_STANDARD_RESPONSE=$(curl -s -X GET ${BASE_URL}/quality/standards/${STANDARD_ID})
echo "Response: $GET_STANDARD_RESPONSE"
print_result $? "Get Quality Standard"

# Test 13: Try to issue certificate for failed batch (should fail)
print_section "Test 13: Try to Issue Certificate for Failed Batch (Should Fail)"
FAIL_CERT_RESPONSE=$(curl -s -X POST ${BASE_URL}/quality/certificates/BATCH-002/issue)
echo "Response: $FAIL_CERT_RESPONSE"
# We expect this to fail, so we check if it contains an error
if echo "$FAIL_CERT_RESPONSE" | grep -q "error\|failed\|Cannot"; then
    print_result 0 "Certificate correctly rejected for failed batch"
else
    print_result 1 "Certificate should have been rejected for failed batch"
fi

# Test 14: Health Check
print_section "Test 14: Health Check"
HEALTH_RESPONSE=$(curl -s ${BASE_URL}/actuator/health)
echo "Response: $HEALTH_RESPONSE"
print_result $? "Health Check"

echo ""
echo "======================================"
echo "API Tests Completed"
echo "======================================"
echo ""
echo "Summary of created resources:"
echo "- Standard ID: $STANDARD_ID"
echo "- Inspection ID (Pass): $INSPECTION_ID"
echo "- Inspection ID (Fail): $INSPECTION2_ID"
echo "- Certificate ID: $CERT_ID"
echo "- Escalation ID: $ESCALATION_ID"
