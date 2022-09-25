#!/usr/bin/env sh

echo "resetting db..."

PGPASSWORD=$PGPASSWORD psql -h $PGHOST -p $PGPORT -U $PGUSER -d $PGDATABASE -c "DROP SCHEMA IF EXISTS public CASCADE;" &&
PGPASSWORD=$PGPASSWORD psql -h $PGHOST -p $PGPORT -U $PGUSER -d $PGDATABASE -c "CREATE SCHEMA public;" &&

PGPASSWORD=$PGPASSWORD psql -h $PGHOST -p $PGPORT -U $PGUSER -d $PGDATABASE < $IMS_HOME/ims-backend/scripts/db/initial-dump.sql &&

echo "Task complete -> db reset"
