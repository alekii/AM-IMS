#!/usr/bin/env sh

[ -z "$DEBUG" ] || set -x

set -e

cd "$(dirname "$0")/../../.."

cd docker
docker-compose down
docker-compose up -d ims-db
sleep 12
cd -

cd ims-backend/src/test/resources/
scripts/db-reset.sh
cd -

cd ims-backend/scripts/
db/migrate.sh

pg_dump > db/initial-dump.sql
cd -
cd docker
docker-compose down
cd -
