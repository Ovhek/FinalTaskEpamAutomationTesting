package com.epam.training.alexandru_cruceat.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

/**
 * Configuration for WebDriver settings
 */
public final class DriverConfig {
    private static final Logger logger = LoggerFactory.getLogger(DriverConfig.class);
    private static final Properties properties = new Properties();

    private static final String DEFAULT_BROWSER = "firefox";
    private static final Duration DEFAULT_IMPLICIT_WAIT_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration DEFAULT_EXPLICIT_WAIT_TIMEOUT = Duration.ofSeconds(15);
    private static final Duration DEFAULT_PAGE_LOAD_TIMEOUT = Duration.ofSeconds(30);
    private static final String DEFAULT_IS_HEADLESS = "false";
    public static final String DEFAULT_BROWSER_WINDOW_MAXIMIZE = "true";
    public static final String BASE_URL = "https://www.saucedemo.com/";

    static {
        try (InputStream input = DriverConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                logger.warn("config.properties file not found, using default values");
            } else {
                properties.load(input);
                logger.info("Loaded config.properties file");
            }
        } catch (IOException e) {
            logger.error("Error loading config.properties", e);
        }
    }

    /**
     * Get browser name from configuration or use default
     * @return browser name
     */
    public static String getBrowser() {
        return properties.getProperty("browser", DEFAULT_BROWSER);
    }

    /**
     * Get implicit wait timeout from configuration or use default
     * @return timeout in seconds
     */
    public static Duration getImplicitWaitTimeout() {
        try {
            return Duration.ofSeconds(
                    Long.parseLong(
                            properties.getProperty("implicit.wait.timeout", String.valueOf(DEFAULT_IMPLICIT_WAIT_TIMEOUT)
                            )
                    ));
        } catch (NumberFormatException e) {
            logger.warn("Invalid implicit wait timeout in config, using default", e);
            return DEFAULT_IMPLICIT_WAIT_TIMEOUT;
        }
    }

    /**
     * Get explicit wait timeout from configuration or use default
     * @return timeout in seconds
     */
    public static Duration getExplicitWaitTimeout() {
        try {
            return Duration.ofSeconds(
                    Long.parseLong(
                            properties.getProperty("explicit.wait.timeout", String.valueOf(DEFAULT_EXPLICIT_WAIT_TIMEOUT)
                            )
                    ));
        } catch (NumberFormatException e) {
            logger.warn("Invalid explicit wait timeout in config, using default", e);
            return DEFAULT_EXPLICIT_WAIT_TIMEOUT;
        }
    }

    /**
     * Get page load timeout from configuration or use default
     * @return timeout in seconds
     */
    public static Duration getPageLoadTimeout() {
        try {
            return Duration.ofSeconds(
                    Long.parseLong(
                            properties.getProperty("page.load.timeout", String.valueOf(DEFAULT_PAGE_LOAD_TIMEOUT)
                            )
                    ));
        } catch (NumberFormatException e) {
            logger.warn("Invalid page load timeout in config, using default", e);
            return DEFAULT_PAGE_LOAD_TIMEOUT;
        }
    }

    /**
     * Check if headless mode is enabled
     * @return true if headless mode is enabled
     */
    public static boolean isHeadless() {
        return Boolean.parseBoolean(properties.getProperty("headless", DEFAULT_IS_HEADLESS));
    }

    /**
     * Check if browser window should be maximized
     * @return true if browser window should be maximized
     */
    public static boolean shouldMaximizeWindow() {
        return Boolean.parseBoolean(properties.getProperty("browser.window.maximize", DEFAULT_BROWSER_WINDOW_MAXIMIZE));
    }

    /**
     * Get base URL from configuration or use default
     * @return base URL
     */
    public static String getBaseUrl() {
        return properties.getProperty("base.url", BASE_URL);
    }

}