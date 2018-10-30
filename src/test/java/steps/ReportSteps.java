package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import services.ParsingService;
import utilities.TestExecution;

public class ReportSteps {
    @Then("^report - append note \\((.*);(.*)\\)$")
    public void report_append_note(String key, String value) throws Throwable {
        String parsedValue = ParsingService.parseParam(value);
        TestExecution.testScenario.getNotes().put(key,parsedValue);
    }
}
