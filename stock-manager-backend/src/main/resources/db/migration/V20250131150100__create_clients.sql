-- Migration para a tabela Clients
CREATE TABLE Clients (
                         id INTEGER PRIMARY KEY AUTOINCREMENT,
                         name VARCHAR(255) NOT NULL,
                         address VARCHAR(255) NOT NULL,
                         cep VARCHAR(10) NOT NULL,
                         contact VARCHAR(30) NOT NULL,
                         status VARCHAR(50) NOT NULL,
                         create_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         update_date DATETIME DEFAULT CURRENT_TIMESTAMP
);