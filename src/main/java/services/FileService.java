package services;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import sun.security.util.PendingException;
import utilities.Helper;
import utilities.Log;

import java.io.*;
import java.util.Properties;

public class FileService {
    public static void createHTMLFile(String content, String filename, String folder) throws Throwable {
        try {
            String saveToFileName = filename + "_" + Helper.getCurrentDate("ddMMyy_hhmmss") + ".html";
            String defaultFolder = Helper.getDefaultLocalFolder();
            String folderFullPathName = "";
            folderFullPathName = defaultFolder + folder + System.getProperty("file.separator");

            Log.info("Report folder: " + folderFullPathName);

            File file = new File(folderFullPathName);
            file.mkdirs();

            String newFilePath = folderFullPathName + saveToFileName;

            IOUtils.write(content, new FileOutputStream(newFilePath));

        } catch (Throwable e) {
            Log.error("EXCEPTION while processing File: " + e.getMessage());
            throw e;
        }
    }

    public static void createTestDataFile(String filename) throws IOException {
        try {
            String filenameFullPath = getTestDataFolder() + filename;
            Log.info("Full path to testdata file: " + filenameFullPath);
            File destFile = new File(filenameFullPath);
            if (!destFile.exists()) {
                //copy content from templates/testdata.properties to create a new testdata file
                String srcFilePath = Helper.getTemplatesFolder() + "testdata.properties";
                File srcFile = new File(srcFilePath);
                FileUtils.copyFile(srcFile, destFile);
            }

        } catch (Throwable e) {
            throw e;
        }
    }

    public static void updatePropertiesToFile(Properties prop, String filePath) throws IOException {
        try {
            Log.info("Full path to testdata file: " + filePath);
            File destFile = new File(filePath);
            OutputStream out = new FileOutputStream(destFile);
            prop.store(out, "File is updated at " + Helper.getCurrentDate("dd/MM/yyyy hh:mm:ss"));
            out.close();
        } catch (Throwable e) {
            throw e;
        }
    }

    public static void loadPropertiesFileToProp(String filePath, Properties prop) throws IOException {
        try {
            Log.info("Full path to properties file to be loaded: " + filePath);
            InputStream inputStream = new FileInputStream(filePath);
            prop.load(inputStream);
            Log.info("Properties file has been loaded successfully to prop.");
        } catch (Throwable e) {
            throw e;
        }
    }

    public static JSONObject getJsonFromFile(String filePath) {
        try {
            throw new PendingException();
        } catch (Throwable e) {
            throw e;
        }
    }

    public static String getTestDataFolder() {
        try {
            return Helper.getDefaultLocalFolder() + "testdata" + System.getProperty("file.separator");
        } catch (Throwable e) {
            throw e;
        }
    }
}
