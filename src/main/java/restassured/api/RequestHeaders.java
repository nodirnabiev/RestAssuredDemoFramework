package restassured.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;

/**
 * @author Nodir.Nabiev
 */
public class RequestHeaders {

    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private HashMap<String, String> requestHeadersMap = new HashMap<>();

    /**
     * Constructor for unauthenticated user
     *
     * @throws Exception exception
     */
    public RequestHeaders() throws Exception {
        setDefaultRequestHeaders();
    }

    /**
     * Set the unauthenticated, default request headers map
     *
     * @throws Exception exception
     */
    private void setDefaultRequestHeaders() {

        logger.debug("Getting the default request headers map...");

        HashMap<String, String> headersMap = new HashMap<>();
        headersMap.put("x-forwarded-proto", "https");
        headersMap.put("x-forwarded-port", "443");
        headersMap.put("content-length", "0");
        headersMap.put("host", "postman-echo.com");
        headersMap.put("user-agent", "PostmanRuntime/7.33.0");
        headersMap.put("accept-encoding", "gzip, deflate, br");

        logger.debug("default headers map: {}", headersMap);

        this.requestHeadersMap = headersMap;

    }

    /**
     * Add a request header to the instance map
     *
     * @param headerKey String
     * @param headerValue String
     */
    public void addRequestHeader(String headerKey, String headerValue) {

        logger.debug("Adding request header \"{}: {}\"...", headerKey, headerValue);

        this.requestHeadersMap.put(headerKey, headerValue);

    }

    /**
     * Get the authenticated request headers map
     *
     * @return HashMap String, String headers
     */
    public HashMap<String, String> getRequestHeaders() {

        logger.debug("Getting the authenticated request headers map: {}...", this.requestHeadersMap);

        return this.requestHeadersMap;

    }


}
