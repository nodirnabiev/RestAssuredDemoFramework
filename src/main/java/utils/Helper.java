package utils;

import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class Helper {

    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Helper() {

    }

    /**
     * Transform an object to a JsonPath object
     *
     * @param object Object
     * @return JsonPath
     */
    public static JsonPath convertObjectToJsonPath(Object object) {

        logger.debug("Converting object to JsonPath object...");

        Gson gson = new Gson();
        String jsonString = gson.toJson(object);

        return new JsonPath(jsonString);

    }

    /**
     * Convert object to Json String
     *
     * @param object Object an entity
     * @return String
     */
    public static String convertObjectToJsonString(Object object) {

        logger.debug("Converting object to JSON String...");

        Gson gson = new Gson();
        String json = gson.toJson(object);

        logger.debug("json: {}", json);
        return json;

    }

}
