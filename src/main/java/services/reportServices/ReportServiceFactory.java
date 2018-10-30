package services.reportServices;

import utilities.Log;

public class ReportServiceFactory {

    public static IReportService getReportService(String method) {
        switch (method.trim()) {
            case "html":
                return new HtmlReportService();
            case "jenkins":
                return new JenkinsReportService();
            default:
                Log.error("Does not support report method: " + method);
                return null;
        }

    }
}
