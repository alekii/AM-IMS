#!/usr/bin/env sh

PGPASS=${PGPASSWORD:-postgres}
HOST=${PGHOST:-localhost}
PORT=${PGPORT:-5432}
USER=${PGUSER:-postgres}
DB=${PGDATABASE:-ims}

PGPASSWORD=$PGPASS psql -h $HOST -p $PORT -U $USER -d $DB -c "DROP SCHEMA IF EXISTS public CASCADE;" &&
PGPASSWORD=$PGPASS psql -h $HOST -p $PORT -U $USER -d $DB -c "CREATE SCHEMA public;" &&

PGPASSWORD=$PGPASS psql -h $HOST -p $PORT -U $USER -d $DB < $IMS_HOME/ims-backend/scripts/db/initial-dump.sql &&

echo "Task complete -> db reset"
