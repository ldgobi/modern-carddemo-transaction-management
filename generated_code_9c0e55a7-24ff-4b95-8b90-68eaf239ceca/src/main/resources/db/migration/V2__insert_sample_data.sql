-- Insert sample data with consistent relationships

-- Insert sample customers
INSERT INTO customers (customer_id, first_name, middle_name, last_name, ssn, address_line_1, address_line_2, address_line_3, state_code, country_code, zip_code, fico_credit_score) VALUES
(1001, 'John', 'Michael', 'Smith', 123456789, '123 Main Street', 'Apt 4B', NULL, 'NY', 'USA', '10001', 720),
(1002, 'Sarah', 'Ann', 'Johnson', 234567890, '456 Oak Avenue', NULL, NULL, 'CA', 'USA', '90210', 680),
(1003, 'Robert', 'James', 'Williams', 345678901, '789 Pine Road', 'Suite 100', NULL, 'TX', 'USA', '75001', 750),
(1004, 'Emily', 'Grace', 'Brown', 456789012, '321 Elm Street', NULL, NULL, 'FL', 'USA', '33101', 690),
(1005, 'Michael', 'David', 'Davis', 567890123, '654 Maple Drive', 'Unit 5', NULL, 'WA', 'USA', '98101', 810);

-- Insert sample accounts
INSERT INTO accounts (account_id, customer_id, active_status, current_balance, credit_limit, cash_credit_limit, open_date, expiration_date, current_cycle_credit, current_cycle_debit) VALUES
(5001, 1001, 'Y', 2450.75, 10000.00, 2000.00, '2023-01-15', '2028-01-31', 5500.00, 3049.25),
(5002, 1002, 'Y', 1250.00, 5000.00, 1000.00, '2023-03-20', '2028-03-31', 2800.00, 1550.00),
(5003, 1003, 'Y', 3750.50, 15000.00, 3000.00, '2022-11-10', '2027-11-30', 8200.00, 4449.50),
(5004, 1004, 'Y', 850.25, 8000.00, 1600.00, '2024-02-05', '2029-02-28', 3400.00, 2549.75),
(5005, 1005, 'Y', 5200.00, 20000.00, 4000.00, '2022-06-01', '2027-06-30', 12000.00, 6800.00);

-- Insert sample cards
INSERT INTO cards (card_number, account_id, customer_id, cvv_code, expiration_date) VALUES
('4532123456789012', 5001, 1001, '123', '2028-01-31'),
('4532234567890123', 5002, 1002, '456', '2028-03-31'),
('4532345678901234', 5003, 1003, '789', '2027-11-30'),
('4532456789012345', 5004, 1004, '234', '2029-02-28'),
('4532567890123456', 5005, 1005, '567', '2027-06-30');

-- Insert transaction types
INSERT INTO transaction_types (type_code, type_description) VALUES
('DB', 'Debit'),
('CR', 'Credit'),
('RF', 'Refund'),
('FE', 'Fee');

-- Insert transaction categories
INSERT INTO transaction_categories (type_code, category_code, category_description) VALUES
('DB', 1001, 'Groceries'),
('DB', 1002, 'Restaurants'),
('DB', 1003, 'Gas & Fuel'),
('DB', 1004, 'Shopping'),
('DB', 1005, 'Entertainment'),
('DB', 1006, 'Travel'),
('DB', 1007, 'Healthcare'),
('DB', 1008, 'Utilities'),
('CR', 2001, 'Payment Received'),
('CR', 2002, 'Cashback Reward'),
('RF', 3001, 'Purchase Refund'),
('RF', 3002, 'Service Refund'),
('FE', 4001, 'Annual Fee'),
('FE', 4002, 'Late Payment Fee'),
('FE', 4003, 'Foreign Transaction Fee');

