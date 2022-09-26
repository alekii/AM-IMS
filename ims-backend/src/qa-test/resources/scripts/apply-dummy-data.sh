#!/usr/bin/env sh
echo "applying test data..."
psql -U $PGUSER -h $PGHOST -p $PGPORT -d $PGDATABASE -a -f src/test/resources/scripts/dummy-data.sql
echo "Task Complete -> applying dummy data"
