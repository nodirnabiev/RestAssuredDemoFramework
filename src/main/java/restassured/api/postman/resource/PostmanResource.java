package restassured.api.postman.resource;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restassured.api.BaseRequest;
import restassured.api.RequestHeaders;
import restassured.api.postman.entity.PostRequestEntity;
import restassured.api.postman.entity.PutRequestEntity;
import utils.Helper;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import static io.restassured.RestAssured.given;

public class PostmanResource extends BaseRequest {

    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * Constructor
     *
     * @throws Exception exception
     */
    public PostmanResource() throws IOException {

    }

    public Response postmanGetRequest(RequestHeaders requestHeaders) {

        Response resp = given().log().all().
                headers(requestHeaders.getRequestHeaders()).
                queryParams("foo1", "bar1",
                        "foo2", "bar2").
                when().get(baseURI + "/" + BASE_PATH);

        return resp;

    }

    public Response postmanPostRequest(RequestHeaders requestHeaders, PostRequestEntity postRequestEntity) {

        logger.debug("Adding test data to post request...");

        String json = Helper.convertObjectToJsonString(postRequestEntity);

        Response resp = given().log().all().
                headers("Content-Type", "application/json").
                body(json).
                when().post(baseURI + "/" + "post");

        return resp;

    }

    public Response postmanPutRequest(RequestHeaders requestHeaders, PutRequestEntity putRequestEntity) {

        logger.debug("Updating test data to put request...");

        String json = Helper.convertObjectToJsonString(putRequestEntity);

        Response resp = given().log().all().
                headers("Content-Type", "application/json").
                body(json).
                when().put(baseURI + "/" + "put");

        return resp;

    }

}
