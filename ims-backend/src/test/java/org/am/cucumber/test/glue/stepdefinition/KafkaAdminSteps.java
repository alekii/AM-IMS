package org.am.cucumber.test.glue.stepdefinition;

import io.cucumber.java.en.Given;
import org.am.cucumber.test.glue.kafka.KafkaAdmin;
import org.junit.Assert;

public class KafkaAdminSteps {

    @Given("I create a Kafka topic with name {string}")
    public void iCreateAKafkaTopicWithName(final String topic) {

        boolean topicCreated = this.getKafkaKafkaAdmin().createTopic(topic);
        Assert.assertTrue(String.format("topic with name %s was not created", topic), topicCreated);
    }

    private KafkaAdmin getKafkaKafkaAdmin() {

        return KafkaAdmin.getInstance();
    }
}
