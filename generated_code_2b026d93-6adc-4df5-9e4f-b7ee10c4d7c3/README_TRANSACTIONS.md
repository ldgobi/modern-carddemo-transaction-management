# Card Account Transaction Management System

## Overview

This is a comprehensive frontend application for managing card account transactions, built with Next.js 14, TypeScript, and Tailwind CSS. The system provides full CRUD operations for transactions, account management, and report generation capabilities.

## Features

### 1. Transaction Management
- **View Transactions**: Paginated list of all transactions with search and filter capabilities
- **Transaction Details**: Detailed view of individual transactions including merchant information
- **Add Transaction**: Create new transactions with comprehensive validation
- **Search & Filter**: Search by transaction ID or filter by date range

### 2. Account Management
- **View Accounts**: List all accounts with status, balance, and credit information
- **Account Details**: Detailed view of account information including:
  - Current balance and credit limit
  - Available credit and utilization percentage
  - Current cycle activity (credits and debits)
  - Cash credit limit

### 3. Report Generation
- **Monthly Reports**: Generate reports for the current month
- **Yearly Reports**: Generate reports for the current year
- **Custom Reports**: Generate reports for custom date ranges
- **Report Display**: View transaction details with totals and summaries

## Project Structure

```
src/
├── app/
│   ├── accounts/
│   │   ├── [id]/
│   │   │   └── page.tsx          # Account detail page
│   │   └── page.tsx               # Accounts list page
│   ├── reports/
│   │   └── page.tsx               # Reports generation page
│   ├── transactions/
│   │   ├── [id]/
│   │   │   └── page.tsx          # Transaction detail page
│   │   ├── add/
│   │   │   └── page.tsx          # Add transaction page
│   │   └── page.tsx               # Transactions list page
│   ├── layout.tsx                 # Root layout
│   └── page.tsx                   # Home page
├── components/
│   └── ui/                        # Reusable UI components
│       ├── Button.tsx
│       ├── Input.tsx
│       ├── Modal.tsx
│       ├── Select.tsx
│       └── Table.tsx
├── services/
│   ├── accountService.ts          # Account API service
│   ├── reportService.ts           # Report API service
│   └── transactionService.ts      # Transaction API service
└── types/
    ├── account.ts                 # Account type definitions
    ├── report.ts                  # Report type definitions
    └── transaction.ts             # Transaction type definitions
```

## API Integration

The application integrates with the following API endpoints:

### Transaction Endpoints
- `GET /api/transactions` - Get paginated transactions
- `GET /api/transactions/{id}` - Get transaction by ID
- `POST /api/transactions` - Create new transaction
- `GET /api/transactions/date-range` - Get transactions by date range
- `GET /api/transactions/card/{cardNumber}` - Get transactions by card number

### Account Endpoints
- `GET /api/accounts` - Get all accounts
- `GET /api/accounts/{id}` - Get account by ID
- `GET /api/accounts/customer/{customerId}` - Get accounts by customer ID

### Report Endpoints
- `POST /api/reports/transactions` - Generate transaction report

## Business Rules Implementation

### Transaction Creation (COTRN02C)
- Validates all input fields for completeness and correctness
- Validates card number and account existence
- Checks credit limit before creating transaction
- Validates transaction date against account expiration
- Requires user confirmation before creation
- Generates unique transaction ID automatically

### Transaction Viewing (COTRN01C)
- Displays detailed transaction information
- Shows masked card numbers for security
- Formats amounts and dates appropriately
- Handles transaction not found errors

### Transaction List (COTRN00C)
- Displays paginated list of transactions (10 per page)
- Supports forward and backward navigation
- Allows transaction selection for detailed view
- Implements search by transaction ID
- Supports date range filtering

### Report Generation (CORPT00C)
- Supports three report types: Monthly, Yearly, Custom
- Validates date ranges for custom reports
- Requires user confirmation before generation
- Displays transaction count and total amounts
- Shows detailed transaction list in report

### Date Validation (CSUTLDTC)
- Validates date format (YYYY-MM-DD)
- Checks for valid calendar dates
- Ensures date ranges are logical (start <= end)

## Data Validation

### Transaction Fields
- **Card Number**: Exactly 16 digits
- **Type Code**: Exactly 2 characters
- **Category Code**: 4-digit number (1000-9999)
- **Source**: Max 10 characters
- **Description**: Max 100 characters
- **Amount**: Numeric with 2 decimal places
- **Merchant ID**: Numeric
- **Merchant Name**: Max 50 characters
- **Merchant City**: Max 50 characters
- **Merchant ZIP**: Max 10 characters
- **Dates**: YYYY-MM-DD format

### Account Fields
- **Account ID**: 11-digit number
- **Customer ID**: 10-digit number
- **Active Status**: Y or N
- **Balances**: Numeric with 2 decimal places
- **Dates**: YYYY-MM-DD format

## UI/UX Features

### User Experience
- Loading states for all async operations
- Error messages with clear descriptions
- Success confirmations for actions
- Empty states when no data is available
- Responsive design for mobile and desktop

### Navigation
- Breadcrumb navigation
- Back buttons on detail pages
- Home page with feature cards
- Consistent header and layout

### Data Display
- Formatted currency values
- Masked card numbers for security
- Color-coded status badges
- Sortable and filterable tables
- Pagination controls

## Error Handling

The application implements comprehensive error handling:

1. **Network Errors**: Displays user-friendly messages when API calls fail
2. **Validation Errors**: Shows specific field-level validation messages
3. **Not Found Errors**: Handles missing resources gracefully
4. **Business Rule Violations**: Displays appropriate error messages

## Security Considerations

1. **Card Number Masking**: Card numbers are masked in list views (showing only last 4 digits)
2. **Authentication**: Uses JWT tokens stored in localStorage
3. **Authorization Headers**: Includes auth tokens in all API requests
4. **Input Validation**: Client-side validation before API calls

## Getting Started

### Prerequisites
- Node.js 18+ 
- npm or yarn

### Installation
```bash
npm install
```

### Development
```bash
npm run dev
```

### Build
```bash
npm run build
```

### Production
```bash
npm start
```

## Environment Variables

Create a `.env.local` file with:
```
NEXT_PUBLIC_API_URL=http://localhost:8080
```

## Testing

The application should be tested with:
1. Valid transaction creation
2. Invalid input handling
3. Pagination navigation
4. Search and filter functionality
5. Report generation for all types
6. Account viewing and navigation

## Future Enhancements

Potential improvements:
1. Export reports to PDF/Excel
2. Advanced filtering options
3. Transaction editing capability
4. Bulk transaction operations
5. Real-time transaction updates
6. Transaction analytics dashboard
7. Multi-language support
8. Dark mode theme

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## License

This project is part of the CardDemo application modernization effort.

## Support

For issues or questions, please contact the development team.
