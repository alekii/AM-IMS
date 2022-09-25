package org.am.cucumber.stepdefinition;

import io.cucumber.java.en.Given;
import org.am.cucumber.utils.Util;
import org.am.cucumber.utils.helpers.UtilHelper;
import org.junit.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class FunctionalSteps {

    private static Map<String, String> envVariables = new HashMap<>();

    @Given("^I set the environment variable \"(.+)\" with value \"(.*)\"$")
    public void setEnvironmentVariable(final String varName, final String varValue) {

        envVariables.put(varName, varValue);
    }

    @Given("^I execute the script \"(.+)\"$")
    public void iExecuteTheScript(String scriptName) throws IOException {

        Assert.assertTrue("An error occured while resetting db", this.executeScript(scriptName));
    }

    public boolean executeScript(String scriptName) throws IOException {

        final String file = getUtil().getResourcesPath() + scriptName;
        final ProcessBuilder processBuilder = new ProcessBuilder(file);

        for (final Map.Entry<String, String> entry : envVariables.entrySet()) {

            processBuilder.environment().put(entry.getKey(), entry.getValue());
        }
        try {
            final Process process = processBuilder.start();

            InputStream inputStream = process.getInputStream();

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            ProcessReader processReader = new ProcessReader(inputStream);
            Future<List<String>> future = executorService.submit(processReader);

            List<String> results = future.get();

            for (String res : results) {
                System.out.println(res);
            }

            int exitCode = process.waitFor();

            process.destroy();
            return exitCode == 0;
        } catch (Throwable e) {
            //TODO ADD LOGGER
            throw new IOException(e);
        }
    }

    @Given("^I set the resources path as \"(.+)\"$")
    public void setResourcesPath(final String resourcesPath) {

        getUtil().setResourcesPath(resourcesPath);
    }

    private Util getUtil() {

        return UtilHelper.getUtil();
    }

    private class ProcessReader implements Callable {

        private InputStream inputStream;

        public ProcessReader(InputStream inputStream) {

            this.inputStream = inputStream;
        }

        @Override
        public Object call() throws Exception {

            return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.toList());
        }
    }

    @Given("^I wait for \"(.+)\" milliseconds$")
    public boolean wait(final String time) {

        try {

            Thread.sleep(Integer.parseInt(time));
            return true;
        } catch (final InterruptedException e) {
            //TODO ADD LOGGER

            return false;
        }
    }
}