-- Insert sample transactions
INSERT INTO transactions (transaction_id, card_number, account_id, type_code, category_code, source, description, amount, merchant_id, merchant_name, merchant_city, merchant_zip, original_timestamp, processed_timestamp) VALUES
('TX00001', '4532123456789012', 5001, 'DB', 1001, 'POS', 'Whole Foods Market Purchase', 156.45, 900001, 'Whole Foods Market', 'New York', '10001', '2025-11-01 10:30:00', '2025-11-01 10:30:15'),
('TX00002', '4532123456789012', 5001, 'DB', 1002, 'POS', 'Restaurant Dining', 85.50, 900002, 'The Italian Bistro', 'New York', '10002', '2025-11-02 19:15:00', '2025-11-02 19:15:10'),
('TX00003', '4532123456789012', 5001, 'DB', 1003, 'POS', 'Gas Station Fill-up', 65.00, 900003, 'Shell Gas Station', 'New York', '10003', '2025-11-03 08:45:00', '2025-11-03 08:45:05'),
('TX00004', '4532123456789012', 5001, 'CR', 2001, 'ONLINE', 'Monthly Payment', 500.00, 0, 'Online Payment', 'Online', '00000', '2025-11-04 14:00:00', '2025-11-04 14:00:20'),
('TX00005', '4532234567890123', 5002, 'DB', 1004, 'ONLINE', 'Amazon Purchase', 129.99, 900004, 'Amazon.com', 'Seattle', '98109', '2025-11-01 11:20:00', '2025-11-01 11:20:30'),
('TX00006', '4532234567890123', 5002, 'DB', 1001, 'POS', 'Trader Joes Groceries', 78.25, 900005, 'Trader Joes', 'Los Angeles', '90210', '2025-11-02 16:30:00', '2025-11-02 16:30:10'),
('TX00007', '4532234567890123', 5002, 'CR', 2002, 'SYSTEM', 'Cashback Reward', 15.50, 0, 'Cashback Program', 'System', '00000', '2025-11-03 00:00:00', '2025-11-03 00:00:05'),
('TX00008', '4532345678901234', 5003, 'DB', 1006, 'POS', 'Delta Airlines Ticket', 450.00, 900006, 'Delta Airlines', 'Atlanta', '30320', '2025-11-01 09:00:00', '2025-11-01 09:00:25'),
('TX00009', '4532345678901234', 5003, 'DB', 1005, 'POS', 'Movie Theater Tickets', 42.00, 900007, 'AMC Theaters', 'Dallas', '75001', '2025-11-02 20:00:00', '2025-11-02 20:00:10'),
('TX00010', '4532345678901234', 5003, 'RF', 3001, 'POS', 'Returned Item Refund', 89.99, 900008, 'Best Buy', 'Houston', '77001', '2025-11-03 15:30:00', '2025-11-03 15:30:15'),
('TX00011', '4532345678901234', 5003, 'FE', 4001, 'SYSTEM', 'Annual Card Fee', 95.00, 0, 'Annual Fee', 'System', '00000', '2025-11-04 00:00:00', '2025-11-04 00:00:05'),
('TX00012', '4532456789012345', 5004, 'DB', 1007, 'POS', 'CVS Pharmacy Purchase', 45.75, 900009, 'CVS Pharmacy', 'Miami', '33101', '2025-11-01 12:45:00', '2025-11-01 12:45:10'),
('TX00013', '4532456789012345', 5004, 'DB', 1008, 'ONLINE', 'Electric Bill Payment', 125.00, 900010, 'Florida Power & Light', 'Miami', '33102', '2025-11-02 10:00:00', '2025-11-02 10:00:20'),
('TX00014', '4532456789012345', 5004, 'CR', 2001, 'ONLINE', 'Monthly Payment', 300.00, 0, 'Online Payment', 'Online', '00000', '2025-11-05 14:30:00', '2025-11-05 14:30:15'),
('TX00015', '4532567890123456', 5005, 'DB', 1002, 'POS', 'Starbucks Coffee', 6.75, 900011, 'Starbucks', 'Seattle', '98101', '2025-11-01 07:30:00', '2025-11-01 07:30:05'),
('TX00016', '4532567890123456', 5005, 'DB', 1004, 'ONLINE', 'Apple Store Purchase', 999.00, 900012, 'Apple Store', 'Cupertino', '95014', '2025-11-02 13:00:00', '2025-11-02 13:00:30'),
('TX00017', '4532567890123456', 5005, 'DB', 1006, 'POS', 'Marriott Hotel Stay', 350.00, 900013, 'Marriott Hotel', 'San Francisco', '94102', '2025-11-03 16:00:00', '2025-11-03 16:00:20'),
('TX00018', '4532567890123456', 5005, 'CR', 2001, 'ONLINE', 'Monthly Payment', 1000.00, 0, 'Online Payment', 'Online', '00000', '2025-11-06 10:00:00', '2025-11-06 10:00:10'),
('TX00019', '4532123456789012', 5001, 'DB', 1004, 'ONLINE', 'Target Online Order', 234.50, 900014, 'Target', 'Minneapolis', '55403', '2025-11-07 11:00:00', '2025-11-07 11:00:25'),
('TX00020', '4532234567890123', 5002, 'FE', 4002, 'SYSTEM', 'Late Payment Fee', 25.00, 0, 'Late Fee', 'System', '00000', '2025-11-08 00:00:00', '2025-11-08 00:00:05');

-- Insert transaction category balances
INSERT INTO transaction_category_balances (account_id, type_code, category_code, balance) VALUES
-- Account 5001 balances
(5001, 'DB', 1001, 156.45),
(5001, 'DB', 1002, 85.50),
(5001, 'DB', 1003, 65.00),
(5001, 'DB', 1004, 234.50),
(5001, 'CR', 2001, 500.00),
-- Account 5002 balances
(5002, 'DB', 1001, 78.25),
(5002, 'DB', 1004, 129.99),
(5002, 'CR', 2002, 15.50),
(5002, 'FE', 4002, 25.00),
-- Account 5003 balances
(5003, 'DB', 1005, 42.00),
(5003, 'DB', 1006, 450.00),
(5003, 'RF', 3001, 89.99),
(5003, 'FE', 4001, 95.00),
-- Account 5004 balances
(5004, 'DB', 1007, 45.75),
(5004, 'DB', 1008, 125.00),
(5004, 'CR', 2001, 300.00),
-- Account 5005 balances
(5005, 'DB', 1002, 6.75),
(5005, 'DB', 1004, 999.00),
(5005, 'DB', 1006, 350.00),
(5005, 'CR', 2001, 1000.00);
