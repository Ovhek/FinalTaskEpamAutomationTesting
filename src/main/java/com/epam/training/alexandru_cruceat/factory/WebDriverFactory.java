package com.epam.training.alexandru_cruceat.factory;


import com.epam.training.alexandru_cruceat.config.DriverConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * Factory class for Browser Types.
 */
public class WebDriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(WebDriverFactory.class);

    /**
     * Creates a WebDriver instance for the specified browser
     * @param browserType Type of browser to use
     * @return Configured WebDriver instance
     */
    public static WebDriver createDriver(String browserType) {
        WebDriver driver = switch (browserType.toLowerCase()) {
            case "edge" -> createEdgeDriver();
            case "chrome" -> createChromeDriver();
            default -> createFirefoxDriver();
        };

        configureDriver(driver);
        return driver;
    }

    /**
     * Applies common configuration to all WebDriver instances
     * @param driver WebDriver instance to configure
     */
    private static void configureDriver(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(DriverConfig.getImplicitWaitTimeout());
        driver.manage().timeouts().pageLoadTimeout(DriverConfig.getPageLoadTimeout());
        driver.manage().timeouts().scriptTimeout(DriverConfig.getExplicitWaitTimeout());
    }

    /**
     * Creates a ChromeDriver instance with default options
     * @return ChromeDriver instance
     */
    private static WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        applyCommonOptions(options);
        return new ChromeDriver(options);
    }

    /**
     * Creates a FirefoxDriver instance with default options
     * @return FirefoxDriver instance
     */
    private static WebDriver createFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        applyCommonOptions(options);
        return new FirefoxDriver(options);
    }

    /**
     * Creates a EdgeDriver instance with default options
     * @return EdgeDriver instance
     */
    private static WebDriver createEdgeDriver() {
        EdgeOptions options = new EdgeOptions();
        applyCommonOptions(options);
        return new EdgeDriver(options);
    }

    /**
     * Applies common configurations to browser options
     * @param options The browser options object (ChromeOptions, FirefoxOptions, EdgeOptions)
     * @param <T> The type of browser options
     * @return The configured options
     */
    private static <T> T applyCommonOptions(T options) {
        try {
            Method addArgumentsMethod = options.getClass().getMethod("addArguments", String[].class);

            List<String> arguments = new ArrayList<>();

            // Handle headless mode
            if (DriverConfig.isHeadless()) {
                arguments.add("--headless=new");
            }

            // Add common browser arguments
            if (DriverConfig.shouldMaximizeWindow()) {
                arguments.add("--start-maximized");
            }

            arguments.add("--disable-extensions");
            arguments.add("--disable-popup-blocking");
            arguments.add("--disable-infobars");

            // Apply all arguments at once
            addArgumentsMethod.invoke(options, (Object) arguments.toArray(new String[0]));

        } catch (Exception e) {
            logger.error("Failed to apply common options: {}", e.getMessage());
        }
        return options;
    }
}