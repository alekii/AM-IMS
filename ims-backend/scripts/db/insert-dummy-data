#!/usr/bin/env bash

if [[ -z "$IMS_HOME" ]]; then
 echo "IMS_HOME environment not set"
 exit 1
fi

cd "$IMS_HOME" || exit 1

echo "Inserting dummy data"

docker exec -i -u postgres docker_ims-db_1 psql -U postgres ims < "$IMS_HOME/ims-backend/scripts/db/initial-data.sql"

echo "Done Inserting dummy data"
