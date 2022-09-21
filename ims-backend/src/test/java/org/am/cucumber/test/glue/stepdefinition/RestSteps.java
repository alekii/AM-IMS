package org.am.cucumber.test.glue.stepdefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.am.cucumber.test.glue.Rest.Rest;
import org.am.cucumber.test.glue.Rest.RestHelper;
import org.am.cucumber.test.glue.utils.Util;
import org.am.cucumber.test.glue.utils.helpers.UtilHelper;

public class RestSteps {

    private static final String BASE_URL = "https://bookstore.toolsqa.com";

    public RestSteps() {

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
