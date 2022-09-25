@Warehouse_created
Feature: B01 - .Warehouse Creation

  Scenario: Create Warehouse
    Given I set the "REST" payload to "warehouses/create_warehouse.json"
    When I make a "warehouses/create" POST request
    Then I search the "REST" response using path "$.code" and expect the values "201"
    Then I search the "REST" response using path "$.data.name" and expect the values "test_warehouse"
    Then I search the "REST" response using path "$.data.address.street" and expect the values "6th street"
    Then I search the "REST" response using path "$.data.address.town" and expect the values "Kiambu"
    Then I search the "REST" response using path "$.data.address.county" and expect the values "KIAMBU"

  # Kafka
    And I consume Kafka message and store it
    And I search Kafka header using the key "event" and expect the value "WAREHOUSE_CREATED"
    Then I search the "Kafka" response using path "$.warehouseName" and expect the values "test_warehouse"
    Then I search the "Kafka" response using path "$.phoneNumber" and expect the values "+254789345213"
    Then I search the "Kafka" response using path "$.contactName" and expect the values "Marion Emard DDS"
    Then I search the "Kafka" response using path "$.addressPayload.streetName" and expect the values "6th street"
    Then I search the "Kafka" response using path "$.addressPayload.mapUrl" and expect the values "http://map.net"
    Then I search the "Kafka" response using path "$.addressPayload.longitude" and expect the values "-131.74708"
    Then I search the "Kafka" response using path "$.addressPayload.latitude" and expect the values "-33.572937"
    Then I search the "Kafka" response using path "$.addressPayload.townPayload.name" and expect the values "Kiambu"
    Then I search the "Kafka" response using path "$.addressPayload.townPayload.townSid" and expect the values "2064eb28-d366-4323-b000-a8a27a0d6eb0"
    Then I search the "Kafka" response using path "$.addressPayload.countyPayload.name" and expect the values "KIAMBU"
    Then I search the "Kafka" response using path "$.addressPayload.countyPayload.countySid" and expect the values "ecc40f2d-1844-4004-b497-f947f561444d"
