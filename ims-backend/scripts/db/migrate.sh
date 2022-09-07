#!/usr/bin/env bash

if [[ -z "$IMS_HOME" ]]; then
  echo "IMS_HOME environment variable not set"
  exit 1
fi

cd "$IMS_HOME" || exit 1

echo 'Migrating db'
./gradlew flywayMigrate -Purl=$FLYWAY_URL
echo 'Task Complete -> Done Migrating'
