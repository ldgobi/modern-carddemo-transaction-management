# Test Plan - Card Account Transaction Management System

## Overview
This document outlines the comprehensive testing strategy for the Card Account Transaction Management frontend application.

## Test Environment Setup

### Prerequisites
- Backend API running on `http://localhost:8080`
- Frontend application running on `http://localhost:3000`
- Test data populated in the database
- Modern web browser (Chrome, Firefox, Safari, or Edge)

### Test Data Requirements
- At least 50 transactions for pagination testing
- Multiple accounts with different statuses
- Transactions spanning multiple months and years
- Valid and invalid card numbers
- Active and inactive accounts
- Accounts with various credit limits and balances

## Test Categories

### 1. Transaction List Page Tests

#### TC-TL-001: View Transaction List
**Objective:** Verify transaction list displays correctly
**Steps:**
1. Navigate to `/transactions`
2. Verify page loads without errors
3. Verify transactions are displayed in a table
4. Verify pagination controls are visible

**Expected Results:**
- Page loads successfully
- Transactions displayed with ID, date, description, amount
- Pagination shows current page and total pages
- Previous/Next buttons are visible

#### TC-TL-002: Pagination - Next Page
**Objective:** Verify next page navigation works
**Steps:**
1. Navigate to `/transactions`
2. Click "Next" button
3. Verify page number increments
4. Verify new transactions are loaded

**Expected Results:**
- Page number increases by 1
- Different transactions are displayed
- Previous button becomes enabled
- URL updates with page parameter

#### TC-TL-003: Pagination - Previous Page
**Objective:** Verify previous page navigation works
**Steps:**
1. Navigate to `/transactions?page=1`
2. Click "Previous" button
3. Verify page number decrements
4. Verify previous transactions are loaded

**Expected Results:**
- Page number decreases by 1
- Previous transactions are displayed
- Previous button disabled on page 0

#### TC-TL-004: View Transaction Details
**Objective:** Verify clicking transaction navigates to detail page
**Steps:**
1. Navigate to `/transactions`
2. Click on any transaction row
3. Verify navigation to detail page

**Expected Results:**
- Navigates to `/transactions/[id]`
- Transaction details are displayed
- Back button is available

#### TC-TL-005: Empty State
**Objective:** Verify empty state displays when no transactions
**Steps:**
1. Navigate to `/transactions` with no data
2. Verify empty state message displays

**Expected Results:**
- "No transactions found" message displays
- Helpful guidance is shown
- No error messages appear

### 2. Transaction Detail Page Tests

#### TC-TD-001: View Transaction Details
**Objective:** Verify transaction details display correctly
**Steps:**
1. Navigate to `/transactions/[valid-id]`
2. Verify all transaction fields are displayed

**Expected Results:**
- Transaction ID displayed
- Card number masked (last 4 digits visible)
- Amount formatted as currency
- Dates formatted correctly
- Merchant information displayed
- All fields populated

#### TC-TD-002: Transaction Not Found
**Objective:** Verify error handling for invalid transaction ID
**Steps:**
1. Navigate to `/transactions/INVALID123456`
2. Verify error message displays

**Expected Results:**
- Error message "Transaction not found" displays
- Back button is available
- No application crash

#### TC-TD-003: Back Navigation
**Objective:** Verify back button returns to list
**Steps:**
1. Navigate to transaction detail page
2. Click "Back to List" button
3. Verify return to transaction list

**Expected Results:**
- Returns to `/transactions`
- Previous page state is maintained

### 3. Add Transaction Page Tests

#### TC-AT-001: Display Add Transaction Form
**Objective:** Verify form displays with all fields
**Steps:**
1. Navigate to `/transactions/add`
2. Verify all form fields are present

**Expected Results:**
- All required fields visible
- Field labels are clear
- Validation hints are shown
- Submit button is present

#### TC-AT-002: Create Valid Transaction
**Objective:** Verify successful transaction creation
**Steps:**
1. Navigate to `/transactions/add`
2. Fill in all required fields with valid data:
   - Card Number: 1234567890123456
   - Type Code: PU
   - Category Code: 5000
   - Source: ONLINE
   - Description: Test transaction
   - Amount: 100.00
   - Merchant ID: 12345
   - Merchant Name: Test Merchant
   - Merchant City: Seattle
   - Merchant ZIP: 98101
   - Date: Current date
   - Confirmation: Y
3. Click "Create Transaction"

**Expected Results:**
- Success message displays
- Form is cleared
- Redirects to transaction list after 2 seconds
- New transaction appears in list

#### TC-AT-003: Validation - Empty Fields
**Objective:** Verify validation for empty required fields
**Steps:**
1. Navigate to `/transactions/add`
2. Leave all fields empty
3. Click "Create Transaction"

