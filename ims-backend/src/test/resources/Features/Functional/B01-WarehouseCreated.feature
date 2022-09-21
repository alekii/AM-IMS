Feature: B01 - .Warehouse Creation

  Scenario: Create Warehouse
    Given I set the "REST" payload to "warehouses/create_warehouse.json"
    When I make a "v1/api/warehouses" POST request
    Then I search the "REST" response using path "$.code" and expect the values "201"
