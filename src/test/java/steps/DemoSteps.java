package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utilities.Environment;
import utilities.Log;
import utilities.TestExecution;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class DemoSteps {
    @And("^log var \\((.*)\\)$")
    public void logVarOTP_DEMO(String var) throws Throwable {
        Log.info("abcbscbsabsabdsabdsa 123`13 " + Environment.globalVar.get(var));
    }

    @Given("^jsonplaceholder get users$")
    public void jsonplaceholderGetUsers() throws Throwable {
        Response response = given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .when().get("https://jsonplaceholder.typicode.com/users")
                .then().contentType(ContentType.JSON).extract().response();
        List<String> jsonResponse = response.jsonPath().getList("$");
        for (int i=0;i<jsonResponse.size();i++){
            Log.info(i + ": " + jsonResponse.get(i));
        }

    }

    @Then("^work with response$")
    public void workWithResponse() throws Throwable {
        try {
            TestExecution.api.test();
        }
        catch (Throwable e){
            Log.error(e.getMessage());
        }
    }

    @Given("^Get values from DB \\((.*)\\) \\((.*)\\) \\((.*)\\)$")
    public void test(String var1, String var2, String var3) throws Throwable {
        try{
            List<Map<String,Object>> result = Environment.dataService.queryRandomPairOfPrimaryPhoneAndContract();

            Log.info("contract_code: " + result.get(0).get("contract_code").toString());
            Environment.globalVar.put(var1,result.get(0).get("contract_code").toString());

            Log.info("CUID: " + result.get(0).get("CUID").toString());
            Environment.globalVar.put(var2,result.get(0).get("CUID").toString());

            Log.info("phone_number: " + result.get(0).get("phone_number").toString());
            Environment.globalVar.put(var3,result.get(0).get("phone_number").toString());
        }
        catch (Throwable e){
            Log.error(e.getMessage());
        }
    }
}
