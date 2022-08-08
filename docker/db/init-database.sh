#!/bin/bash
 PGPASSWORD=postgres psql -h localhost -p 5432 -U postgres -c "DROP DATABASE IF EXISTS $DB;" &&
 PGPASSWORD=postgres psql -h localhost -p 5432 -U postgres -c "CREATE DATABASE $DB;"
