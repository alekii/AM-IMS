ALTER TABLE purchases
    ADD COLUMN IF NOT EXISTS fk_warehouse INT NOT NULL;

ALTER TABLE purchases ADD CONSTRAINT fk_purchases_warehouse FOREIGN KEY (fk_warehouse) REFERENCES warehouses (id_warehouse);

ALTER TABLE products
    ADD COLUMN IF NOT EXISTS warehouse VARCHAR(255) NOT NULL;
