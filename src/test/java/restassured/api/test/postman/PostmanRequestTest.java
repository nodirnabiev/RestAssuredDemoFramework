package restassured.api.test.postman;

import io.restassured.response.Response;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import restassured.api.RequestHeaders;
import restassured.api.postman.entity.PostRequestEntity;
import restassured.api.postman.entity.PutRequestEntity;
import restassured.api.postman.resource.PostmanResource;
import restassured.api.test.BaseTest;

import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

import static java.net.HttpURLConnection.HTTP_OK;

public class PostmanRequestTest extends BaseTest {

    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static TestData testData;

    private static class TestData {

        PostRequestEntity postRequestEntity = new PostRequestEntity();
        PutRequestEntity putRequestEntity = new PutRequestEntity();
        RequestHeaders requestHeaders = new RequestHeaders();
        PostmanResource postmanResource = new PostmanResource();

        TestData() throws Exception {}
    }



    protected PostmanRequestTest() throws Exception {
    }

    @BeforeMethod()
    public void logTestName(Method method) { logger.info("Running " + method.getName() + "()...");}

    @BeforeClass()
    public void testSetup(ITestContext context) throws Exception {

        testData = new TestData();

        logger.info("initialize test data..");

    }

    @Test(description = "Postman Get request")
    public void testPostmanGetRequest() {

        logger.info("Getting response and get request data");
        Response getRequestResponse = testData.postmanResource.postmanGetRequest(testData.requestHeaders);

        logger.info("Checking that call status is successful");
        Assert.assertEquals(getRequestResponse.statusCode(), HTTP_OK, "Status code is NOT \"" + HTTP_OK + "\"!");
        Assert.assertNotNull(getRequestResponse.jsonPath().get("args"), "Args object is not returned");
        //Assertion for query params
        Assert.assertEquals(getRequestResponse.jsonPath().get("args.foo1").toString(), "bar1");
        Assert.assertEquals(getRequestResponse.jsonPath().get("args.foo2").toString(), "bar2");

    }

    @Test(description = "Postman Post request")
    public void testPostmanPostRequest() {

        logger.info("Getting response and post request data");
        testData.requestHeaders.addRequestHeader("connection", "keep-alive");

        Response postRequestResponse = testData.postmanResource.
                postmanPostRequest(testData.requestHeaders, testData.postRequestEntity);

        logger.info("Checking that call status is successful");
        Assert.assertEquals(postRequestResponse.statusCode(), HTTP_OK, "Status code is NOT \"" + HTTP_OK + "\"!");
        Assert.assertNotNull(postRequestResponse.jsonPath().get("data"), "Data object is not returned");
        Assert.assertNotNull(postRequestResponse.jsonPath().get("json"), "Json object is not returned");

        Assert.assertEquals(postRequestResponse.jsonPath().get("data.testData").toString(), testData.postRequestEntity.getTestData());
        Assert.assertEquals(postRequestResponse.jsonPath().get("json.testData").toString(), testData.postRequestEntity.getTestData());
    }

    @Test(description = "Postman Put request")
    public void testPostmanPutRequest() {

        logger.info("Getting response and put request data");
        testData.requestHeaders.addRequestHeader("connection", "keep-alive");

        Response putRequestResponse = testData.postmanResource.
                postmanPutRequest(testData.requestHeaders, testData.putRequestEntity);

        logger.info("Checking that call status is successful");
        Assert.assertEquals(putRequestResponse.statusCode(), HTTP_OK, "Status code is NOT \"" + HTTP_OK + "\"!");
        Assert.assertNotNull(putRequestResponse.jsonPath().get("data"), "Data object is not returned");
        Assert.assertNotNull(putRequestResponse.jsonPath().get("json"), "Json object is not returned");

        Assert.assertEquals(putRequestResponse.jsonPath().get("data.testData").toString(), testData.putRequestEntity.getTestData());
        Assert.assertEquals(putRequestResponse.jsonPath().get("json.testData").toString(), testData.putRequestEntity.getTestData());
    }

}
