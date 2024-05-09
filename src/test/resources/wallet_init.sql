CREATE TABLE IF NOT EXISTS wallet (
    id UUID PRIMARY KEY,
    balance DECIMAL(19, 2) NOT NULL
);
INSERT INTO wallet (id, balance) values
('6e8e2314-a1ec-4e0f-938a-3014a61e8018', 1000),
('833e48a8-b663-4a65-a852-f04d13be8ec3', 500);
