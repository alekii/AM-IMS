ALTER TABLE products ADD CONSTRAINT fk_product_category FOREIGN KEY (fk_category) REFERENCES categories (id_category);
