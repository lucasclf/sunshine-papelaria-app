-- Migration para a tabela Orders
CREATE TABLE Orders (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        client_id INTEGER NOT NULL,
                        total_value DOUBLE NOT NULL,
                        status VARCHAR(50) NOT NULL,
                        create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                        update_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (client_id) REFERENCES Clients(id) ON DELETE CASCADE
);
