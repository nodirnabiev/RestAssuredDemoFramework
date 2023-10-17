package restassured.api.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Config;

import java.lang.invoke.MethodHandles;

public class BaseTest {

    private static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    protected static Config config;

    protected BaseTest() throws Exception {

        config = Config.getInstance();
    }
}