**Expected Results:**
- Error message displays
- Specific field errors are shown
- Form is not submitted
- No API call is made

#### TC-AT-004: Validation - Invalid Card Number
**Objective:** Verify card number validation
**Steps:**
1. Navigate to `/transactions/add`
2. Enter card number with less than 16 digits
3. Fill other fields with valid data
4. Click "Create Transaction"

**Expected Results:**
- Error message: "Card number must be exactly 16 digits"
- Form is not submitted

#### TC-AT-005: Validation - Invalid Type Code
**Objective:** Verify type code validation
**Steps:**
1. Navigate to `/transactions/add`
2. Enter type code with 1 or 3+ characters
3. Fill other fields with valid data
4. Click "Create Transaction"

**Expected Results:**
- Error message: "Type code must be exactly 2 characters"
- Form is not submitted

#### TC-AT-006: Validation - Invalid Category Code
**Objective:** Verify category code validation
**Steps:**
1. Navigate to `/transactions/add`
2. Enter category code outside 1000-9999 range
3. Fill other fields with valid data
4. Click "Create Transaction"

**Expected Results:**
- Error message: "Category code must be a 4-digit number"
- Form is not submitted

#### TC-AT-007: Validation - Invalid Amount
**Objective:** Verify amount validation
**Steps:**
1. Navigate to `/transactions/add`
2. Enter amount as 0 or negative
3. Fill other fields with valid data
4. Click "Create Transaction"

**Expected Results:**
- Error message: "Amount is required and must not be zero"
- Form is not submitted

#### TC-AT-008: Validation - Missing Confirmation
**Objective:** Verify confirmation requirement
**Steps:**
1. Navigate to `/transactions/add`
2. Fill all fields except confirmation
3. Click "Create Transaction"

**Expected Results:**
- Error message: "Please confirm the transaction by entering Y"
- Form is not submitted

#### TC-AT-009: Validation - Invalid Confirmation
**Objective:** Verify confirmation value validation
**Steps:**
1. Navigate to `/transactions/add`
2. Fill all fields
3. Enter 'N' in confirmation
4. Click "Create Transaction"

**Expected Results:**
- Error message: "Please confirm the transaction by entering Y"
- Form is not submitted

#### TC-AT-010: Business Rule - Card Not Found
**Objective:** Verify error handling for invalid card
**Steps:**
1. Navigate to `/transactions/add`
2. Fill all fields with valid format
3. Use non-existent card number
4. Enter 'Y' for confirmation
5. Click "Create Transaction"

**Expected Results:**
- Error message: "Card Number NOT found..."
- Form is not submitted
- User can correct and retry

#### TC-AT-011: Business Rule - Credit Limit Exceeded
**Objective:** Verify credit limit validation
**Steps:**
1. Navigate to `/transactions/add`
2. Fill all fields
3. Use amount that exceeds credit limit
4. Enter 'Y' for confirmation
5. Click "Create Transaction"

**Expected Results:**
- Error message: "Overlimit transaction..."
- Form is not submitted

#### TC-AT-012: Business Rule - Expired Account
**Objective:** Verify account expiration validation
**Steps:**
1. Navigate to `/transactions/add`
2. Fill all fields
3. Use date after account expiration
4. Enter 'Y' for confirmation
5. Click "Create Transaction"

**Expected Results:**
- Error message: "Transaction received after account expiration..."
- Form is not submitted

### 4. Account List Page Tests

#### TC-AL-001: View Account List
**Objective:** Verify account list displays correctly
**Steps:**
1. Navigate to `/accounts`
2. Verify accounts are displayed

**Expected Results:**
- All accounts displayed in table
- Account ID, Customer ID, Status visible
- Balance and credit limit shown
- Status badges colored correctly

#### TC-AL-002: View Account Details
**Objective:** Verify navigation to account details
**Steps:**
1. Navigate to `/accounts`
2. Click "View Details" on any account

**Expected Results:**
- Navigates to `/accounts/[id]`
- Account details are displayed

#### TC-AL-003: Status Badge Display
**Objective:** Verify status badges display correctly
**Steps:**
1. Navigate to `/accounts`
2. Verify active accounts show green badge
3. Verify inactive accounts show red badge

**Expected Results:**
- Active accounts: Green badge with "Active"
- Inactive accounts: Red badge with "Inactive"

### 5. Account Detail Page Tests

#### TC-AD-001: View Account Details
**Objective:** Verify account details display correctly
**Steps:**
1. Navigate to `/accounts/[valid-id]`
2. Verify all account information displays

**Expected Results:**
- Account ID displayed
- Customer ID shown
- Balance information complete
- Credit limit displayed
- Available credit calculated correctly
- Credit utilization percentage shown
- Cycle activity displayed

