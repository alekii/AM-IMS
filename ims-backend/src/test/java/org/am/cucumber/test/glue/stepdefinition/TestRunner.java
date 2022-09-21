package org.am.cucumber.test.glue.stepdefinition;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty",
                "html:build/reports/cucumber-report.html",
                "json:build/reports/cucumber.json"},
        features = {"src/test/resources/Features/Functional/A00-Setup.feature"}
)
public class TestRunner {

}
