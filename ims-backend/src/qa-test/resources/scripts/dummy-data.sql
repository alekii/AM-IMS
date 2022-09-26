INSERT INTO counties (id_county, name, sid)
VALUES
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
