package services.reportServices;

import services.FileService;
import utilities.Log;

public class HtmlReportService implements IReportService {
    @Override
    public void generateReport(String content) {
        try {
            FileService.createHTMLFile(content, "testreport_", "ArchivedTestReport");
        } catch (Throwable e) {
            Log.error(e.getMessage());
        }
    }

}
