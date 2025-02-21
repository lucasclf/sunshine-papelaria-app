-- Migration para a tabela Orders
CREATE TABLE Orders (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        client_id INTEGER NOT NULL,
                        total_value DOUBLE NOT NULL DEFAULT 0.0,
                        status VARCHAR(50) NOT NULL,
                        discount DOUBLE NOT NULL DEFAULT 0.0,
                        freight DOUBLE NOT NULL DEFAULT 0.0,
                        refunded_value DOUBLE NOT NULL DEFAULT 0.0,
                        carrier_name VARCHAR(50),
                        tracking_code VARCHAR(50),
                        refunded_reason VARCHAR(200),
                        refunded_date DATETIME,
                        sent_date DATETIME,
                        payment_date DATETIME,
                        create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                        update_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (client_id) REFERENCES Clients(id) ON DELETE CASCADE
);
