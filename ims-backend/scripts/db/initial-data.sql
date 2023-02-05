INSERT INTO counties (id_county, name, sid)
VALUES
  (
    nextval ('counties_id_seq'),
    'ELDORET',
    '1d545f3d-7dce-4463-9ca6-48f45f03d444'
  ),
  (
    nextval ('counties_id_seq'),
    'MOMBASA',
    '4af2a28d-5c9a-428a-badd-6249275c06c4'
  ),
  (
    nextval ('counties_id_seq'),
    'NAIROBI',
    '1e965b1c1-1c63-4012-a1da-26cd32a5307'
  ),
  (
    nextval ('counties_id_seq'),
    'NAKURU',
    'f960fd96-32d9-4caa-85c8-edb476d7ebcc'
  ),
  (
    nextval ('counties_id_seq'),
    'KIAMBU',
    'ecc40f2d-1844-4004-b497-f947f561444d'
  );
INSERT INTO towns(id_town, name, sid, fk_county)
VALUES
  (
    nextval ('towns_id_seq'),
    'Eldoret',
    '7fd1ba57-b0c9-403c-bf27-e8e4457cc33c',
    (
      SELECT
        id_county
      FROM
        counties county
      WHERE
        county.id_county = 1
    )
  ),
  (
    nextval ('towns_id_seq'),
    'Mombasa',
    'be5652f5-e6c3-41e2-a8f3-10f2c6dfb8b1',
    (
      SELECT
        id_county
      FROM
        counties county
      WHERE
        county.id_county = 11
    )
  ),
  (
    nextval ('towns_id_seq'),
    'Nairobi',
    'd040a3f8-58be-448c-ad26-015b9be054fc',
    (
      SELECT
        id_county
      FROM
        counties county
      WHERE
        county.id_county = 21
    )
  ),
  (
    nextval ('towns_id_seq'),
    'Nakuru',
    'b6015b7f-abfc-4bb9-8630-221fece27cd4',
    (
      SELECT
        id_county
      FROM
        counties county
      WHERE
        county.id_county = 31
    )
  ),
  (
    nextval ('towns_id_seq'),
    'Kiambu',
    '2064eb28-d366-4323-b000-a8a27a0d6eb0',
    (
      SELECT
        id_county
      FROM
        counties county
      WHERE
        county.id_county = 41
    )
  );
INSERT INTO addresses (
  id_address, street_name, latitude,
  longitude, map_url, fk_town
)
VALUES
  (
    nextval ('address_id_seq'),
    '5th Avenue',
    30.82609,
    148.20398,
    'http://map1.com',
    (
      SELECT
        id_town
      FROM
        towns town
      WHERE
        town.id_town = 1
    )
  ),
  (
    nextval ('address_id_seq'),
    'Lumber Way',
    5.87776,
    40.45254,
    'http://map2.com',
    (
      SELECT
        id_town
      FROM
        towns town
      WHERE
        town.id_town = 41
    )
  ),
  (
    nextval ('address_id_seq'),
    'Lilypad Passage',
    14.94752,
    15.83095,
    'http://map3.com',
    (
      SELECT
        id_town
      FROM
        towns town
      WHERE
        town.id_town = 31
    )
  ),
  (
    nextval ('address_id_seq'),
    'Sapphire Way',
    39.28248,
    58.25683,
    'http://map4.com',
    (
      SELECT
        id_town
      FROM
        towns town
      WHERE
        town.id_town = 21
    )
  ),
  (
    nextval ('address_id_seq'),
    '5th Avenue',
    -21.18794,
    -48.18496,
    'http://map5.com',
    (
      SELECT
        id_town
      FROM
        towns town
      WHERE
        town.id_town = 11
    )
  ),
  (
    nextval ('address_id_seq'),
    '42nd Freedom Avenue',
    -24.59142,
    67.39737,
    'http://map6.com',
    (
      SELECT
        id_town
      FROM
        towns town
      WHERE
        town.id_town = 31
    )
  );
