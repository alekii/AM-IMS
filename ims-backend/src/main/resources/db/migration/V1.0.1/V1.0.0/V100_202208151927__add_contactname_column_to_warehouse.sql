ALTER TABLE warehouses DROP COLUMN IF EXISTS contact_name;
DO $$
    BEGIN
        BEGIN
            ALTER TABLE warehouses ADD COLUMN contact_name VARCHAR(255);
        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'column contact_name already exists in warehouses.';
        END;
    END;
$$;
