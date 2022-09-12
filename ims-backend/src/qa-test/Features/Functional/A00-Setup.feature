@Before
Feature: Setup Functional Tests

  Scenario: Reset db
    Given I set the resources path as "src/qa-test/resources/"
    Given I execute the script "db/reset-db.sh"

  Scenario: Set index symbols
    Given I set my wildcard symbols as "§" and "§"

  Scenario: Set IMS as Backend HOST
    Given I set the backend host with env var "IMS_BACKEND_HOST"

  Scenario: Setup Kafka
    Given I set the Kafka bootstrap servers url with env var "KAFKA_BOOTSTRAP_SERVERS"
    And I create a Kafka topic with name "topic_warehouses"
