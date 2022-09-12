#!/usr/bin/env sh
sh ${IMS_HOME}/gradlew -b "$IMS_HOME"/build.gradle flywayClean flywayMigrate -Dflyway.user=$FLYWAY_USER -Dflyway.schemas=public -Dflyway.password=$FLYWAY_PASSWORD -Dflyway.url=$FLYWAY_URL -i
