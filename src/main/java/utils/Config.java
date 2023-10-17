package utils;

import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class Config {

    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Config instance = null;
    private static JsonPath config;
    private static List serversList;
    public static final int VERY_SHORT_WAIT = 1;
    public static final int SHORT_WAIT = 2;
    public static final int MEDIUM_WAIT = 5;
    public static final int DEFAULT_WAIT = 10;
    public static final int LONG_WAIT = 15;
    public static final int VERY_LONG_WAIT = 30;
    public static final int MAX_ATTEMPTS = 5;

    private JsonPath server;

    public Config() throws IOException {
        config = parseConfigData();
        setServersList();
        setDefaultServer();
    }

    /**
     * Explicitly instantiate singleton Config object if one does not exist
     *
     * @return Config object
     * @throws IOException exception
     */
    public static Config getInstance() throws IOException {

        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    /**
     * Set the servers list from (local-)config.yaml
     */
    private void setServersList() {

        logger.debug("Setting list of servers...");

        serversList = config.getList("servers");

    }

    /**
     * Parses config.yaml and returns LinkedHashMap of file
     *
     * @return LinkedHashMap
     * @throws IOException exception
     */
    private JsonPath parseConfigData() throws IOException {

        Gson gson = new Gson();
        Yaml yaml = new Yaml();
        LinkedHashMap configData;

        final String localFileName = "src/test/resources/local-config.yaml";
        final String defaultFileName = "src/test/resources/config.yaml";

        logger.debug("Locating local configuration file, \"{}\"...", localFileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(localFileName))) {

            logger.debug("Local configuration file, \"{}\" found. Parsing yaml file...", localFileName);
            configData = yaml.load(reader);

        } catch (IOException e) {

            logger.debug("Could not locate local configuration file \"{}\". " +
                    "Locating default configuration file, \"{}\"...", localFileName, defaultFileName);

            try (BufferedReader reader = new BufferedReader(new FileReader(defaultFileName))) {

                logger.debug("Default configuration file, \"{}\" found. Parsing yaml file...", defaultFileName);
                configData = yaml.load(reader);

            } catch (IOException ex) {

                logger.error("Could not locate default configuration file \"{}\"...", defaultFileName);
                throw new IOException(ex.getMessage());

            }

        }

        return JsonPath.given(gson.toJson(configData));

    }

    public String getServerUrl() {

        String serverUrl = Optional.ofNullable(this.server.getString("serverUrl")).orElseThrow(
                () -> new RuntimeException("\"serverUrl\" value is required but not found in configuration file!"));

        logger.debug("Server URL: {}", serverUrl);

        return serverUrl;

    }

    /**
     * Verify if server name is mapped in configuration file before setting it and trying to access its properties
     *
     * @param serverName String server nick name
     */
    private JsonPath validateServer(List serversList, String serverName) {

        logger.debug("Validating server config...");

        boolean isValid = false;
        HashMap serverMap = new HashMap();
        for (Object server : serversList) {

            serverMap = (HashMap) server;
            isValid = serverMap.containsKey(serverName);
            if (isValid) {

                logger.debug("Server \"{}\" located in configuration file...", serverName);
                break;

            }

        }

        if (!isValid) {

            throw new RuntimeException("The server \"" + serverName + "\" is NOT mapped in the configuration file!");

        }

        return Helper.convertObjectToJsonPath(serverMap.get(serverName));

    }

    /**
     * Set the default server for mapping server config either from command line or config file
     */
    public void setDefaultServer() {

        String server = System.getProperty( "server", "");

        if (server.isEmpty()) {

            logger.debug("Setting server from configuration file...");

            server = Optional.ofNullable(config.getString("server")).orElseThrow(
                    () -> new RuntimeException("\"server\" value is required but not found in configuration file!"));

        } else {

            logger.debug("Setting server from command line argument \"serverUrl\"...");

        }

        this.server = validateServer(serversList, server);

    }

    /**
     * Get API Key from config file or Gradle command line argument
     *
     * @return String api key
     */
    public String getApiKey() {

        String apiKey = System.getProperty( "apiKey", "");

        if (apiKey.isEmpty()) {

            logger.debug("Getting apiKey from configuration file...");

            apiKey = Optional.ofNullable(this.server.getString("apiKey")).orElseThrow(
                    () -> new RuntimeException("\"apiKey\" value is required but not found in configuration file!"));

        } else {

            logger.debug("Getting apiKey from command line argument \"apiKey\"...");

        }

        logger.debug("apiKey: {}", apiKey);

        return apiKey;

    }
}
