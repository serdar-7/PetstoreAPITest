package services;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import utils.PropertiesFile;
import model.*;


import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseService {

    private final RequestSpecBuilder defaultRequestSpecBuilder = new RequestSpecBuilder();
    private static final String BASE_URL = PropertiesFile.getProperty("baseURL");


    public BaseService() {
        configSetup();
    }

    public RequestSpecBuilder getDefaultRequestSpecBuilder() {
        return defaultRequestSpecBuilder;
    }


    /**
     * config method for setting uri, authentication and other headers
     **/
    private void configSetup() {
        getDefaultRequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .addHeader(PropertiesFile.getProperty("apikey"), PropertiesFile.getProperty("key"))
                .setAccept("application/json")
                .setContentType(ContentType.JSON);
    }


    /**
     * Execute HTTP POST method.
     *
     * @param body       request payload
     * @param parameters request query parameters
     * @param path       endpoint path
     * @return response as {@link Response}
     */
    public Response post(Object body, Map<String, String> parameters, String path) {
        RequestSpecBuilder requestSpecificationBuilder = new RequestSpecBuilder()
                .addRequestSpecification(getDefaultRequestSpecBuilder().build());
        setBody(body, requestSpecificationBuilder);
        setParameters(parameters, requestSpecificationBuilder);
        return given()
                .spec(requestSpecificationBuilder.build())
                .when()
                .post(path)
                .then()
                .extract()
                .response();
    }

    /**
     * Execute HTTP GET method.
     *
     * @param parameters request query parameters
     * @param path       endpoint path
     * @return response as {@link Response}
     */
    public Response get(Map<String, String> parameters, String path) {
        RequestSpecBuilder requestSpecificationBuilder = new RequestSpecBuilder()
                .addRequestSpecification(getDefaultRequestSpecBuilder().build());
        setParameters(parameters, requestSpecificationBuilder);
        return given()
                .spec(requestSpecificationBuilder.build())
                .when()
                .get(path)
                .then()
                .extract()
                .response();
    }

    /**
     * Execute HTTP PUT method.
     *
     * @param body       request payload
     * @param parameters request query parameters
     * @param path       endpoint path
     * @return response as {@link Response}
     */
    public Response put(Object body, Map<String, String> parameters, String path) {
        RequestSpecBuilder requestSpecificationBuilder = new RequestSpecBuilder()
                .addRequestSpecification(getDefaultRequestSpecBuilder().build());
        setBody(body, requestSpecificationBuilder);
        setParameters(parameters, requestSpecificationBuilder);
        return given()
                .spec(requestSpecificationBuilder.build())
                .when()
                .put(path)
                .then()
                .extract()
                .response();
    }

    /**
     * Execute HTTP DELETE method.
     *
     * @param parameters request query parameters
     * @param path       endpoint path
     * @return response as {@link Response}
     */
    public Response delete(Map<String, String> parameters, String path) {
        RequestSpecBuilder requestSpecificationBuilder = new RequestSpecBuilder()
                .addRequestSpecification(getDefaultRequestSpecBuilder().build());
        setParameters(parameters, requestSpecificationBuilder);
        return given()
                .spec(requestSpecificationBuilder.build())
                .when()
                .delete(path)
                .then()
                .extract()
                .response();
    }


    /**
     * Sets Body to Request Specification.
     *
     * @param body                        payload body
     * @param requestSpecificationBuilder request specification builder
     */
    private void setBody(Object body, RequestSpecBuilder requestSpecificationBuilder) {
        if (null != body) {
            requestSpecificationBuilder.setBody(body);
        }
    }

    /**
     * Set Query Params to Request Specification.
     *
     * @param parameters                  parameters
     * @param requestSpecificationBuilder request specification builder
     */
    private void setParameters(Map<String, String> parameters, RequestSpecBuilder requestSpecificationBuilder) {
        if (null != parameters) {
            requestSpecificationBuilder.addQueryParams(parameters);
        }
    }


    /**
     * Makes assertion for the response status code with the expected code
     *
     * @param response     returned response
     * @param expectedCode expected statsu code
     **/
    public void verifyResponseStatusValue(Response response, int expectedCode) {
        Assert.assertEquals("Response status code does not match with the expected code", response.getStatusCode(), expectedCode);
    }

    /**
     * Makes assertion for ApiResponse object message attribute
     *
     * @param response returned response
     * @param message  expected message
     **/
    public void validatApiResponseMessage(ApiResponse response, String message) {
        Assert.assertEquals("Response " + message + " attribute does not match with the id!", response.getMessage(), message);
    }
}
