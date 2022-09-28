@Warehouse_updated
Feature: B01 - Warehouse Update

  Scenario: Kafka topics setup
    And I seek to the end of the Kafka topic "topic_warehouses"

  Scenario: Update Warehouse

    #update warehouse created in warehouseCreated feature
    Given I set the "REST" payload to "warehouses/create_warehouse.json"
    And I modify the "REST" payload key "warehouseName" with new value "WH"
    And I modify the "REST" payload key "mapUrl" with new value "http://f.vom"
    And I modify the "REST" payload key "townSid" with new value "b6015b7f-abfc-4bb9-8630-221fece27cd4"
    When I make a "warehouses/{warehouseSid}" PUT request
    Then I search the "REST" response using path "$.code" and expect the values "200"
    Then I search the "REST" response using path "$.data.name" and expect the values "WH"
    Then I search the "REST" response using path "$.data.address.mapUrl" and expect the values "http://f.vom"
    Then I search the "REST" response using path "$.data.address.town.sid" and expect the values "b6015b7f-abfc-4bb9-8630-221fece27cd4"

    # Kafka
    And I consume Kafka message and store it
    And I search Kafka header using the key "event" and expect the value "WAREHOUSE_UPDATED"
    Then I search the "Kafka" response using path "$.warehouseName" and expect the values "WH"
    Then I search the "Kafka" response using path "$.phoneNumber" and expect the values "+254789345213"
    Then I search the "Kafka" response using path "$.contactName" and expect the values "Marion Emard DDS"
    Then I search the "Kafka" response using path "$.addressPayload.streetName" and expect the values "6th street"
    Then I search the "Kafka" response using path "$.addressPayload.mapUrl" and expect the values "http://f.vom"
    Then I search the "Kafka" response using path "$.addressPayload.longitude" and expect the values "-131.74708"
    Then I search the "Kafka" response using path "$.addressPayload.latitude" and expect the values "-33.572937"
    Then I search the "Kafka" response using path "$.addressPayload.townPayload.name" and expect the values "Nakuru"
    Then I search the "Kafka" response using path "$.addressPayload.townPayload.townSid" and expect the values "b6015b7f-abfc-4bb9-8630-221fece27cd4"
    Then I search the "Kafka" response using path "$.addressPayload.countyPayload.name" and expect the values "NAKURU"
    Then I search the "Kafka" response using path "$.addressPayload.countyPayload.countySid" and expect the values "f960fd96-32d9-4caa-85c8-edb476d7ebcc"
