# IMS

Warehouse API

## How to run QA Tests

### First start the required docker containers

In order to successfully run QA tests, DB and Kafka containers need to be running.

#### Start docker and Kafka

```sh
./ims-backend/scripts/docker/dev
```

Start the application

```sh
./gradlew run
```

And finally, run the QATestRunner file to run the QA tests

```
AM-IMS/ims-backend/src/qa-test/java/QaTestRunner.java
```

You can also run the qa tests from the project root

```
./gradlew cucumber
```

All the tests present in the following directory will be run:

```sh
ims-backend/src/qa-test/resources/features
```
