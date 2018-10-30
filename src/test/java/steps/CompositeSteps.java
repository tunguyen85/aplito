package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.junit.Assert;
import services.ParsingService;
import enums.ERROR_CODE;
import utilities.Environment;
import utilities.Log;
import utilities.TestExecution;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CompositeSteps {

    @Given("^Login with \\((.*);(.*)\\) and get the access_token$")
    public void login_with_account(String un, String pw) throws Throwable {
        try{
            String baseUrl = Environment.properties.getProperty("baseURL");
            String endpoint = baseUrl + "/token";
            String username = ParsingService.parseParam(un);
            String password = ParsingService.parseParam(pw);
            Log.info("Username: " + username);
            Log.info("Password: " + password);
            Response response = given().header("Authorization",Environment.properties.getProperty("testdata.basic_auth"))
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("grant_type","password")
                    .formParam("username",username.trim())
                    .formParam("password",password.trim())
                    .formParam("login_type","direct")
                    .when().post(endpoint);
            Log.info(response.prettyPrint());
            String access_token = response.jsonPath().get("access_token");
            if (!access_token.equals("")){
                Log.info("Access token: " + access_token);
                Environment.globalVar.put("ACCESS_TOKEN","Bearer " + access_token);
            }
            else {
                throw new Throwable("Access token is empty!!!");
            }
        }
        catch (Throwable e){
            Log.error(e.getMessage());
            TestExecution.testScenario.setResult("FAILED");
            TestExecution.testScenario.setErrorCode(ERROR_CODE.E30.getErrorMessage());
            TestExecution.testScenario.setErrorMessage(e.getMessage());
            Assert.assertTrue(false);
        }
    }

    @And("^register account with \\((.*)\\) and \\((.*)\\)$")
    public void register_account(String phone, String contractNo) throws Throwable {
        try{
            String baseUrl = Environment.properties.getProperty("baseURL");
            //step 1
            String endpoint = baseUrl + "/api/customer/signup/verify";
            String phoneValue = ParsingService.parseParam(phone);
            String contractValue = ParsingService.parseParam(contractNo);
            Log.info("Phone for registration: " + phoneValue);
            Log.info("Contract number for registration: " + contractValue);
            JSONObject requestBody = new JSONObject();
            requestBody.put("PhoneNumber",phoneValue);
            requestBody.put("ContractNumber",contractValue);
            RequestSpecification request = given().header("Content-Type","application/json")
                                            .request().body(requestBody);
            Response response = request.post(endpoint);
            response.then().statusCode(200).body("response_code",is(0));
            Log.info("Registration - step 1: PASSED");
            //step 2
            endpoint += "/otp";
            String otp = Environment.dataService.getOTP(contractValue);
            requestBody.put("VerificationCode",otp);
            request = given().header("Content-Type","application/json")
                    .request().body(requestBody);
            response = request.post(endpoint);
            response.then().statusCode(200).body("response_code",is(0));
            Log.info("Registration - step 2: PASSED");
            //step 3
            endpoint = baseUrl + "/api/customer/signup";
            requestBody.put("Password",Environment.properties.getProperty("testdata.password"));
            request = given().header("Content-Type","application/json")
                    .request().body(requestBody);
            response = request.post(endpoint);
            response.then().statusCode(200).body("response_code",is(0));
            Log.info("Registration - step 2: PASSED \n" +
                    "Account has been registered: " + phoneValue);
        }
        catch (Throwable e){
            Log.error(e.getMessage());
            TestExecution.testScenario.setResult("FAILED");
            TestExecution.testScenario.setErrorCode(ERROR_CODE.E31.getErrorMessage());
            TestExecution.testScenario.setErrorMessage(e.getMessage());
            Assert.assertTrue(false);
        }
    }
}
