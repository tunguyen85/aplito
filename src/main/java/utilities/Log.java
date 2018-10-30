package utilities;

import org.apache.log4j.Logger;

public class Log {
    //Initialize Log4j instance
    private static Logger Log = Logger.getLogger(Log.class.getName());

    //We can use it when starting tests
    public static void startTest() {
        Log.info("Test is starting...");

    }

    //We can use it when ending tests
    public static void endTest() {
        Log.info("End test!!!");
        Log.info("Test result: |" +
                TestExecution.testScenario.getId() + "|" +
                TestExecution.testScenario.getResult() + "|" +
                TestExecution.testScenario.getExecutionTime() + "|" +
                TestExecution.testScenario.getErrorCode() + "|" +
                TestExecution.testScenario.getErrorMessage());
    }

    //Info Level Logs
    public static void info(String message) {
        Log.info("<" + Helper.getMethodName(1) + "> " + message);
    }

    //Warn Level Logs
    public static void warn(String message) {
        Log.warn("<" + Helper.getMethodName(1) + "> " + message);
    }

    //Error Level Logs
    public static void error(String message) {
        Log.error("<" + Helper.getMethodName(1) + "> " + message);
    }

    //Fatal Level Logs
    public static void fatal(String message) {
        Log.fatal("<" + Helper.getMethodName(1) + "> " + message);
    }

    //Debug Level Logs
    public static void debug(String message) {
        Log.debug("<" + Helper.getMethodName(1) + "> " + message);
    }

}