INSERT INTO warehouses (
  id_warehouse, sid, name, phone_number,
  contact_name, created_at, tracking_numbers_count,
  fk_address
)
VALUES
  (
    nextval('warehouse_id_seq'),
    '969f795c-b550-4f4e-80de-0db4c317581a',
    'Warehouse1',
    '+254734567892',
    'Manager1',
    '2022-09-06T21:07:15.039575Z',
    365423,
    (
      SELECT
        id_address
      FROM
        addresses address
      WHERE
        address.id_address = 1
    )
  ),
  (
    nextval('warehouse_id_seq'),
    '06f60db0-90b6-4119-b267-7246a8c8428c',
    'Warehouse2',
    '+254767234156',
    'Manager2',
    '2022-09-06T21:06:47.051642Z',
    158355,
    (
      SELECT
        id_address
      FROM
        addresses address
      WHERE
        address.id_address = 11
    )
  ),
  (
    nextval('warehouse_id_seq'),
    '3e083c68-f193-4bfc-b53c-7a58869c9048',
    'Warehouse3',
    '+254723890567',
    'Manager3',
    '2022-09-06T21:06:13.669522Z',
    159452,
    (
      SELECT
        id_address
      FROM
        addresses address
      WHERE
        address.id_address = 21
    )
  ),
  (
    nextval('warehouse_id_seq'),
    '18b401c1-0598-4fb7-9f5b-7417d25de8ec',
    'Warehouse4',
    '+254737234678',
    'Manager4',
    '2022-09-06T20:53:58.864771Z',
    337433,
    (
      SELECT
        id_address
      FROM
        addresses address
      WHERE
        address.id_address = 31
    )
  ),
  (
    nextval('warehouse_id_seq'),
    'a9b3746f-63d0-41d6-9589-f3ac4aadf44c',
    'Warehouse5',
    '+254712908453',
    'Manager5',
    '2022-09-06T21:05:42.252271Z',
    116564,
    (
      SELECT
        id_address
      FROM
        addresses address
      WHERE
        address.id_address = 41
    )
  ),
  (
    nextval('warehouse_id_seq'),
    '9d771afd-ff6c-49c5-a120-c7da7774b0f5',
    'Warehouse6',
    '+254708452985',
    'Manager6',
    '2022-09-06T21:01:40.593941Z',
    721218,
    (
      SELECT
        id_address
      FROM
        addresses address
      WHERE
        address.id_address = 51
    )
  );
INSERT INTO suppliers (id_supplier, name, sid, lead_time, phone_number, email)
VALUES  (
           nextval ('supplier_id_seq'),
           'XB Suppliers',
           '1d545f3d-7dce-4463-9ca6-43f43f03d324',
           '2',
           '+254789098987',
           'xbsuppliers@xb.com'
         );

INSERT INTO purchases (id_purchases, sid, fk_supplier, invoice_number, date_received, bill_value, purchase_status, warehouse)
VALUES  (
           nextval ('purchases_id_seq'),
           '1d545f3d-7dce-4463-9ca6-43f43f03d324',
           (
            SELECT
              id_supplier
            FROM
              suppliers supplier
            WHERE
              supplier.id_supplier = 1
          ),
           '22362735',
           '2022-10-11T21:05:42.252271Z',
           '4234',
           '1',
           '06f60db0-90b6-4119-b267-7246a8c8428c'
         );

INSERT INTO brands(id_brand, sid, name)
VALUES
(
    nextval('brand_id_seq'),
    '36b327c1-3451-0fb7-8f5b-1324d90de4ec',
    'sony'
);

INSERT INTO categories(id_category, sid, name)
VALUES
(
    nextval('category_id_seq'),
    '18b327c1-0598-4fb7-8f5b-4321d90de4ec',
    'electronics'
);

INSERT INTO products (
    id_product,
    name,
    sid,
    date_received,
    fk_brand,
    fk_category,
    fk_supplier,
    received_by,
    sku,
    price,
    discount,
    quantity,
    description,
    warehouse
   )
   VALUES(
   nextval ('product_id_seq'),
   'socket',
   '18b401c1-0598-4fb7-9f5b-5678d23de4ec',
   '2022-10-11T21:09:00.252271Z',
  (
       SELECT
         id_brand
       FROM
         brands brand
       WHERE
         brand.id_brand = 1
  ),
    (
         SELECT
           id_category
         FROM
           categories category
         WHERE
           category.id_category = 1
        ),
         (
          SELECT
            id_supplier
          FROM
            suppliers supplier
          WHERE
            supplier.id_supplier = 1
         ),
        'me',
        'fjhfgsdhfgsdhfgdshfg',
        '34534',
        '0',
        '4',
        'this is a socket',
        '90b327c1-0598-4fb7-8f5b-4321d09de4ec'
   );

INSERT INTO line_items(
id_line_item, sid, fk_product, fk_purchases,price, quantity
)
VALUES(
nextval('line_item_id_seq'),
'14b327c1-6789-5fb7-8f5b-4321d00de4ec',
  (
      SELECT
        id_product
      FROM
        products product
      WHERE
        product.id_product = 1
  ),
     (
         SELECT
           id_purchases
         FROM
           purchases purchase
         WHERE
           purchase.id_purchases= 1
     ),
     '4',
     '6564'
);
