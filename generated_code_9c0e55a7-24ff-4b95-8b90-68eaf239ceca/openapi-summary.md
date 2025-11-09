# Card Account Transaction Management API

## Overview
This API provides endpoints for managing card account transactions, including transaction creation, retrieval, listing, and reporting capabilities. The system implements business rules from a legacy COBOL card transaction processing system.

## Base URL
```
http://localhost:8080/api
```

## API Endpoints

### Transaction Management

#### 1. Get Transaction by ID
Retrieves detailed information about a specific transaction.

**Endpoint:** `GET /api/transactions/{transactionId}`

**Path Parameters:**
- `transactionId` (string, required): The unique identifier of the transaction (16 characters)

**Response:**
- `200 OK`: Transaction found
- `404 NOT FOUND`: Transaction not found

**Response Body:**
```json
{
  "success": true,
  "data": {
    "transactionId": "string",
    "cardNumber": "string",
    "accountId": 0,
    "typeCode": "string",
    "categoryCode": 0,
    "source": "string",
    "description": "string",
    "amount": 0.00,
    "merchantId": 0,
    "merchantName": "string",
    "merchantCity": "string",
    "merchantZip": "string",
    "originalTimestamp": "2024-01-01T00:00:00",
    "processedTimestamp": "2024-01-01T00:00:00"
  }
}
```

---

#### 2. List Transactions
Retrieves a paginated list of transactions.

**Endpoint:** `GET /api/transactions`

**Query Parameters:**
- `page` (integer, optional, default: 0): Page number
- `size` (integer, optional, default: 10): Number of items per page

**Response:**
- `200 OK`: Transactions retrieved successfully

**Response Body:**
```json
{
  "success": true,
  "data": [
    {
      "transactionId": "string",
      "transactionDate": "2024-01-01T00:00:00",
      "description": "string",
      "amount": 0.00
    }
  ],
  "currentPage": 0,
  "totalPages": 0,
  "totalItems": 0
}
```

---

#### 3. Create Transaction
Creates a new transaction after validating card, account, and transaction details.

**Endpoint:** `POST /api/transactions`

**Request Body:**
```json
{
  "cardNumber": "string (16 digits)",
  "typeCode": "string (2 chars)",
  "categoryCode": 0,
  "source": "string (max 10 chars)",
  "description": "string (max 100 chars)",
  "amount": 0.00,
  "merchantId": 0,
  "merchantName": "string (max 50 chars)",
  "merchantCity": "string (max 50 chars)",
  "merchantZip": "string (max 10 chars)",
  "originalTimestamp": "2024-01-01T00:00:00",
  "confirmation": "Y"
}
```

**Validations:**
- Card number must exist and be valid
- Account must exist and be active
- Transaction date must be before account expiration
- Transaction must not exceed credit limit
- All required fields must be provided
- Confirmation must be 'Y' or 'y'

**Response:**
- `201 CREATED`: Transaction created successfully
- `400 BAD REQUEST`: Validation error or business rule violation

**Response Body:**
```json
{
  "success": true,
  "message": "Transaction created successfully with ID: {transactionId}",
  "data": {
    "transactionId": "string",
    "cardNumber": "string",
    "accountId": 0,
    "typeCode": "string",
    "categoryCode": 0,
    "source": "string",
    "description": "string",
    "amount": 0.00,
    "merchantId": 0,
    "merchantName": "string",
    "merchantCity": "string",
    "merchantZip": "string",
    "originalTimestamp": "2024-01-01T00:00:00",
    "processedTimestamp": "2024-01-01T00:00:00"
  }
}
```

---

#### 4. Get Transactions by Date Range
Retrieves transactions within a specified date range.

**Endpoint:** `GET /api/transactions/date-range`

**Query Parameters:**
- `startDate` (date, required): Start date in format YYYY-MM-DD
- `endDate` (date, required): End date in format YYYY-MM-DD

**Response:**
- `200 OK`: Transactions retrieved successfully

**Response Body:**
```json
{
  "success": true,
  "data": [
    {
      "transactionId": "string",
      "cardNumber": "string",
      "accountId": 0,
      "typeCode": "string",
      "categoryCode": 0,
      "source": "string",
      "description": "string",
      "amount": 0.00,
      "merchantId": 0,
      "merchantName": "string",
      "merchantCity": "string",
      "merchantZip": "string",
      "originalTimestamp": "2024-01-01T00:00:00",
      "processedTimestamp": "2024-01-01T00:00:00"
    }
  ],
  "count": 0
}
```

