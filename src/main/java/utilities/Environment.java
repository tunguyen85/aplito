package utilities;

import services.FileService;
import services.dataServices.DataServiceFactory;
import services.dataServices.IDataService;
import services.reportServices.IReportService;
import services.reportServices.ReportServiceFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Environment {

    public static Properties properties;
    public static Properties settings;
    public static Properties resources;
    public static Properties testdata;
    public static JdbcSources dataSources;
    public static TestReport testReport;
    public static Map<String,String> globalVar;
    public static IReportService reportService;
    public static IDataService dataService;

    public void initialize() {
        try {
            //get environment properties
            generateProperties();
            //get settings
            generateSettings();
            //generate resources
            generateResources();
            //generate testdata
            generateTestdata();
            //generate jdbc templates
            generateJdbcSources();
            //init testReport
            testReport = new TestReport();
            //init reportService based on the setting
            generateReportService();
            //init dataService
            generateDataService();
            //init globalVar
            globalVar = new HashMap<String, String>();

        } catch (Throwable e) {
            Log.error(e.getMessage());
        }
    }

    private void generateTestdata() {
        try {
            String testdataFilename = properties.getProperty("testdata.filename");
            FileService.createTestDataFile(testdataFilename);
            this.testdata = new Properties();
            String testDataFolder = FileService.getTestDataFolder();
            Log.info("Testdata file: " + testDataFolder + testdataFilename);
            InputStream inputStream = new FileInputStream(testDataFolder + testdataFilename);
            testdata.load(inputStream);
            inputStream.close();
            Log.info("test data sample: " + testdata.getProperty("basic_auth"));

        } catch (Throwable e) {
            Log.error("Exception: " + e);
        }
    }

    private void generateDataService() {
        try{
            dataService = DataServiceFactory.getDataService(Environment.settings.getProperty("dataservice.type"));
        }
        catch (Throwable e){
            Log.error(e.getMessage());
        }
    }

    private void generateReportService() {
        try {
            reportService = ReportServiceFactory.getReportService(Environment.settings.getProperty("report.method"));
        }
        catch (Throwable e){
            Log.error(e.getMessage());
        }
    }

    private void generateJdbcSources() {
        dataSources = new JdbcSources(properties);
    }

    public void cleanup() {
        Log.info("Test finished!!!");
    }

    private void generateProperties() {
        InputStream inputStream = null;
        try {
            this.properties = new Properties();
            String propFileName = "environment.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            this.properties.load(inputStream);
            inputStream.close();

        } catch (Throwable e) {
            Log.error("Exception: " + e);
        }
    }

    private void generateSettings() {
        InputStream inputStream = null;
        try {
            this.settings = new Properties();
            String propFileName = "settings.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            this.settings.load(inputStream);
            inputStream.close();

        } catch (Throwable e) {
            Log.error("Exception: " + e);
        }
    }

    private void generateResources() {
        InputStream inputStream = null;
        try {
            this.resources = new Properties();
            String propFileName = "resources.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            this.resources.load(inputStream);
            inputStream.close();

        } catch (Throwable e) {
            Log.error("Exception: " + e);
        }
    }
}
