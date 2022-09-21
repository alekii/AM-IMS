package org.am.cucumber.test.glue;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        stepNotifications = true,
        plugin = {"pretty", "html:build/reports/cucumber-report.html", "json:build/reports/cucumber.json"},
        glue = {"org.am.cucumber.test.glue.stepdefinition"},
        features = {"src/test/resources/Features/Functional"},
        tags = "@Before"
)
public class TestRunner {

}
