-- Create customers table
CREATE TABLE customers (
    customer_id BIGINT NOT NULL PRIMARY KEY,
    first_name VARCHAR(25) NOT NULL,
    middle_name VARCHAR(25),
    last_name VARCHAR(25) NOT NULL,
    ssn BIGINT NOT NULL,
    address_line_1 VARCHAR(50) NOT NULL,
    address_line_2 VARCHAR(50),
    address_line_3 VARCHAR(50),
    state_code VARCHAR(2) NOT NULL,
    country_code VARCHAR(3) NOT NULL,
    zip_code VARCHAR(10) NOT NULL,
    fico_credit_score INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create accounts table
CREATE TABLE accounts (
    account_id BIGINT NOT NULL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    active_status VARCHAR(1) NOT NULL CHECK (active_status IN ('Y', 'N')),
    current_balance DECIMAL(19,2) NOT NULL,
    credit_limit DECIMAL(19,2) NOT NULL,
    cash_credit_limit DECIMAL(19,2) NOT NULL,
    open_date DATE NOT NULL,
    expiration_date DATE NOT NULL,
    current_cycle_credit DECIMAL(19,2) NOT NULL,
    current_cycle_debit DECIMAL(19,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- Create cards table
CREATE TABLE cards (
    card_number VARCHAR(16) NOT NULL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    cvv_code VARCHAR(3) NOT NULL,
    expiration_date DATE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- Create transaction_types table
CREATE TABLE transaction_types (
    type_code VARCHAR(2) NOT NULL PRIMARY KEY,
    type_description VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create transaction_categories table
CREATE TABLE transaction_categories (
    type_code VARCHAR(2) NOT NULL,
    category_code INT NOT NULL,
    category_description VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (type_code, category_code),
    FOREIGN KEY (type_code) REFERENCES transaction_types(type_code)
);

-- Create transactions table
CREATE TABLE transactions (
    transaction_id VARCHAR(16) NOT NULL PRIMARY KEY,
    card_number VARCHAR(16) NOT NULL,
    account_id BIGINT NOT NULL,
    type_code VARCHAR(2) NOT NULL,
    category_code INT NOT NULL,
    source VARCHAR(10) NOT NULL,
    description VARCHAR(100) NOT NULL,
    amount DECIMAL(19,2) NOT NULL,
    merchant_id BIGINT NOT NULL,
    merchant_name VARCHAR(50) NOT NULL,
    merchant_city VARCHAR(50) NOT NULL,
    merchant_zip VARCHAR(10) NOT NULL,
    original_timestamp TIMESTAMP NOT NULL,
    processed_timestamp TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (card_number) REFERENCES cards(card_number),
    FOREIGN KEY (account_id) REFERENCES accounts(account_id),
    FOREIGN KEY (type_code) REFERENCES transaction_types(type_code)
);

-- Create transaction_category_balances table
CREATE TABLE transaction_category_balances (
    account_id BIGINT NOT NULL,
    type_code VARCHAR(2) NOT NULL,
    category_code INT NOT NULL,
    balance DECIMAL(19,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (account_id, type_code, category_code),
    FOREIGN KEY (account_id) REFERENCES accounts(account_id),
    FOREIGN KEY (type_code, category_code) REFERENCES transaction_categories(type_code, category_code)
);

-- Create indexes for better query performance
CREATE INDEX idx_transactions_card_number ON transactions(card_number);
CREATE INDEX idx_transactions_account_id ON transactions(account_id);
CREATE INDEX idx_transactions_original_timestamp ON transactions(original_timestamp);
CREATE INDEX idx_transactions_date_range ON transactions(original_timestamp, account_id);
CREATE INDEX idx_accounts_customer_id ON accounts(customer_id);
CREATE INDEX idx_cards_account_id ON cards(account_id);
CREATE INDEX idx_cards_customer_id ON cards(customer_id);
