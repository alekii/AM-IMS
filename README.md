# AM-IMS

Enterprise Inventory Management System for an ecommerce application

## Requirements

### OpenJDK

This project uses `openjdk-11`.

The recommended way to install and switch between JDK versions is to use *SDKMAN*

Check how to install java 11 and switch between java versions using *SDKMAN*  at https://sdkman.io

## How to build

This project uses gradle as a build tool.

In the project run the below command to build compile and package the project.

```sh
./gradlew build
```

## How to run migrations

Migrations do not run automatically when the app starts.
They need to be run manually using:

```
./gradlew flywayMigrate
``` 

## How to run the app

After building the project and running migrations, run the following command:

```sh
./gradlew run
```

## Dockers to run the application can be found at:

`ims-backend/scripts/docker/`

## Scripts to reset db, migrate and apply dummy data can be found at:

`ims-backend/scripts/db/`
