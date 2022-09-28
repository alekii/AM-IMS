@Warehouse_updated
Feature: B01 - Warehouse Update

  Scenario: Kafka topics setup
    And I seek to the end of the Kafka topic "topic_warehouses"

  Scenario: Update Warehouse

    #update warehouse created in warehouseCreated feature
    Given I set the "REST" payload to "warehouses/create_warehouse.json"
    And I modify the "REST" payload key "warehouseName" with new value "WH"
    And I modify the "REST" payload key "mapUrl" with new value "http://f.vom"
    When I make a "warehouses/{warehouseSid}" PUT request
    Then I search the "REST" response using path "$.code" and expect the values "200"
    Then I search the "REST" response using path "$.data.name" and expect the values "WH"
    Then I search the "REST" response using path "$.data.address.mapUrl" and expect the values "http://f.vom"
