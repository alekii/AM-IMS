import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        stepNotifications = true,
        plugin = {"pretty", "html:build/reports/cucumber-report.html", "json:build/reports/cucumber.json"},
        glue = {"org.am.cucumber.stepdefinition"},
        features = {"src/qa-test/resources/features/"},
        tags = {
                "@Before or "
                        + "@Warehouse_created or "
                        + "@Warehouse_updated"
        }
)
public class Functional {

}
