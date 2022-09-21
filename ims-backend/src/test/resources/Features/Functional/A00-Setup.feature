@Before
Feature: Setup Functional Tests

  Scenario: Reset db
    Given I set the resources path as "src/test/resources/"
    Given I set the environment variable "IMS_HOME" with value "/home/alex/project/AM-IMS"
    Given I execute the script "scripts/db-reset.sh"
    Given I wait for "1000" milliseconds

  Scenario: Apply test data for tests
    Given I execute the script "scripts/apply-dummy-data.sh"

  Scenario: Set backend HOST
    Given I set the backend host with env var "IMS_BACKEND_HOST"

  Scenario: Create Warehouse
    Given I set the "REST" payload to "warehouses/create_warehouse.json"
    When I make a "warehouses/create" POST request
    Then I search the "REST" response using path "$.code" and expect the values "201"


