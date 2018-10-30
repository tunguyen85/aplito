package utilities;

import java.util.HashMap;
import java.util.Map;

public class TestScenario {
    private String id;
    private String description;
    private String result;
    private int executionTime;
    private String errorCode;
    private String errorMessage;
    private Map<String,String> notes;

    public TestScenario() {
        this.executionTime = 0;
        this.errorMessage = "";
        this.errorCode = "";
        this.notes = new HashMap<String,String>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getNotes() {
        return notes;
    }

    public void setNotes(HashMap<String, String> notes) {
        this.notes = notes;
    }
}
