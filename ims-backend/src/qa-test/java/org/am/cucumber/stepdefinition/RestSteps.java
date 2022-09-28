package org.am.cucumber.stepdefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.am.cucumber.Rest.Rest;
import org.am.cucumber.Rest.RestHelper;
import org.am.cucumber.utils.Util;
import org.am.cucumber.utils.helpers.UtilHelper;
import org.junit.Assert;

import java.util.UUID;

public class RestSteps {

    private static final String BASE_URL = "https://bookstore.toolsqa.com";

    public RestSteps() {

    }

    @When("I make a \"(.+)\" PUT request")
    public void makePutRequest(String endpoint) {

        this.getRest().setEndpoint(endpoint);
        final UUID sid = UUID.fromString(this.getUtil().getValueFromDataPath(this.getRest().getResponsePayload(), "$.data.sid"));
        Assert.assertTrue("PUT " + endpoint + " error", this.getRest().makePutRequest(endpoint, sid));
    }

    @Given("^I set the backend host with env var \"(.+)\"$")
    public void setBackendHost(final String envVar) {

        getUtil().setBackendUrl(getUtil().getEnvValue(envVar));
    }

    @When("^I make a \"(.+)\" POST request$")
    public void makePostRequest(final String restEndpoint) {

        this.getRest().setEndpoint(restEndpoint);
        this.getRest().makePostRequest(restEndpoint);
    }

    private Util getUtil() {

        return UtilHelper.getUtil();
    }

    private Rest getRest() {

        return RestHelper.getRest();
    }
}
