ALTER TABLE warehouses
    ADD COLUMN IF NOT EXISTS created_at timestamp;
