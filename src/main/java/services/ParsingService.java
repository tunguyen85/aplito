package services;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import utilities.Environment;
import utilities.Log;

public class ParsingService {
    public static final String GET_VAR = "?";
    public static final String GET_SETTING = "{";
    public static final String GET_PROPERTY = "|";
    public static final String GET_RESOURCE = "^";
    public static final String GET_TESTDATA = "@";
    public static final String EXEC_FUNCTION = "#";

    public static String parseParam(String param) throws Throwable {
        Log.info("Param to be parsed: " + param);
        //get the param operator (1st character in the input string)
        String operator = param.substring(0, 1);
        Log.info("Operator: " + operator);
        if (isOperator(operator)) {
            String value = param.substring(1);
            Log.info("Value: " + value);
            String result = parseParam(operator,value);
            Log.info("Result: " + result);
            return result;
        } else {   //plain text
            return param;
        }
    }

    private static String parseParam(String operator, String key) throws Throwable {
        try {
            switch (operator) {
                case GET_VAR:
                    return Environment.globalVar.get(key);
                case GET_PROPERTY:
                    return Environment.properties.getProperty(key);
                case GET_RESOURCE:
                    return Environment.resources.getProperty(key);
                case GET_SETTING:
                    return Environment.settings.getProperty(key);
                case GET_TESTDATA:
                    return Environment.testdata.getProperty(key);
                case EXEC_FUNCTION:
                    return execFunction(key);
                default:
                    throw new Throwable("Unsupported param operator: " + operator);
            }
        }
        catch (Throwable e){
            throw e;
        }
    }

    private static String execFunction(String functionName) throws Throwable {
        try {
            String function = StringUtils.substringBefore(functionName, "(");
            String param = StringUtils.substringBetween(functionName, "(", ")");
            int length = Integer.parseInt(param);
            switch (function) {
                case "random_number":
                    return RandomStringUtils.randomNumeric(length);
                case "random_alphabetic":
                    return RandomStringUtils.randomAlphabetic(length);
                case "random_alphanumeric":
                    return RandomStringUtils.randomAlphanumeric(length);
                default:
                    throw new Throwable("Unsupported function: " + function);
            }
        } catch (Throwable e) {
            throw e;
        }
    }

    private static boolean isOperator(String operator) {
        if (operator.equals(GET_VAR)
                || operator.equals(GET_PROPERTY)
                || operator.equals(GET_RESOURCE)
                || operator.equals(GET_SETTING)
                || operator.equals(GET_TESTDATA)
                || operator.equals(EXEC_FUNCTION)) {
            return true;
        }
        else return false;
    }
}
