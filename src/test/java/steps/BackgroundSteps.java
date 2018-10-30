package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import enums.CONTRACT_STATUS;
import org.junit.Assert;
import services.FileService;
import services.ParsingService;
import enums.ERROR_CODE;
import utilities.Environment;
import utilities.Log;
import utilities.TestExecution;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

public class BackgroundSteps {
    @Given("^query for a pair of contract number and phone number to register account - assign to \\((.*)\\) and \\((.*)\\)$")
    public void get_info_for_registration(String varContractNo, String varPhoneNo) throws Throwable {
        //just sample code
        Environment.globalVar.put("REG_CONTRACT", "3802837276");
        Environment.globalVar.put("REG_PHONE", "0789002937");
    }

    @Given("^query for OTP to register account with contract number = \\((.*)\\) and assign to global var \\((.*)\\)$")
    public void query_OTP_for_registration(String contractValue, String varOTP) throws Throwable {
        try {
            String contractNo = ParsingService.parseParam(contractValue);
            Log.info("contractNo: " + contractNo);
            String otp = Environment.dataService.getOTP(contractNo);
            Log.info("OTP to register account: " + otp);
            Environment.globalVar.put(varOTP, otp);
        } catch (Throwable e) {
            Log.error(e.getMessage());
            TestExecution.testScenario.setResult("FAILED");
            TestExecution.testScenario.setErrorCode(ERROR_CODE.E20.getErrorMessage());
            TestExecution.testScenario.setErrorMessage(e.getMessage());
            Assert.assertTrue(false);
        }

    }

    @Given("^query for client that has \\((.*)\\) \\((.*)\\) contract, and extract data to \\((.*)\\) and \\((.*)\\)$")
    public void query_client_info_that_has_active_cel_contract(String contractStatus, String contractType, String var_phone, String var_contract) throws Throwable {
        try {
            //get contractStatus code
            String statusCode = CONTRACT_STATUS.valueOf(contractStatus).getStatusCode();
            //get contractType
            String type = "";
            if (contractType.equalsIgnoreCase("cel")){ type = "CEL"; }
            else if (contractType.equalsIgnoreCase("rel")){ type = "REL"; }
            else { throw new Throwable("Contract type '" + contractType + "' is invalid.");}
            //query for client that matches the given criteria
            boolean isPhoneExisted = true;
            String phone = "", contract = "";
            while (isPhoneExisted) {
                contract = Environment.dataService.getContractByStatusAndType(statusCode, type);
                phone = Environment.dataService.getPrimaryPhoneByContractNo(contract);
                Log.info("Phone: " + phone);
                Log.info("Contract no: " + contract);
                isPhoneExisted = Environment.dataService.isAccountExisted(phone);   //this will also return true
                                                                                    // in case phone is empty
                Log.info("Account '" + phone + "' " + (isPhoneExisted?"already existed and cannot be registered again." :"does not exist and can be used to register."));
            }
            Environment.globalVar.put(var_phone, phone);
            Environment.globalVar.put(var_contract, contract);
        } catch (Throwable e) {
            Log.error(e.getMessage());
            TestExecution.testScenario.setResult("FAILED");
            TestExecution.testScenario.setErrorCode(ERROR_CODE.E10.getErrorMessage());
            TestExecution.testScenario.setErrorMessage(e.getMessage());
            Assert.assertTrue(false);
        }
    }

    @Then("^add to test data \\((.*);(.*)\\)$")
    public void add_test_data(String key, String value) throws Throwable {
        try {
            String finalValue = ParsingService.parseParam(value);
            if (Environment.testdata.containsKey(key)){     //check if key already existed, then update its value
                Environment.testdata.setProperty(key, finalValue);
            }
            else {          //if key does not exist, then put it to properties
                Environment.testdata.put(key,finalValue);
            }
            Log.info("Test data updated: (" + key + ":" + Environment.testdata.getProperty(key) + ")");
        } catch (Throwable e) {
            Log.error(e.getMessage());
            TestExecution.testScenario.setResult("FAILED");
            TestExecution.testScenario.setErrorCode(ERROR_CODE.E98.getErrorMessage());
            TestExecution.testScenario.setErrorMessage(e.getMessage());
            Assert.assertTrue(false);

        }
    }

    @And("^update test data file and reload it to Environment.testdata$")
    public void updateTestDataFile() throws Throwable {
        try{
            String testdataFilename = FileService.getTestDataFolder() + Environment.properties.getProperty("testdata.filename");
            FileService.updatePropertiesToFile(Environment.testdata,testdataFilename);
            FileService.loadPropertiesFileToProp(testdataFilename,Environment.testdata);
            TestExecution.testScenario.setResult("PASSED");
        }
        catch (Throwable e){
            Log.error(e.getMessage());
            TestExecution.testScenario.setResult("FAILED");
            TestExecution.testScenario.setErrorCode(ERROR_CODE.E98.getErrorMessage());
            TestExecution.testScenario.setErrorMessage(e.getMessage());
            Assert.assertTrue(false);
        }
    }

    @Given("^get and store contract info not registered \\((.*)\\) \\((.*)\\) contract, and extract data to \\((.*)\\) \\((.*)\\) \\((.*)\\)$")
    public void getContractInfoNotRegistered(String contractStatus, String contractType, String phoneNo, String contractNo, String cuid) throws Throwable {
        try{
            String statusCode = CONTRACT_STATUS.valueOf(contractStatus).getStatusCode();
            Map<String, Object> map = Environment.dataService.getInfoContractNotRegistered(statusCode, contractType);
            Environment.testdata.put(phoneNo,map.get("phone"));
            Environment.testdata.put(contractNo,map.get("contract"));
            Environment.testdata.put(cuid,map.get("cuid"));
        }catch (Throwable e){
            Log.error(e.getMessage());
            TestExecution.testScenario.setResult("FAILED");
            TestExecution.testScenario.setErrorCode(ERROR_CODE.E98.getErrorMessage());
            TestExecution.testScenario.setErrorMessage(e.getMessage());
            Assert.assertTrue(false);
        }
    }
}
