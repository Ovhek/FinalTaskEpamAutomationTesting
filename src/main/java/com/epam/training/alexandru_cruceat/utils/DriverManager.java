package com.epam.training.alexandru_cruceat.utils;

import com.epam.training.alexandru_cruceat.config.DriverConfig;
import com.epam.training.alexandru_cruceat.factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Singleton pattern implementation for managing WebDriver instances
 */
public class DriverManager {
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverManager() {
        // Private constructor to prevent instantiation
    }

    /**
     * Get the WebDriver instance for the current thread
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {

            logger.info("Initializing WebDriver for thread: {}", Thread.currentThread().threadId());
            WebDriver driver = WebDriverFactory.createDriver(DriverConfig.getBrowser());
            driverThreadLocal.set(driver);
        }
        return driverThreadLocal.get();
    }

    /**
     * Quit the WebDriver instance for the current thread
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            logger.info("Quitting WebDriver for thread: {}", Thread.currentThread().threadId());
            driver.quit();
            driverThreadLocal.remove();
        }
    }
}
