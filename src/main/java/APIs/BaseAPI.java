package APIs;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import services.SchemaFactory;
import utilities.Environment;
import utilities.Helper;
import utilities.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
//import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class BaseAPI {
    private String url;
    private Response response;
    private ContentType contentType;
    private RequestSpecification requestSpec;
    private Map<String, Object> pathParams;
    private Map<String, Object> queryParams;
    private Map<String, Object> formParams;
    private Map<String, Object> headers;
    private JSONObject requestBody;

    public BaseAPI() {
        this.headers = new HashMap<>();
        this.pathParams = new HashMap<>();
        this.queryParams = new HashMap<>();
        this.formParams = new HashMap<>();
        this.requestBody = new JSONObject();
        this.contentType = ContentType.JSON;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Response getResponse() {
        return this.response;
    }

    public void setEndpoint(String endpoint) {
        this.url = Environment.properties.getProperty("baseURL") + "/" + endpoint;
        Log.info("Endpoint is set to: " + this.url);
    }

    public void execute(String method) throws Exception {
        try {
            this.requestSpec = given()//.contentType(this.contentType.withCharset("UTF-8"))
                                        .headers(this.headers)
                                        .queryParams(this.queryParams)
                                        //.formParams(this.formParams)
                                        .pathParams(this.pathParams);
                                        //.body(requestBody.toJSONString());
            if (this.requestBody.isEmpty()) {this.requestSpec.formParams(this.formParams);}
            else {this.requestSpec.body(requestBody.toJSONString());}

            switch (method) {
                case "GET":
                    this.response = this.requestSpec.when().get(this.url);
                    Log.info(this.response.body().prettyPrint());
                    break;
                case "POST":
                    this.response = this.requestSpec.when().post(this.url);
                    Log.info(this.response.body().prettyPrint());
                    break;
                case "PUT":
                    this.response = this.requestSpec.when().put(this.url);
                    Log.info(this.response.body().prettyPrint());
                    break;
                default:
                    throw new Exception("Unsupported request method: '" + method + "'");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void checkStatus(int statusCode) {
        try{
            this.response.then().statusCode(statusCode);
        }
        catch (Throwable e){
            throw e;
        }
    }

    public void checkResponseContains(String key) {
        try{
            response.then().body("$",hasKey(key));
        }
        catch (Throwable e){
            throw e;
        }
    }

    public void addParam(String paramType, String key, int value) throws Exception {
        try {
            switch (paramType) {
                case "form":
                    this.addFormParam(key, value);
                    break;
                case "query":
                    this.addQueryParam(key, value);
                    break;
                case "path":
                    this.addPathParam(key, value);
                    break;
                case "header":
                    this.addHeader(key, value);
                    break;
                case "body":
                    this.addBodyParam(key,value);
                    break;
                default:
                    throw new Exception("Unsupported param type: '" + paramType + "'");
            }
        } catch (Throwable e) {
            throw e;
        }
    }

    public void addParam(String paramType, String key, String value) throws Exception {
        try {
            switch (paramType) {
                case "form":
                    this.addFormParam(key, value);
                    break;
                case "query":
                    this.addQueryParam(key, value);
                    break;
                case "path":
                    this.addPathParam(key, value);
                    break;
                case "header":
                    this.addHeader(key, value);
                    break;
                case "body":
                    this.addBodyParam(key,value);
                    break;
                default:
                    throw new Exception("Unsupported param type: '" + paramType + "'");
            }
        } catch (Throwable e) {
            throw e;
        }
    }

    public void addParam(String paramType, String key, boolean value) throws Exception {
        try {
            switch (paramType) {
                case "form":
                    this.addFormParam(key, value);
                    break;
                case "query":
                    this.addQueryParam(key, value);
                    break;
                case "path":
                    this.addPathParam(key, value);
                    break;
                case "header":
                    this.addHeader(key, value);
                    break;
                case "body":
                    this.addBodyParam(key,value);
                    break;
                default:
                    throw new Exception("Unsupported param type: '" + paramType + "'");
            }
        } catch (Throwable e) {
            throw e;
        }
    }

    private void addHeader(String key, boolean value) {
        try {
            this.headers.put(key, value);
        } catch (Throwable e) {
            throw e;
        }
    }

    private void addHeader(String key, int value) {
        try {
            this.headers.put(key, value);
        } catch (Throwable e) {
            throw e;
        }
    }

    private void addHeader(String key, String value) {
        try {
            this.headers.put(key, value);
        } catch (Throwable e) {
            throw e;
        }
    }

    private void addPathParam(String key, int value) {
        try {
            this.pathParams.put(key, value);
        } catch (Throwable e) {
            throw e;
        }
    }

    private void addPathParam(String key, String value) {
        try {
            this.pathParams.put(key, value);
        } catch (Throwable e) {
            throw e;
        }
    }

    private void addPathParam(String key, boolean value) {
        try {
            this.pathParams.put(key, value);
        } catch (Throwable e) {
            throw e;
        }
    }

    private void addQueryParam(String key, int value) {
        try {
            this.queryParams.put(key, value);
        } catch (Throwable e) {
            throw e;
        }
    }

    private void addQueryParam(String key, String value) {
        try {
            this.queryParams.put(key, value);
        } catch (Throwable e) {
            throw e;
        }
    }

    private void addQueryParam(String key, boolean value) {
        try {
            this.queryParams.put(key, value);
        } catch (Throwable e) {
            throw e;
        }
    }

    private void addFormParam(String key, int value) {
        try {
            this.formParams.put(key, value);
        } catch (Throwable e) {
            throw e;
        }
    }

    private void addFormParam(String key, String value) {
        try {
            this.formParams.put(key, value);
        } catch (Throwable e) {
            throw e;
        }
    }

    private void addFormParam(String key, boolean value) {
        try {
            this.formParams.put(key, value);
        } catch (Throwable e) {
            throw e;
        }
    }

    private void addBodyParam(String key, String value){
        try{
            this.requestBody.put(key,value);
        }
        catch (Throwable e){
            throw e;
        }
    }

    private void addBodyParam(String key, int value){
        try{
            this.requestBody.put(key,value);
        }
        catch (Throwable e){
            throw e;
        }
    }

    private void addBodyParam(String key, boolean value){
        try{
            this.requestBody.put(key,value);
        }
        catch (Throwable e){
            throw e;
        }
    }

    public boolean checkResponseValue(String dataType, String jsonPath, String operator, String value) throws Throwable {
        try {
            Object actual, expected;
            //string|boolean|int|long
            switch (dataType) {
                case "string":
                case "String":
                    actual = this.response.jsonPath().getString(jsonPath);
                    expected = value;
                    break;
                case "int":
                case "Int":
                case "integer":
                case "Integer":
                    actual = this.response.jsonPath().getInt(jsonPath);
                    expected = Integer.parseInt(value);
                    break;
                default:
                    throw new Throwable("Unsupported data type: " + dataType);

            }
            return Helper.compareByMatcher(actual,expected,operator);
        }
        catch (Throwable e){
            throw e;
        }
    }

    public void validateResponseAgainstSchema(String schema) {
        try {
//            this.response.then().body(matchesJsonSchemaInClasspath(SchemaFactory.get(schema)));
        }
        catch (Throwable e){
            throw e;
        }
    }

    public String getResponseValueAsString(String jsonPath){
        try{
            return response.jsonPath().getString(jsonPath);
        }
        catch (Throwable e){
            throw e;
        }
    }

    public List<Map<String,Object>> getResponseValueAsListMap(String jsonPath){
        try{
            return response.jsonPath().getList(jsonPath);
        }
        catch (Throwable e){
            throw e;
        }
    }

    public boolean verifyResponseValueEqual(String dataType, String jsonPath, String value) throws Throwable {
        try {
            switch (dataType) {
                case "string":
                case "String":
                    response.then().body(jsonPath,equalTo(value));
                    break;
                case "int":
                case "Int":
                case "integer":
                case "Integer":
                    int i = Integer.parseInt(value);
                    response.then().body(jsonPath, equalTo(i));
                    break;
                case "float":
                case "Float":
                    float f = Integer.parseInt(value);
                    response.then().body(jsonPath, equalTo(f));
                    break;
                case "long":
                case "Long":
                    long l = Integer.parseInt(value);
                    response.then().body(jsonPath, equalTo(l));
                    break;
                default:
                    throw new Throwable("Unsupported data type: " + dataType);

            }
            return true;
        }
        catch (Throwable e){
            throw e;
        }
    }

    public void test() throws Throwable {
        try {
            List<String> list = this.response.jsonPath().getList("data.Contracts.product_code");
            for (String s:list){
                Log.info("test test: " +s);
            }
        }
        catch (Throwable e){
            throw new Throwable(Helper.getMethodName(1) + " " + e.getMessage());
        }
    }

    public ResponseBody getResponseBody() {
        return this.response.body().prettyPeek();
    }
}
