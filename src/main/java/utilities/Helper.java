package utilities;

import org.hamcrest.Matchers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Helper {
    public static String getCurrentDate(String format) {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(dt);
    }

    /**
     * Get the method name for a depth in call stack. <br />
     * Utility function
     *
     * @param depth depth in the call stack (0 means current method, 1 means call method, ...)
     * @return method name
     */
    public static String getMethodName(final int depth) {
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        return ste[2 + depth].getMethodName();
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().startsWith("windows");
    }

    public static String getDefaultLocalFolder() {
        return System.getProperty("user.dir") + System.getProperty("file.separator") + "apilato" + System.getProperty("file.separator");
    }

    public static String getTemplatesFolder(){
        return System.getProperty("user.dir") + System.getProperty("file.separator")
                + "target" + System.getProperty("file.separator")
                + "classes" + System.getProperty("file.separator")
                + "templates" + System.getProperty("file.separator");
    }

    public static boolean compareByMatcher(Object actual, Object expected, String operator) throws Throwable {
        try{
            int actualValue, expectedValue;
            String originalString, subString;
            switch (operator){
                case "=":
                    assertThat(actual,equalTo(expected));
                    break;
                case ">":
                    actualValue = (int) actual;
                    expectedValue = (int) expected;
                    assertThat(actualValue,greaterThan(expectedValue));
                    break;
                case ">=":
                    actualValue = (int) actual;
                    expectedValue = (int) expected;
                    assertThat(actualValue,greaterThanOrEqualTo(expectedValue));
                    break;
                case "<":
                    actualValue = (int) actual;
                    expectedValue = (int) expected;
                    assertThat(actualValue,lessThan(expectedValue));
                    break;
                case "<=":
                    actualValue = (int) actual;
                    expectedValue = (int) expected;
                    assertThat(actualValue,lessThanOrEqualTo(expectedValue));
                    break;
                case "!=":

                    break;
                case "contains":
                case "has":
                    originalString = actual.toString();
                    subString = expected.toString();
                    assertThat(originalString,containsString(subString));
                    break;
                default:
                    throw new Throwable("Unsupported operator: " + operator);
            }
            return true;
        }
        catch (Throwable e){
            throw e;
        }

    }
}
