#!/usr/bin/env bash

if [[ -z "$IMS_HOME" ]]; then
  echo "IMS_HOME environment variable is not set"
  exit 1
fi

cd "$IMS_HOME" || exit 1

echo 'Building db'

"$IMS_HOME/ims-backend/scripts/db/clean-db.sh" &&
"$IMS_HOME/ims-backend/scripts/db/migrate.sh" &&
"$IMS_HOME/ims-backend/scripts/db/insert-dummy-data.sh"

echo 'Task Complete -> Building db'
