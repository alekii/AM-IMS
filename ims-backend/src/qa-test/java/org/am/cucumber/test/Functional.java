package org.am.cucumber.test;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                "src/qa-test/Features"
        },
        plugin = {
                "html:build/reports/cucumber",
                "json:build/reports/cucumber.json"
        },
        glue = {
                "org.am.cucumber.glue.stepdefinition"
        },
        stepNotifications = true,
        tags = "@Before or @WarehouseCreated"
)
public class Functional {

}
