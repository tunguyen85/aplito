import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import utilities.Environment;
import utilities.Log;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/Accounts",
        glue = {"steps"},
        tags = {"@login"},
        plugin = {"steps.Hook"},
        format = {"pretty",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "rerun:target/cucumber-reports/rerun.txt"
        })

public class RunnerTest {
    private static Environment environment;

    @BeforeClass
    public static void start() {
        Log.info("Start test...");
        environment = new Environment();
        environment.initialize();
    }

    @AfterClass
    public static void end() {
        Log.info("End test...");
        Environment.reportService.generateReport(Environment.testReport.toString());
    }

}
