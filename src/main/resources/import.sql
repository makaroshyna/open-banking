INSERT INTO account (iban, owner_name, balance) VALUES
('DE89370400440532013000', 'Alice', 5000.00),
('FR1420041010050500013M02606', 'Bob', 2500.00);

INSERT INTO transaction (account_iban, amount, currency, timestamp, description) VALUES
('DE89370400440532013000', 100.50, 'EUR', NOW(), 'Grocery shopping'),
('DE89370400440532013000', 250.00, 'EUR', NOW(), 'Online purchase'),
('FR1420041010050500013M02606', 50.00, 'EUR', NOW(), 'Coffee shop');
