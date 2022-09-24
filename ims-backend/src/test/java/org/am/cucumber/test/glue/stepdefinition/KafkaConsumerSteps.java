package org.am.cucumber.test.glue.stepdefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.am.cucumber.test.glue.kafka.KafkaConsumerClient;
import org.apache.kafka.common.header.Header;
import org.junit.Assert;

import java.util.Objects;
import java.util.Optional;

public class KafkaConsumerSteps {

    @Then("I consume Kafka message and store it")
    public void iConsumeKafkaMessageAndStoreIt() {

        String topic = this.getKafkaConsumer().getKafkaConsumerTopic();
        Assert.assertTrue("No topic provided", Objects.nonNull(topic));
        boolean messageFound = this.getKafkaConsumer().getConsumedMessage((topic));
        Assert.assertTrue("Message is not present", messageFound);
        Assert.assertTrue("Kafka consumer record is not present", Objects.nonNull(this.getKafkaConsumer().getKafkaConsumedRecord()));
    }

    @And("I search Kafka header using the key \"(.+)\" and expect the value \"(.+)\"")
    public void iSearchKafkaHeaderUsingTheKeyAndExpectTheValue(final String headerKey, final String headerValue) {

        Optional<Header> headerRecord = this.getKafkaConsumer().getKafkaHeaderRecord(headerKey);
        Assert.assertTrue("key not present in Kafka headers", headerRecord.isPresent());
        Assert.assertEquals("key and value not present in Kafka headers", new String(headerRecord.get().value()), headerValue);
    }

    @When("I seek to the end of the Kafka topic {string}")
    public void iSeekToTheEndOfTheKafkaTopic(final String topic) {

        Assert.assertTrue("topic is not present", Objects.nonNull(topic));
        this.getKafkaConsumer().setKafkaConsumerTopic(topic);
        Assert.assertEquals("error while setting consumer", this.getKafkaConsumer().getKafkaConsumerTopic(), topic);
        this.getKafkaConsumer().seekConsumerToTheEnd(topic);
    }

    @When("I set Kafka consumer to topic {string}")
    public void iSetKafkaConsumerTopicTo(final String topic) {

        Assert.assertTrue("topic is not present", Objects.nonNull(topic));
        this.getKafkaConsumer().setKafkaConsumerTopic(topic);
        Assert.assertEquals("error while setting consumer", this.getKafkaConsumer().getKafkaConsumerTopic(), topic);
    }

    private KafkaConsumerClient getKafkaConsumer() {

        return KafkaConsumerClient.getInstance();
    }
}
