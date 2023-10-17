package restassured.api;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Config;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import static io.restassured.RestAssured.given;

/**
 * @author Nodir.Nabiev
 */
public class BaseRequest {

    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    protected final String baseURI;
    protected static final String BASE_PATH = "get";
    //Not used with apiKey
    // public final String apiKey;

    /**
     * Constructor
     *
     * @throws Exception exception
     */
    public BaseRequest() throws IOException {

        Config config = Config.getInstance();
        this.baseURI = config.getServerUrl();
        // this.apiKey = config.getApiKey();
    }

    public Response getPostmanRequestHeaders(RequestHeaders requestHeaders) {

        requestHeaders.addRequestHeader("my-sample-header", "foo header");

        logger.debug("Getting postman request headers...");

        return given().
                headers(requestHeaders.getRequestHeaders()).
                when().get(baseURI + "/" + BASE_PATH);

    }

}
