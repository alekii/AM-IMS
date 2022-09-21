#!/usr/bin/env sh
echo "resetting db..."
PGPASSWORD=$PGPASSWORD psql -h $PGHOST -p $PGPORT -U $PGUSER -d $PGDATABASE -c "DROP SCHEMA IF EXISTS public CASCADE;" &&
sh ${IMS_HOME}/gradlew -b "$IMS_HOME"/build.gradle flywayMigrate -Dflyway.user=$FLYWAY_USER -Dflyway.schemas=public -Dflyway.password=$FLYWAY_PASSWORD -Dflyway.url=$FLYWAY_URL -i
echo "Task complete -> db reset"
