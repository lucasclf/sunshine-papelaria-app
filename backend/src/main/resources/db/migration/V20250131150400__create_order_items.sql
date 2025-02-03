-- Migration para a tabela OrderItems
CREATE TABLE OrderItems (
                            order_id INTEGER NOT NULL,
                            product_id INTEGER NOT NULL,
                            quantity INTEGER NOT NULL,
                            create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                            PRIMARY KEY (order_id, product_id),
                            FOREIGN KEY (order_id) REFERENCES Orders(id) ON DELETE CASCADE,
                            FOREIGN KEY (product_id) REFERENCES Products(id) ON DELETE RESTRICT
);