---

#### 5. Get Transactions by Card Number
Retrieves all transactions for a specific card.

**Endpoint:** `GET /api/transactions/card/{cardNumber}`

**Path Parameters:**
- `cardNumber` (string, required): The card number (16 digits)

**Response:**
- `200 OK`: Transactions retrieved successfully

**Response Body:**
```json
{
  "success": true,
  "data": [
    {
      "transactionId": "string",
      "cardNumber": "string",
      "accountId": 0,
      "typeCode": "string",
      "categoryCode": 0,
      "source": "string",
      "description": "string",
      "amount": 0.00,
      "merchantId": 0,
      "merchantName": "string",
      "merchantCity": "string",
      "merchantZip": "string",
      "originalTimestamp": "2024-01-01T00:00:00",
      "processedTimestamp": "2024-01-01T00:00:00"
    }
  ],
  "count": 0
}
```

---

### Account Management

#### 6. Get Account by ID
Retrieves detailed information about a specific account.

**Endpoint:** `GET /api/accounts/{accountId}`

**Path Parameters:**
- `accountId` (long, required): The unique identifier of the account

**Response:**
- `200 OK`: Account found
- `404 NOT FOUND`: Account not found

**Response Body:**
```json
{
  "success": true,
  "data": {
    "accountId": 0,
    "customerId": 0,
    "activeStatus": "Y",
    "currentBalance": 0.00,
    "creditLimit": 0.00,
    "cashCreditLimit": 0.00,
    "openDate": "2024-01-01",
    "expirationDate": "2024-12-31",
    "currentCycleCredit": 0.00,
    "currentCycleDebit": 0.00
  }
}
```

---

#### 7. Get All Accounts
Retrieves a list of all accounts.

**Endpoint:** `GET /api/accounts`

**Response:**
- `200 OK`: Accounts retrieved successfully

**Response Body:**
```json
{
  "success": true,
  "data": [
    {
      "accountId": 0,
      "customerId": 0,
      "activeStatus": "Y",
      "currentBalance": 0.00,
      "creditLimit": 0.00,
      "cashCreditLimit": 0.00,
      "openDate": "2024-01-01",
      "expirationDate": "2024-12-31",
      "currentCycleCredit": 0.00,
      "currentCycleDebit": 0.00
    }
  ],
  "count": 0
}
```

---

#### 8. Get Accounts by Customer ID
Retrieves all accounts for a specific customer.

**Endpoint:** `GET /api/accounts/customer/{customerId}`

**Path Parameters:**
- `customerId` (long, required): The unique identifier of the customer

**Response:**
- `200 OK`: Accounts retrieved successfully

**Response Body:**
```json
{
  "success": true,
  "data": [
    {
      "accountId": 0,
      "customerId": 0,
      "activeStatus": "Y",
      "currentBalance": 0.00,
      "creditLimit": 0.00,
      "cashCreditLimit": 0.00,
      "openDate": "2024-01-01",
      "expirationDate": "2024-12-31",
      "currentCycleCredit": 0.00,
      "currentCycleDebit": 0.00
    }
  ],
  "count": 0
}
```

---

### Report Management

#### 9. Generate Transaction Report
Generates a transaction report based on the specified type (MONTHLY, YEARLY, or CUSTOM).

**Endpoint:** `POST /api/reports/transactions`

**Request Body:**
```json
{
  "reportType": "MONTHLY|YEARLY|CUSTOM",
  "startDate": "2024-01-01",
  "endDate": "2024-12-31",
  "confirmation": "Y"
}
```

**Report Types:**
- `MONTHLY`: Generates report for current month (startDate and endDate not required)
- `YEARLY`: Generates report for current year (startDate and endDate not required)
- `CUSTOM`: Generates report for specified date range (startDate and endDate required)

**Validations:**
- Confirmation must be 'Y' or 'y'
- For CUSTOM reports, both startDate and endDate are required
- Dates must be in valid format (YYYY-MM-DD)
- Start date must be before or equal to end date

**Response:**
- `200 OK`: Report generated successfully
- `400 BAD REQUEST`: Validation error or invalid report type

