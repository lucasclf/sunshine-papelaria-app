-- Migration para a tabela Products
CREATE TABLE Products (
                          id INTEGER PRIMARY KEY AUTOINCREMENT,
                          name VARCHAR(255) NOT NULL,
                          price DOUBLE NOT NULL,
                          stock INTEGER NOT NULL,
                          create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                          update_date DATETIME DEFAULT CURRENT_TIMESTAMP
);