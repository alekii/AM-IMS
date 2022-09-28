@Before
Feature: Setup Functional Tests

  Scenario: Set backend HOST
    Given I set the backend host with env var "IMS_BACKEND_HOST"

  Scenario: Reset db
    Given I set the resources path as "src/qa-test/resources/"
    Given I execute the script "scripts/db-reset.sh"
    Given I wait for "1000" milliseconds

  Scenario: Kafka topics setup
    Given I create a Kafka topic with name "topic_warehouses"
    Then I set Kafka consumer to topic "topic_warehouses"
    And I seek to the end of the Kafka topic "topic_warehouses"
