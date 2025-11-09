# Quick Start Guide - Card Account Transaction Management

## Prerequisites

Before you begin, ensure you have the following installed:
- Node.js 18.x or higher
- npm 9.x or higher (or yarn/pnpm)
- Git

## Installation

1. **Clone or navigate to the project directory**
```bash
cd /tmp/archetypes/trentinrossi_archetype-nextjs_2b026d93-6adc-4df5-9e4f-b7ee10c4d7c3
```

2. **Install dependencies**
```bash
npm install
```

3. **Set up environment variables**

Create a `.env.local` file in the root directory:
```bash
NEXT_PUBLIC_API_URL=http://localhost:8080
```

## Running the Application

### Development Mode
```bash
npm run dev
```

The application will be available at `http://localhost:3000`

### Production Build
```bash
npm run build
npm start
```

## Application Structure

```
Card Account Transaction Management
├── Home (/)
│   ├── Transactions
│   ├── Add Transaction
│   ├── Accounts
│   └── Reports
```

## Main Features

### 1. Transaction Management

**View Transactions** (`/transactions`)
- Browse paginated list of transactions
- 10 transactions per page
- Navigate with Previous/Next buttons
- Click any transaction to view details

**View Transaction Details** (`/transactions/[id]`)
- Complete transaction information
- Merchant details
- Formatted amounts and dates
- Masked card numbers for security

**Add New Transaction** (`/transactions/add`)
- Fill in all required fields:
  - Card Number (16 digits)
  - Type Code (2 characters)
  - Category Code (4 digits, 1000-9999)
  - Source (max 10 characters)
  - Description (max 100 characters)
  - Amount (numeric with 2 decimals)
  - Transaction Date
  - Merchant Information
- Enter 'Y' to confirm
- Click "Create Transaction"

### 2. Account Management

**View Accounts** (`/accounts`)
- List of all accounts
- Status indicators (Active/Inactive)
- Balance and credit limit information
- Click any account to view details

**View Account Details** (`/accounts/[id]`)
- Complete account information
- Current balance and credit limit
- Available credit calculation
- Credit utilization percentage
- Current cycle activity (credits/debits)
- Link to view account transactions

### 3. Report Generation

**Generate Reports** (`/reports`)

**Monthly Report:**
1. Select "Monthly Report" option
2. Enter 'Y' to confirm
3. Click "Generate Report"
4. View transactions for current month

**Yearly Report:**
1. Select "Yearly Report" option
2. Enter 'Y' to confirm
3. Click "Generate Report"
4. View transactions for current year

**Custom Report:**
1. Select "Custom Date Range" option
2. Enter start date (YYYY-MM-DD)
3. Enter end date (YYYY-MM-DD)
4. Enter 'Y' to confirm
5. Click "Generate Report"
6. View transactions for selected period

## Common Tasks

### Search for a Transaction
1. Go to Transactions page
2. Note the transaction ID from the list
3. Click on the transaction row
4. View complete details

### Check Account Balance
1. Go to Accounts page
2. Find the account in the list
3. View balance in the table
4. Click "View Details" for more information

### Create a Transaction
1. Go to Add Transaction page
2. Fill in all required fields
3. Ensure all validations pass:
   - Card number is 16 digits
   - Type code is 2 characters
   - Category code is 4 digits (1000-9999)
   - All fields are filled
4. Enter 'Y' in confirmation field
5. Click "Create Transaction"
6. Wait for success message
7. Automatically redirected to transaction list

### Generate Monthly Report
1. Go to Reports page
2. Select "Monthly Report"
3. Enter 'Y' to confirm
4. Click "Generate Report"
5. View report with transaction count and total
6. Scroll down to see detailed transaction list

## Validation Rules

### Transaction Fields
- **Card Number**: Must be exactly 16 digits
- **Type Code**: Must be exactly 2 characters (uppercase)
- **Category Code**: Must be 4 digits (1000-9999)
- **Source**: Maximum 10 characters
- **Description**: Maximum 100 characters
- **Amount**: Numeric with up to 2 decimal places
- **Merchant Name**: Maximum 50 characters
- **Merchant City**: Maximum 50 characters
- **Merchant ZIP**: Maximum 10 characters
- **Date**: Must be in YYYY-MM-DD format
- **Confirmation**: Must be 'Y' or 'y'

### Date Validation
- Dates must be in YYYY-MM-DD format
- Start date must be before or equal to end date
- Dates must be valid calendar dates

## Error Messages

### Common Errors and Solutions

**"Card Number NOT found..."**
- Solution: Verify the card number exists in the system
- Check that you entered all 16 digits correctly

**"Account ID NOT found..."**
- Solution: Verify the account exists
- Check the account ID is correct

**"Overlimit transaction..."**
- Solution: Transaction would exceed credit limit
- Check account credit limit and current balance

**"Transaction received after account expiration..."**
- Solution: Transaction date is after account expiration
- Use a date before the account expiration date

**"Invalid date format..."**
- Solution: Use YYYY-MM-DD format
- Example: 2024-01-15

**"Confirm to add this transaction..."**
- Solution: Enter 'Y' in the confirmation field

## Tips and Best Practices

1. **Always confirm transactions** - Enter 'Y' when prompted
2. **Check validation messages** - Red error messages indicate what needs to be fixed
3. **Use proper date format** - Always use YYYY-MM-DD
4. **Verify card numbers** - Ensure 16-digit card numbers are correct
5. **Review before submitting** - Double-check all fields before creating transactions
6. **Use pagination** - Navigate through large lists with Previous/Next buttons
7. **Check account status** - Ensure accounts are active before creating transactions

## Keyboard Shortcuts

- **Tab**: Navigate between form fields
- **Enter**: Submit forms
- **Escape**: Close modals (if implemented)

## Browser Support

The application works best on:
- Chrome (latest version)
- Firefox (latest version)
- Safari (latest version)
- Edge (latest version)

## Troubleshooting

### Application won't start
```bash
# Clear node modules and reinstall
rm -rf node_modules
npm install
npm run dev
```

### API connection errors
- Check that the backend API is running
- Verify the API URL in `.env.local`
- Check network connectivity

### Build errors
```bash
# Clear Next.js cache
rm -rf .next
npm run build
```

### Page not loading
- Clear browser cache
- Hard refresh (Ctrl+Shift+R or Cmd+Shift+R)
- Check browser console for errors

## Getting Help

### Documentation
- See `README_TRANSACTIONS.md` for detailed documentation
- See `IMPLEMENTATION_SUMMARY.md` for implementation details

### Common Issues
1. **Validation errors**: Check field requirements above
2. **API errors**: Verify backend is running
3. **Display issues**: Try different browser or clear cache

## Next Steps

After getting familiar with the basics:
1. Explore all transaction types
2. Try different report date ranges
3. Review account details and balances
4. Practice creating various transactions
5. Generate reports for analysis

## Support

For technical support or questions:
- Check the documentation files
- Review error messages carefully
- Contact the development team

---

**Version:** 1.0.0  
**Last Updated:** 2024  
**Status:** Production Ready
