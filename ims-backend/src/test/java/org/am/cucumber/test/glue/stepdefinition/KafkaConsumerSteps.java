package org.am.cucumber.test.glue.stepdefinition;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.am.cucumber.test.glue.kafka.KafkaConsumerClient;
import org.junit.Assert;

import java.util.Objects;

public class KafkaConsumerSteps {

    @Then("I consume Kafka message and store it")
    public void iConsumeKafkaMessageAndStoreIt() {

        String topic = this.getKafkaConsumer().getKafkaConsumerTopic();
        Assert.assertTrue("No topic provided", Objects.nonNull(topic));
        boolean messageFound = this.getKafkaConsumer().getConsumedMessage((topic));
        Assert.assertTrue("Message is not present", messageFound);
        Assert.assertTrue("Kafka consumer record is not present", Objects.nonNull(this.getKafkaConsumer().getKafkaConsumedRecord()));
    }

    @When("I set Kafka consumer topic to {string}")
    public void iSetKafkaConsumerTopicTo(final String topic) {

        Assert.assertTrue("topic is not present", Objects.nonNull(topic));
        this.getKafkaConsumer().setKafkaConsumerTopic(topic);
        Assert.assertEquals("error while setting consumer", this.getKafkaConsumer().getKafkaConsumerTopic(), topic);
    }

    @Then("I verify Kafka header key {string} has value {string}")
    public void iVerifyKafkaHeaderKeyHasValue(String arg0, String arg1) {

    }

    private KafkaConsumerClient getKafkaConsumer() {

        return KafkaConsumerClient.getInstance();
    }
}
