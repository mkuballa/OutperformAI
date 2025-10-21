CREATE TABLE portfolio (
    id UUID PRIMARY KEY,
    total_value NUMERIC(19, 2) NOT NULL,
    daily_change_value NUMERIC(19, 2) NOT NULL,
    daily_change_percent NUMERIC(5, 2) NOT NULL,
    total_change_value NUMERIC(19, 2) NOT NULL,
    total_change_percent NUMERIC(5, 2) NOT NULL
);

CREATE TABLE holding (
    id UUID PRIMARY KEY,
    portfolio_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    symbol VARCHAR(10) NOT NULL,
    quantity INTEGER NOT NULL,
    price NUMERIC(19, 2) NOT NULL,
    purchase_price NUMERIC(19, 2) NOT NULL,
    purchase_date DATE NOT NULL,
    daily_change_value NUMERIC(19, 2) NOT NULL,
    daily_change_percent NUMERIC(5, 2) NOT NULL,
    total_change_value NUMERIC(19, 2) NOT NULL,
    total_change_percent NUMERIC(5, 2) NOT NULL,
    FOREIGN KEY (portfolio_id) REFERENCES portfolio(id)
);

CREATE TABLE portfolio_history (
    id UUID PRIMARY KEY,
    portfolio_id UUID NOT NULL,
    date DATE NOT NULL,
    value NUMERIC(19, 2) NOT NULL,
    FOREIGN KEY (portfolio_id) REFERENCES portfolio(id)
);
