ALTER TABLE warehouses DROP COLUMN IF EXISTS tracking_numbers_count;
DO $$
    BEGIN
        BEGIN
            ALTER TABLE warehouses ADD COLUMN tracking_numbers_count integer;
        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'column tracking_numbers_count already exists in warehouses.';
        END;
    END;
$$;