package org.am.cucumber.Rest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.am.cucumber.utils.Constants;
import org.am.cucumber.utils.Util;
import org.am.cucumber.utils.helpers.UtilHelper;
import org.apache.commons.text.StringSubstitutor;

import java.util.Objects;
import java.util.UUID;

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
        RestAssured.baseURI = this.getUtil().getBackendUrl();
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

        RequestSpecification request = prepareRequest();
        response = request.body(this.getPayload()).post(endpoint);
        this.setResponse(response);
    }

    public boolean makePutRequest(final String endpoint, final UUID sid) {

        RequestSpecification request = prepareRequest();
        if (Objects.nonNull(this.getPayload())) {
            response = request.body(this.getPayload()).put(endpoint, sid);
            if (response.body().asString().isEmpty()) {
                return false;
            }

            this.setResponse(response);
            return true;
        }
        return false;
    }

    private RequestSpecification prepareRequest() {

        RequestSpecification request = RestAssured.given();
        request.header("charset", "utf-8");
        request.header("Content-Type", payloadType);
        return request;
    }

    private void setResponse(Response response) {

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
