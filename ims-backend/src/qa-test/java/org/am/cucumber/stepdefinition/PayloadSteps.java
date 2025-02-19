package org.am.cucumber.stepdefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.am.cucumber.Rest.Rest;
import org.am.cucumber.Rest.RestHelper;
import org.am.cucumber.kafka.KafkaConsumerClient;
import org.am.cucumber.utils.Util;
import org.am.cucumber.utils.helpers.UtilHelper;
import org.junit.Assert;

import java.text.MessageFormat;
import java.util.Objects;

public class PayloadSteps {

    public PayloadSteps() {

    }

    @And("I modify the \"(Kafka|REST)\" payload key \"(.+)\" with new value \"(.+)\"$")
    public void iModifyThePayloadKeyWithNewValue(String payloadApp, String key, String newValue) {

        this.setMessagePayload(payloadApp, this.modifyPayload(payloadApp, key, newValue));
    }

    private String modifyPayload(final String payloadApp, final String key, final String newValue) {

        String payload = this.getPayload(payloadApp);
        return this.getUtil().replacePayload(payload, key, newValue);
    }

    @Given("^I set the \"(Kafka|REST)\" payload to \"(.+)\"$")
    public void setPayload(final String payloadApp, final String payloadFilePath) {

        String payload = getUtil().loadPayloadfromFile(payloadFilePath);
        this.setMessagePayload(payloadApp, payload);
        Assert.assertNotNull(MessageFormat.format("failed to set {0} payload", payloadApp), this.getPayload(payloadApp));
    }

    private String getPayload(String payloadApp) {

        Assert.assertNotNull("payload source service is not defined", payloadApp);
        switch (payloadApp) {
            case "REST":
                return this.getRest().getPayload();
            default:
                return "";
        }
    }

    @Then("^I search the \"(Kafka|REST)\" response using path \"(.+)\" and expect the values \"(.*)\"$")
    public void compareExpectedValuesWithResponseValues(final String requestService, final String dataPath, final String expectedValue) {

        String dataElement = getUtil().getValueFromDataPath(this.getResponsePayload(requestService), dataPath);
        Assert.assertEquals(this.getResponsePayloadErrors(requestService) + "\nValue conflicting!", expectedValue, dataElement);
    }

    private String getResponsePayloadErrors(String requestService) {

        switch (requestService) {
            case "REST":
                return "Last message request: " + this.getRest().getResponsePayload();
            case "Kafka":
                return "Last consumed message: " + this.getKafkaConsumer().getKafkaConsumedRecord().value() + "\n";
            default:
                return "";
        }
    }

    private void setMessagePayload(final String payloadApp, final String payload) {

        switch (payloadApp) {
            case "REST":
                this.getRest().setPayload(payload);
        }
    }

    private String getResponsePayload(final String payloadApp) {

        switch (payloadApp) {
            case "REST":
                return this.getRest().getResponsePayload();
            case "Kafka":
                Assert.assertTrue("kafka consumer is not present", Objects.nonNull(this.getKafkaConsumer()));
                Assert.assertTrue("kafka message is not present", Objects.nonNull(this.getKafkaConsumer().getKafkaConsumedRecord()));
                return this.getKafkaConsumer().getKafkaConsumedRecord().value();
            default:
                return "";
        }
    }

    private Util getUtil() {

        return UtilHelper.getUtil();
    }

    private Rest getRest() {

        return RestHelper.getRest();
    }

    private KafkaConsumerClient getKafkaConsumer() {

        return KafkaConsumerClient.getInstance();
    }
}