**Response Body:**
```json
{
  "success": true,
  "message": "MONTHLY report generated successfully",
  "reportType": "MONTHLY",
  "transactionCount": 0,
  "startDate": "2024-01-01",
  "endDate": "2024-01-31",
  "data": [
    {
      "transactionId": "string",
      "cardNumber": "string",
      "accountId": 0,
      "typeCode": "string",
      "categoryCode": 0,
      "source": "string",
      "description": "string",
      "amount": 0.00,
      "merchantId": 0,
      "merchantName": "string",
      "merchantCity": "string",
      "merchantZip": "string",
      "originalTimestamp": "2024-01-01T00:00:00",
      "processedTimestamp": "2024-01-01T00:00:00"
    }
  ]
}
```

---

## Business Rules

### Transaction Creation Rules
1. **Card Validation**: Card number must exist in the system
2. **Account Validation**: Associated account must exist and be active
3. **Date Validation**: Transaction date must be in valid format (YYYY-MM-DD) and before account expiration
4. **Credit Limit Check**: Transaction must not cause account to exceed credit limit
5. **Confirmation Required**: User must confirm transaction creation with 'Y'
6. **Automatic ID Generation**: System generates unique transaction ID automatically
7. **Balance Updates**: Account balances are automatically updated upon transaction creation
8. **Category Balance Tracking**: Transaction category balances are maintained per account

### Date Validation Rules
1. Dates must be in format YYYY-MM-DD
2. Month must be between 1 and 12
3. Day must be between 1 and 31
4. Year must be numeric
5. Date must be a valid calendar date

### Report Generation Rules
1. **Monthly Report**: Covers current month from 1st to last day
2. **Yearly Report**: Covers current year from January 1st to December 31st
3. **Custom Report**: Requires valid start and end dates
4. **Confirmation Required**: User must confirm report generation with 'Y'

---

## Error Responses

All endpoints return consistent error responses:

```json
{
  "success": false,
  "error": "Error message describing what went wrong"
}
```

Common error messages:
- "Transaction ID NOT found..."
- "Card Number NOT found..."
- "Account ID NOT found..."
- "Confirm to add this transaction..."
- "Invalid date format..."
- "Overlimit transaction..."
- "Transaction received after account expiration..."
- "Invalid report type. Valid values are MONTHLY, YEARLY, or CUSTOM"

---

## Data Models

### Transaction
- `transactionId`: String (16 chars) - Unique identifier
- `cardNumber`: String (16 digits) - Card used for transaction
- `accountId`: Long - Associated account
- `typeCode`: String (2 chars) - Transaction type
- `categoryCode`: Integer (4 digits) - Transaction category
- `source`: String (10 chars) - Transaction source
- `description`: String (100 chars) - Transaction description
- `amount`: BigDecimal - Transaction amount (signed, 2 decimals)
- `merchantId`: Long - Merchant identifier
- `merchantName`: String (50 chars) - Merchant name
- `merchantCity`: String (50 chars) - Merchant city
- `merchantZip`: String (10 chars) - Merchant ZIP code
- `originalTimestamp`: DateTime - When transaction occurred
- `processedTimestamp`: DateTime - When transaction was processed

### Account
- `accountId`: Long - Unique identifier
- `customerId`: Long - Associated customer
- `activeStatus`: String (1 char) - Y or N
- `currentBalance`: BigDecimal - Current account balance
- `creditLimit`: BigDecimal - Credit limit
- `cashCreditLimit`: BigDecimal - Cash credit limit
- `openDate`: Date - Account open date
- `expirationDate`: Date - Account expiration date
- `currentCycleCredit`: BigDecimal - Current cycle credits
- `currentCycleDebit`: BigDecimal - Current cycle debits

---

## Notes

1. All monetary amounts are represented as BigDecimal with 2 decimal places
2. All timestamps are in ISO 8601 format
3. All dates are in YYYY-MM-DD format
4. Card numbers must be exactly 16 digits
5. Transaction IDs are 16 characters
6. The API implements SOLID principles and follows RESTful conventions
7. All operations are transactional to ensure data consistency
8. Comprehensive validation is performed on all inputs
9. The system maintains referential integrity between entities
10. Audit fields (createdAt, updatedAt) are automatically managed