#### TC-AD-002: Calculate Available Credit
**Objective:** Verify available credit calculation
**Steps:**
1. Navigate to account detail page
2. Verify available credit = credit limit - current balance

**Expected Results:**
- Calculation is correct
- Displayed in green
- Formatted as currency

#### TC-AD-003: Calculate Credit Utilization
**Objective:** Verify credit utilization calculation
**Steps:**
1. Navigate to account detail page
2. Verify utilization = (balance / limit) * 100

**Expected Results:**
- Percentage is correct
- Displayed with 2 decimal places
- Shows % symbol

#### TC-AD-004: Account Not Found
**Objective:** Verify error handling for invalid account
**Steps:**
1. Navigate to `/accounts/99999999999`
2. Verify error message displays

**Expected Results:**
- Error message "Account not found"
- Back button available
- No application crash

### 6. Report Generation Page Tests

#### TC-RG-001: Display Report Form
**Objective:** Verify report form displays correctly
**Steps:**
1. Navigate to `/reports`
2. Verify all report options are visible

**Expected Results:**
- Three report type options visible
- Monthly, Yearly, Custom options
- Confirmation field present
- Generate button visible

#### TC-RG-002: Generate Monthly Report
**Objective:** Verify monthly report generation
**Steps:**
1. Navigate to `/reports`
2. Select "Monthly Report"
3. Enter 'Y' for confirmation
4. Click "Generate Report"

**Expected Results:**
- Success message displays
- Report shows current month transactions
- Transaction count displayed
- Total amount calculated
- Detailed transaction list shown

#### TC-RG-003: Generate Yearly Report
**Objective:** Verify yearly report generation
**Steps:**
1. Navigate to `/reports`
2. Select "Yearly Report"
3. Enter 'Y' for confirmation
4. Click "Generate Report"

**Expected Results:**
- Success message displays
- Report shows current year transactions
- Transaction count displayed
- Total amount calculated
- Detailed transaction list shown

#### TC-RG-004: Generate Custom Report
**Objective:** Verify custom date range report
**Steps:**
1. Navigate to `/reports`
2. Select "Custom Date Range"
3. Enter start date: 2024-01-01
4. Enter end date: 2024-01-31
5. Enter 'Y' for confirmation
6. Click "Generate Report"

**Expected Results:**
- Success message displays
- Report shows transactions in date range
- Transaction count displayed
- Total amount calculated
- Detailed transaction list shown

#### TC-RG-005: Validation - Missing Dates for Custom
**Objective:** Verify date requirement for custom reports
**Steps:**
1. Navigate to `/reports`
2. Select "Custom Date Range"
3. Leave dates empty
4. Enter 'Y' for confirmation
5. Click "Generate Report"

**Expected Results:**
- Error message: "Start date and end date are required"
- Report is not generated

#### TC-RG-006: Validation - Invalid Date Range
**Objective:** Verify date range validation
**Steps:**
1. Navigate to `/reports`
2. Select "Custom Date Range"
3. Enter start date: 2024-12-31
4. Enter end date: 2024-01-01
5. Enter 'Y' for confirmation
6. Click "Generate Report"

**Expected Results:**
- Error message: "Start date must be before or equal to end date"
- Report is not generated

#### TC-RG-007: Validation - Missing Confirmation
**Objective:** Verify confirmation requirement
**Steps:**
1. Navigate to `/reports`
2. Select any report type
3. Leave confirmation empty
4. Click "Generate Report"

**Expected Results:**
- Error message: "Please confirm the report generation by entering Y"
- Report is not generated

### 7. Navigation Tests

#### TC-NAV-001: Home Page Navigation
**Objective:** Verify home page links work
**Steps:**
1. Navigate to `/`
2. Click each feature card
3. Verify navigation to correct pages

**Expected Results:**
- Transactions card → `/transactions`
- Add Transaction card → `/transactions/add`
- Accounts card → `/accounts`
- Reports card → `/reports`

#### TC-NAV-002: Back Button Navigation
**Objective:** Verify back buttons work on all pages
**Steps:**
1. Navigate to detail pages
2. Click back buttons
3. Verify return to previous page

**Expected Results:**
- All back buttons work correctly
- Previous page state maintained
- No navigation errors

### 8. Responsive Design Tests

#### TC-RD-001: Mobile View (375px)
**Objective:** Verify mobile responsiveness
**Steps:**
1. Resize browser to 375px width
2. Navigate through all pages
3. Verify layout adapts correctly

**Expected Results:**
- All content visible
- No horizontal scrolling
- Buttons accessible
- Forms usable

#### TC-RD-002: Tablet View (768px)
**Objective:** Verify tablet responsiveness
**Steps:**
1. Resize browser to 768px width
2. Navigate through all pages
3. Verify layout adapts correctly

