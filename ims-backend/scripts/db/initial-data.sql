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
    '1e965b1c1-1c63-4012-a1da-26cd32a5307b'
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
