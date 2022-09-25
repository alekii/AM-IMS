package org.am.cucumber.Rest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.am.cucumber.utils.Constants;
import org.am.cucumber.utils.Util;
import org.am.cucumber.utils.helpers.UtilHelper;
import org.apache.commons.text.StringSubstitutor;

public class Rest {

    private String endpoint;

    private String payload;

    private String payloadType;

    private String responsePayload;

    private Response response;

    public Rest() {

        this.endpoint = "/";
        this.payload = null;
        this.payloadType = "application/json";
    }

    public void setEndpoint(final String endpoint) {

        this.endpoint = Constants.FORWARD_SLASH + endpoint;
    }

    public void setPayload(final String payload) {

        this.payload = StringSubstitutor.replaceSystemProperties(payload);
    }

    public String getEndpoint() {

        return this.endpoint;
    }

    public String getPayload() {

        return this.payload;
    }

    public void makePostRequest(final String endpoint) {

        RestAssured.baseURI = this.getUtil().getBackendUrl();
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", payloadType);
        response = request.body(this.getPayload()).post(endpoint);
        int statusCode = response.statusCode();
        String body = response.getBody().asString();
        setResponsePayload("{\"code\":\"" + statusCode + "\",\"data\":" + body + "}");
    }

    public String getResponsePayload() {

        return responsePayload;
    }

    public void setResponsePayload(final String responsePayload) {

        this.responsePayload = responsePayload;
    }

    private Util getUtil() {

        return UtilHelper.getUtil();
    }
}
