package restassured.api.postman.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class PostRequestEntity {

    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private String testData;

    public PostRequestEntity() {

        this.testData = "This is expected to be sent back as part of response body for put request";
    }

    /**
     * Get test data
     *
     * @return String
     */
    public String getTestData() {

        logger.debug("Getting test data \"{}\"", this.testData);
        return this.testData;
    }

    /**
     * Set test data
     *
     * @param testData String
     */
    public void setTestData(String testData) {

        logger.debug("Setting test data to \"{}\"", testData);
        this.testData = testData;
    }
}
