ALTER TABLE warehouses
    ADD COLUMN IF NOT EXISTS created_at timestamp NOT NULL DEFAULT NOW()::timestamp;
