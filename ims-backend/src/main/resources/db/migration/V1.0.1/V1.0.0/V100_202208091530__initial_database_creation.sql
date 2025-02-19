CREATE TABLE warehouses (
  id_warehouse        INT4 NOT NULL,
  name                VARCHAR(45) NOT NULL,
  sid                 VARCHAR(255) NOT NULL,
  phone_number        VARCHAR(255),
  fk_address          INT NOT NULL,
  PRIMARY KEY (id_warehouse)
);

CREATE TABLE addresses (
  id_address             INT4 NOT NULL,
  street_name            VARCHAR(255) NOT NULL,
  latitude               FLOAT,
  longitude              FLOAT,
  map_url                VARCHAR(255),
  fk_town                INT NOT NULL,
  PRIMARY KEY (id_address)
);

CREATE TABLE towns (
  id_town                INT4 NOT NULL,
  name                   VARCHAR(45) NOT NULL,
  sid                    VARCHAR(255) NOT NULL,
  fk_county              INT NOT NULL,
  PRIMARY KEY (id_town)
);

CREATE TABLE counties (
  id_county              INT2 NOT NULL,
  name                   VARCHAR(45) NOT NULL,
  sid                    VARCHAR(255) NOT NULL,
  PRIMARY KEY (id_county)
);

CREATE TABLE warehouse_town_coverages (
  id_warehouse_town_coverage        INT NOT NULL,
  fk_town                           INT NOT NULL,
  fk_warehouse                      INT NOT NULL,
  PRIMARY KEY (id_warehouse_town_coverage)
);

ALTER TABLE warehouses ADD CONSTRAINT fk_warehouse_address FOREIGN KEY (fk_address) REFERENCES addresses (id_address);
ALTER TABLE warehouses ADD CONSTRAINT warehouse_sid_unique_idx UNIQUE (sid);
ALTER TABLE warehouses ADD CONSTRAINT warehouse_name_unique_idx UNIQUE (name);
ALTER TABLE addresses ADD CONSTRAINT fk_address_town FOREIGN KEY (fk_town) REFERENCES towns (id_town);
ALTER TABLE addresses ADD CONSTRAINT addresses_unique_id_address UNIQUE (id_address);
ALTER TABLE towns ADD CONSTRAINT fk_towns_county FOREIGN KEY (fk_county) REFERENCES counties (id_county);
ALTER TABLE towns ADD CONSTRAINT towns_sid_unique_idx UNIQUE (sid);
ALTER TABLE counties ADD CONSTRAINT counties_sid_unique_idx UNIQUE (sid);
ALTER TABLE warehouse_town_coverages ADD CONSTRAINT fk_warehouse_id FOREIGN KEY (fk_warehouse) REFERENCES warehouses (id_warehouse);
ALTER TABLE warehouse_town_coverages ADD CONSTRAINT fk_town_id FOREIGN KEY (fk_town) REFERENCES towns (id_town);
ALTER TABLE warehouse_town_coverages ADD CONSTRAINT warehouse_town_coverage_town_unique_idx UNIQUE (fk_warehouse,fk_town);

CREATE SEQUENCE warehouse_id_seq START 1 INCREMENT BY 50;
CREATE SEQUENCE address_id_seq START 1 INCREMENT BY 10;
CREATE SEQUENCE towns_id_seq START 1 INCREMENT BY 10;
CREATE SEQUENCE counties_id_seq START 1 INCREMENT BY 10;
CREATE SEQUENCE warehouse_town_coverages_id_seq START 1 INCREMENT BY 10;

SELECT setval(
  'warehouse_id_seq',
  (SELECT max(id_warehouse) FROM warehouses)
);

ALTER TABLE warehouses ALTER COLUMN id_warehouse SET DEFAULT nextval('warehouse_id_seq');
ALTER SEQUENCE warehouse_id_seq OWNED BY warehouses.id_warehouse;
ALTER TABLE addresses ALTER COLUMN id_address SET DEFAULT nextval('address_id_seq');
ALTER TABLE counties ALTER COLUMN id_county SET DEFAULT nextval('counties_id_seq');
ALTER TABLE towns ALTER COLUMN id_town SET DEFAULT nextval('towns_id_seq');
ALTER TABLE warehouse_town_coverages ALTER COLUMN id_warehouse_town_coverage SET DEFAULT nextval('warehouse_town_coverages_id_seq');
