#!/bin/bash
set -e

function exec_sql() {
    psql -v ON_ERROR_STOP=1 --dbname "$DB" --username "$POSTGRES_USER" --command "$1"
}

exec_sql "DROP DATABASE IF EXISTS $DB;"
exec_sql "CREATE DATABASE $DB;"