**Expected Results:**
- Optimal layout for tablet
- All features accessible
- Good use of space

#### TC-RD-003: Desktop View (1920px)
**Objective:** Verify desktop responsiveness
**Steps:**
1. Resize browser to 1920px width
2. Navigate through all pages
3. Verify layout uses full width appropriately

**Expected Results:**
- Content centered or full-width as appropriate
- No excessive whitespace
- Optimal reading experience

### 9. Error Handling Tests

#### TC-EH-001: Network Error
**Objective:** Verify handling of network errors
**Steps:**
1. Disconnect network
2. Try to load any page
3. Verify error message displays

**Expected Results:**
- User-friendly error message
- No application crash
- Retry option available

#### TC-EH-002: API Error (500)
**Objective:** Verify handling of server errors
**Steps:**
1. Trigger 500 error from API
2. Verify error message displays

**Expected Results:**
- Error message displays
- No sensitive information exposed
- User can navigate away

#### TC-EH-003: API Error (404)
**Objective:** Verify handling of not found errors
**Steps:**
1. Request non-existent resource
2. Verify appropriate error message

**Expected Results:**
- "Not found" message displays
- User can navigate back
- No application crash

### 10. Performance Tests

#### TC-PERF-001: Page Load Time
**Objective:** Verify pages load within acceptable time
**Steps:**
1. Clear cache
2. Load each page
3. Measure load time

**Expected Results:**
- All pages load within 3 seconds
- No blocking resources
- Smooth user experience

#### TC-PERF-002: Large Dataset Handling
**Objective:** Verify performance with large datasets
**Steps:**
1. Load transaction list with 1000+ records
2. Navigate through pages
3. Verify performance remains acceptable

**Expected Results:**
- Pagination works smoothly
- No lag or freezing
- Memory usage reasonable

### 11. Security Tests

#### TC-SEC-001: Card Number Masking
**Objective:** Verify card numbers are masked
**Steps:**
1. View transaction list
2. View transaction details
3. Verify card numbers show only last 4 digits

**Expected Results:**
- Card numbers masked as **** **** **** 1234
- Full number not visible in UI
- Full number not in HTML source

#### TC-SEC-002: Authentication Token
**Objective:** Verify authentication token is used
**Steps:**
1. Open browser dev tools
2. Make API request
3. Verify Authorization header present

**Expected Results:**
- Authorization header included
- Token format correct
- Token stored securely

### 12. Accessibility Tests

#### TC-ACC-001: Keyboard Navigation
**Objective:** Verify keyboard navigation works
**Steps:**
1. Use Tab key to navigate
2. Use Enter to activate buttons
3. Verify all interactive elements accessible

**Expected Results:**
- All elements reachable via keyboard
- Focus indicators visible
- Logical tab order

#### TC-ACC-002: Screen Reader Compatibility
**Objective:** Verify screen reader compatibility
**Steps:**
1. Use screen reader
2. Navigate through pages
3. Verify content is announced correctly

**Expected Results:**
- All content readable
- Form labels announced
- Error messages announced

## Test Execution Schedule

### Phase 1: Core Functionality (Week 1)
- Transaction list and detail pages
- Add transaction functionality
- Basic validation

### Phase 2: Extended Features (Week 2)
- Account management
- Report generation
- Advanced validation

### Phase 3: Non-Functional (Week 3)
- Performance testing
- Security testing
- Accessibility testing

### Phase 4: Integration (Week 4)
- End-to-end scenarios
- Cross-browser testing
- Final regression

## Test Metrics

### Success Criteria
- 100% of critical tests pass
- 95% of high priority tests pass
- 90% of medium priority tests pass
- No critical bugs in production

### Test Coverage Goals
- Code coverage: 80%
- Feature coverage: 100%
- Browser coverage: 4 major browsers
- Device coverage: Mobile, tablet, desktop

## Bug Reporting

### Bug Report Template
```
Title: [Brief description]
Priority: Critical/High/Medium/Low
Steps to Reproduce:
1. 
2. 
3. 
Expected Result:
Actual Result:
Environment:
- Browser:
- OS:
- Screen size:
Screenshots:
```

## Sign-off

### Test Lead Approval
- [ ] All critical tests passed
- [ ] All high priority tests passed
- [ ] Known issues documented
- [ ] Test report completed

### Development Team Approval
- [ ] All bugs fixed
- [ ] Code reviewed
- [ ] Documentation updated

### Product Owner Approval
- [ ] Features meet requirements
- [ ] User experience acceptable
- [ ] Ready for deployment

---

**Document Version:** 1.0  
**Last Updated:** 2024  
**Status:** Ready for Execution
