DROP TABLE IF EXISTS "warehouses" CASCADE;
DROP TABLE IF EXISTS "addresses" CASCADE;
DROP TABLE IF EXISTS "towns" CASCADE;
DROP TABLE IF EXISTS "counties" CASCADE;
DROP TABLE IF EXISTS "warehouse_town_coverages" CASCADE;

DROP TABLE IF EXISTS "suppliers" CASCADE;
DROP TABLE IF EXISTS "purchases" CASCADE;
DROP TABLE IF EXISTS "categories" CASCADE;
DROP TABLE IF EXISTS "brands" CASCADE;
DROP TABLE IF EXISTS "products" CASCADE;
DROP TABLE IF EXISTS "line_items" CASCADE;

DROP SEQUENCE IF EXISTS "warehouse_id_seq" CASCADE;
DROP SEQUENCE IF EXISTS "address_id_seq" CASCADE;
DROP SEQUENCE IF EXISTS "towns_id_seq" CASCADE;
DROP SEQUENCE IF EXISTS "counties_id_seq" CASCADE;
DROP SEQUENCE IF EXISTS "warehouse_town_coverages_id_seq" CASCADE;

DROP SEQUENCE IF EXISTS "category_id_seq" CASCADE;
DROP SEQUENCE IF EXISTS "brand_id_seq" CASCADE;
DROP SEQUENCE IF EXISTS "supplier_id_seq" CASCADE;
DROP SEQUENCE IF EXISTS "purchases_id_seq" CASCADE;
DROP SEQUENCE IF EXISTS "product_id_seq" CASCADE;
DROP SEQUENCE IF EXISTS "line_item_id_seq" CASCADE;
