package org.am.cucumber.utils;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

public class Util {

    private String backendHost;

    private String resourcesPath = "src/test/resources/";

    public Util() {

        this.backendHost = System.getProperty("backend.host");
    }

    public void setBackendUrl(final String backendURL) {

        if (StringUtils.isNotEmpty(backendURL)) {
            this.backendHost = backendURL;
        }
    }

    public void setResourcesPath(String resourcePath) {

        this.resourcesPath = resourcePath;
    }

    public String getEnvValue(final String envVar) {

        try {
            final String value = System.getenv(envVar);
            if (value.isEmpty()) {
                throw new NullPointerException("No value for env value = " + envVar + " found");
            }
            return System.getenv(envVar);
        } catch (final NullPointerException err) {
            throw new NullPointerException("No value for env value = " + envVar + " found");
        }
    }

    public String getResourcesPath() {

        return resourcesPath;
    }

    public String getBackendUrl() {

        return backendHost;
    }

    public String loadPayloadfromFile(final String payloadPath) throws IOException {

        String payload = "";
        File file = new File(this.resourcesPath + payloadPath);
        try (BufferedInputStream bufferedInput = new BufferedInputStream(new FileInputStream(file));
             InputStreamReader stream = new InputStreamReader(bufferedInput, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(stream)) {
            payload = br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
        return payload;
    }

    public String getValueFromDataPath(final String payload, final String dataPath) {

        Object results;
        try {
            results = JsonPath.using(Configuration.defaultConfiguration()).parse(payload).read(dataPath);
        } catch (PathNotFoundException e) {
            return "Key not found";
        }
        return Objects.isNull(results) ? "null" : results.toString();
    }
}
