package steps;

import cucumber.api.java.en.And;
import org.junit.Assert;
import services.ParsingService;
import enums.ERROR_CODE;
import utilities.Log;
import utilities.TestExecution;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ContractsSteps {
    @And("^number of contracts is \\((.*)\\)$")
    public void verify_contracts_count(String countValue) throws Throwable {
        try{
            List<Map<String,Object>> contracts = TestExecution.api.getResponseValueAsListMap("data.Contracts");
            int count = Integer.parseInt(ParsingService.parseParam(countValue));
            Log.info("Expected contracts count: " + count);
            Log.info("Actual contracts count: " + contracts.size());
            Assert.assertEquals(contracts.size(),count);
        }
        catch (Throwable e){
            Log.error(e.getMessage());
            TestExecution.testScenario.setResult("FAILED");
            TestExecution.testScenario.setErrorCode(ERROR_CODE.E03.getErrorMessage());
            TestExecution.testScenario.setErrorMessage(e.getMessage());
            Assert.assertTrue(false);
        }
    }
}
