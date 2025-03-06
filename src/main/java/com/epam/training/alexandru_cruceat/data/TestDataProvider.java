package com.epam.training.alexandru_cruceat.data;
import com.epam.training.alexandru_cruceat.pages.LoginPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data provider for test data
 */
public class TestDataProvider {
    private static final Logger logger = LoggerFactory.getLogger(TestDataProvider.class);
    private static TestDataProvider instance;

    private final Map<String, Object> dataMap = new HashMap<>();

    private TestDataProvider() {
        logger.info("Initializing TestDataProvider");
        initializeStaticData();
    }

    /**
     * Get singleton instance
     * @return TestDataProvider instance
     */
    public static synchronized TestDataProvider getInstance() {
        if (instance == null) {
            instance = new TestDataProvider();
        }
        return instance;
    }

    /**
     * Initialize static test data
     */
    private void initializeStaticData() {
        // Empty credentials
        dataMap.put("emptyUsername", "");
        dataMap.put("emptyPassword", "");
    }

    /**
     * Initialize dynamic test data from login page
     * @param loginPage the login page to extract data from
     */
    public void initializeDynamicData(LoginPage loginPage) {
        logger.info("Initializing dynamic test data from login page");

        // Extract usernames
        List<String> usernames = loginPage.getValidUsernames();
        if (!usernames.isEmpty()) {
            dataMap.put("validUsername", usernames.getFirst());
            dataMap.put("allValidUsernames", usernames);
            logger.info("Extracted {} valid usernames", usernames.size());
        }

        // Extract password
        String password = loginPage.getValidPassword();
        if (!password.isEmpty()) {
            dataMap.put("validPassword", password);
            logger.info("Extracted valid password");
        }
    }

    /**
     * Get test data
     * @param key data key
     * @return data value
     */
    public Object getData(String key) {
        logger.debug("Getting test data for key: {}", key);
        return dataMap.get(key);
    }

    /**
     * Get test data as string
     * @param key data key
     * @return data value as string
     */
    public String getStringData(String key) {
        return (String) getData(key);
    }

    /**
     * Get valid usernames
     * @return list of valid usernames
     */
    @SuppressWarnings("unchecked")
    public List<String> getValidUsernames() {
        return (List<String>) getData("allValidUsernames");
    }
}