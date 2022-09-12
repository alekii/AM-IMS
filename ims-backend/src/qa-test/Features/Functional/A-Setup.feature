@Before
Feature: Setup Tests

  Scenario: Setup QA Tests
    Given I set the REST header "Content-Type" with value "application/json"
    Given I set the RabbitMQ header "version" with value "V1"
    Given I set the resources path as "src/test-qa/resources/"
    Given I set my wildcard symbols as "§" and "§"
#    Given I execute the script "db/reset-db.sh"

  Scenario: Set backend HOST
    Given I set the backend host with env var "EXC_BACKEND_HOST"

  Scenario Outline: Connect queue and binding
    Given I create the RabbitMQ queue "<queueName>" and bind it to the exchange "<exchange>" through the routing key "<routingKey>"
    Examples:
      | queueName   | exchange                | routingKey                       |
      | DUMMY_QUEUE | exc.et.service-requests | exc.*.logistics.service-requests |

  Scenario: Rabbit - Purge queue messages
    Given I delete all the messages from the RabbitMQ queue "DUMMY_QUEUE"
