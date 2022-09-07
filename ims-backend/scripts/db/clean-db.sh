#!/usr/bin/env bash

if [[ -z "$IMS_HOME" ]]; then
 echo "IMS_HOME environment not set"
 exit 1
fi

echo 'Cleaning db...'
./gradlew flywayClean -Purl=$FLYWAY_URL
echo 'Task complete -> Cleaning db'
