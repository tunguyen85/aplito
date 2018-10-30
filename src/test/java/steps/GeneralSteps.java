package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import enums.ERROR_CODE;
import org.junit.Assert;
import services.ParsingService;
import utilities.Environment;
import utilities.Log;
import utilities.TestExecution;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GeneralSteps {
    /** [Given] block
     * */
    @Given("^Testcase-ID \\((.*)\\) - Testcase description \\((.*)\\)$")
    public void set_testcase_ID(String testcaseID, String testcaseDesc) throws Throwable {
        TestExecution.testScenario.setId(testcaseID);
        TestExecution.testScenario.setDescription(testcaseDesc);
        Log.info("Testcase ID: " + TestExecution.testScenario.getId() + " - Testcase description: " + TestExecution.testScenario.getDescription());
    }

    @Given("^endpoint \\((.*)\\)$")
    public void set_endpoint(String endpoint) throws Throwable {
        String parsedValue = ParsingService.parseParam(endpoint);
        TestExecution.api.setEndpoint(parsedValue);
        Log.info("Endpoint is set: " + TestExecution.api.getUrl());
    }

    @Given("^param - integer \\((.*);(.*);(.*)\\)$")
    public void add_param_integer(String paramType, String key, int value) throws Throwable {
        TestExecution.api.addParam(paramType,key,value);
        Log.info("Add param: " + paramType + " - " + key + ":" + value);
    }

    @Given("^param - string \\((.*);(.*);(.*)\\)$")
    public void add_param_string(String paramType, String key, String value) throws Throwable {
        String parsedValue = ParsingService.parseParam(value);
        TestExecution.api.addParam(paramType,key, parsedValue);
        Log.info("Add param: " + paramType + " - " + key + ":" + parsedValue);
    }

    @Given("^param - boolean \\((.*);(.*);(.*)\\)$")
    public void add_param_boolean(String paramType, String key, boolean value) throws Throwable {
        TestExecution.api.addParam(paramType,key,value);
        Log.info("Add param: " + paramType + " - " + key + ":" + value);
    }

    /** [When] block
     * */
    @When("^execute \\((.*)\\)$")
    public void execute_method(String method) throws Throwable {
        try{
            TestExecution.api.execute(method);
        }
        catch (Throwable e){
            Log.error(e.getMessage());
            TestExecution.testScenario.setResult("FAILED");
            TestExecution.testScenario.setErrorCode(ERROR_CODE.E99.getErrorMessage());
            TestExecution.testScenario.setErrorMessage(e.getMessage());
            Assert.assertTrue(false);
        }
    }

    /** [Then] block
     * */
    @Then("^status \\((\\d+)\\)$")
    public void status(int status) throws Throwable {
        try{
            TestExecution.api.checkStatus(status);
            TestExecution.testScenario.setResult("PASSED");
        }
        catch (Throwable e){
            Log.error(e.getMessage());
            TestExecution.testScenario.setResult("FAILED");
            TestExecution.testScenario.setErrorCode(ERROR_CODE.E01.getErrorMessage());
            TestExecution.testScenario.setErrorMessage(e.getMessage());
            Assert.assertTrue(false);
        }
    }

    @Then("^verify response matches \\((.*);(.*);(.*);(.*)\\)$")
    public void check_response_matches(String dataType, String jsonPath, String operator, String value) throws Throwable {
        try{
            boolean result = TestExecution.api.checkResponseValue(dataType, jsonPath,operator,value);
            if (result) {
                TestExecution.testScenario.setResult("PASSED");
            } else {
                throw new Throwable("Response key: [" + jsonPath + "] is not " + operator + " " + value);
            }

        }
        catch (Throwable e){
            Log.error(e.getMessage());
            TestExecution.testScenario.setResult("FAILED");
            TestExecution.testScenario.setErrorCode(ERROR_CODE.E05.getErrorMessage());
            TestExecution.testScenario.setErrorMessage(e.getMessage());
            Assert.assertTrue(false);
        }
    }

    @Then("^response contains \\((.*)\\)$")
    public void check_response_contains_key(String key) throws Throwable {
        try{
            TestExecution.api.checkResponseContains(key.trim());
            TestExecution.testScenario.setResult("PASSED");
        }
        catch (Throwable e){
            Log.error(e.getMessage());
            TestExecution.testScenario.setResult("FAILED");
            TestExecution.testScenario.setErrorCode(ERROR_CODE.E03.getErrorMessage());
            TestExecution.testScenario.setErrorMessage(e.getMessage());
            Assert.assertTrue(false);
        }
    }

    @Then("^extract response value \\((.*)\\) to \\((.*)\\)$")
    public void extract_response_value_to_global_var(String jsonPath, String varName) throws Throwable {
        try{
            String value = TestExecution.api.getResponseValueAsString(jsonPath.trim());
            if (value != null && !value.equals("")) {
                Environment.globalVar.put(varName, value);
                Log.info("Global var: " + varName + " - value: " + value);
            }
            else
                throw new Throwable("Cannot extract the response value at path: " + jsonPath);

        }
        catch (Throwable e){
            Log.error(e.getMessage());
            TestExecution.testScenario.setResult("FAILED");
            TestExecution.testScenario.setErrorCode(ERROR_CODE.E05.getErrorMessage());
            TestExecution.testScenario.setErrorMessage(e.getMessage());
            Assert.assertTrue(false);
        }
    }

    @Then("^verify response value equals \\((.*);(.*);(.*)\\)$")
    public void verify_response_value_equal(String dataType, String jsonPath, String value) throws Throwable {
        try{
            String parsedValue = ParsingService.parseParam(value);
            boolean result = TestExecution.api.verifyResponseValueEqual(dataType.trim(),jsonPath.trim(),parsedValue.trim());
        }
        catch (Throwable e){
            Log.error(e.getMessage());
            TestExecution.testScenario.setResult("FAILED");
            TestExecution.testScenario.setErrorCode(ERROR_CODE.E05.getErrorMessage());
            TestExecution.testScenario.setErrorMessage(e.getMessage());
            Assert.assertTrue(false);
        }
    }

    @Then("^extract response \\((.*)\\) to model \\((.*)\\)$")
    public void extract_response_to_model(String jsonPath, String model) throws Throwable {
        try{
            TestExecution.api.test();

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
