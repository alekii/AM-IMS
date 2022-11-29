CREATE TABLE products (
  id_product          INT NOT NULL,
  name                VARCHAR(50) NOT NULL,
  sid                 VARCHAR(255) NOT NULL,
  date_received       TIMESTAMP,
  fk_brand            INT NOT NULL,
  fk_category         INT NOT NULL,
  fk_supplier         INT NOT NULL,
  received_by         VARCHAR(255),
  sku                 VARCHAR(255),
  price               DOUBLE PRECISION NOT NULL,
  discount            DOUBLE PRECISION,
  quantity            INT NOT NULL,
  description         VARCHAR(1024),
  PRIMARY KEY (id_product)
);

CREATE TABLE categories (
  id_category            INT4 NOT NULL,
  sid                    VARCHAR(255) NOT NULL,
  name                   VARCHAR(255) NOT NULL,
  PRIMARY KEY (id_category)
);

CREATE TABLE brands (
  id_brand               INT4 NOT NULL,
  name                   VARCHAR(50) NOT NULL,
  sid                    VARCHAR(255) NOT NULL,
  PRIMARY KEY (id_brand)
);

CREATE TABLE suppliers (
  id_supplier            INT NOT NULL,
  name                   VARCHAR(50) NOT NULL,
  sid                    VARCHAR(255) NOT NULL,
  lead_time              INT,
  phone_number           VARCHAR(255),
  email                  VARCHAR(255),
  PRIMARY KEY (id_supplier)
);

CREATE TABLE purchases (
  id_purchases            INT NOT NULL,
  sid                     VARCHAR(255) NOT NULL,
  fk_supplier             INT,
  invoice_number          INT,
  date_received           TIMESTAMP,
  bill_value              DOUBLE PRECISION,
  purchase_status         VARCHAR(255) NOT NULL,
  PRIMARY KEY (id_purchases)
);

CREATE TABLE line_items (
  id_line_item            INT NOT NULL,
  sid                     VARCHAR(255) NOT NULL,
  fk_product              INT NOT NULL,
  fk_purchases            INT NOT NULL,
  price                   DOUBLE PRECISION NOT NULL,
  quantity                INT NOT NULL,
  PRIMARY KEY (id_line_item)
);

CREATE TABLE images (
  id_image                INT NOT NULL,
  sid                     VARCHAR(255) NOT NULL,
  fk_product              INT NOT NULL,
  image_path              VARCHAR(255) NOT NULL,
  PRIMARY KEY (id_image)
);

ALTER TABLE products ADD CONSTRAINT fk_product_brand FOREIGN KEY (fk_brand) REFERENCES brands (id_brand);
ALTER TABLE products ADD CONSTRAINT fk_product_category FOREIGN KEY (fk_category) REFERENCES categories (id_category);
ALTER TABLE products ADD CONSTRAINT fk_product_supplier FOREIGN KEY (fk_supplier) REFERENCES suppliers (id_supplier);
ALTER TABLE products ADD CONSTRAINT product_sid_unique_idx UNIQUE (sid);
ALTER TABLE products ADD CONSTRAINT product_name_unique_idx UNIQUE (name);

ALTER TABLE categories ADD CONSTRAINT category_sid_unique_idx UNIQUE (sid);
ALTER TABLE categories ADD CONSTRAINT category_name_unique_idx UNIQUE (name);

ALTER TABLE brands ADD CONSTRAINT brand_name_unique_idx UNIQUE (name);
ALTER TABLE brands ADD CONSTRAINT brand_sid_unique_idx UNIQUE (sid);

ALTER TABLE suppliers ADD CONSTRAINT supplier_phone_number_unique_idx UNIQUE (phone_number);
ALTER TABLE suppliers ADD CONSTRAINT supplier_email_unique_idx UNIQUE (email);
ALTER TABLE suppliers ADD CONSTRAINT supplier_name_unique_idx UNIQUE (name);
ALTER TABLE suppliers ADD CONSTRAINT supplier_sid_unique_idx UNIQUE (sid);

ALTER TABLE purchases ADD CONSTRAINT fk_purchases_supplier FOREIGN KEY (fk_supplier) REFERENCES suppliers (id_supplier);
ALTER TABLE purchases ADD CONSTRAINT purchases_invoice_number_unique_idx UNIQUE (invoice_number);
ALTER TABLE purchases ADD CONSTRAINT purchases_sid_unique_idx UNIQUE (sid);

ALTER TABLE line_items ADD CONSTRAINT fk_product_id FOREIGN KEY (fk_product) REFERENCES products (id_product);
ALTER TABLE line_items ADD CONSTRAINT fk_purchases_id FOREIGN KEY (fk_purchases) REFERENCES purchases (id_purchases);
ALTER TABLE line_items ADD CONSTRAINT line_item_sid_unique_idx UNIQUE (sid);

ALTER TABLE images ADD CONSTRAINT fk_product_image FOREIGN KEY (fk_product) REFERENCES products (id_product);
ALTER TABLE images ADD CONSTRAINT image_sid_unique_index UNIQUE (sid);

CREATE SEQUENCE product_id_seq START 1 INCREMENT BY 1;
CREATE SEQUENCE category_id_seq START 1 INCREMENT BY 50;
CREATE SEQUENCE brand_id_seq START 1 INCREMENT BY 50;
CREATE SEQUENCE supplier_id_seq START 1 INCREMENT BY 50;
CREATE SEQUENCE purchases_id_seq START 1 INCREMENT BY 1;
CREATE SEQUENCE line_item_id_seq START 1 INCREMENT BY 1;
CREATE SEQUENCE image_id_seq START 1 INCREMENT BY 1;

ALTER TABLE products ALTER COLUMN id_product SET DEFAULT nextval('product_id_seq');
ALTER TABLE categories ALTER COLUMN id_category SET DEFAULT nextval('category_id_seq');
ALTER TABLE brands ALTER COLUMN id_brand SET DEFAULT nextval('brand_id_seq');
ALTER TABLE suppliers ALTER COLUMN id_supplier SET DEFAULT nextval('supplier_id_seq');
ALTER TABLE purchases ALTER COLUMN id_purchases SET DEFAULT nextval('purchases_id_seq');
ALTER TABLE line_items ALTER COLUMN id_line_item SET DEFAULT nextval('line_item_id_seq');
ALTER TABLE images ALTER COLUMN id_image SET DEFAULT nextval('image_id_seq');
