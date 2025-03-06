package com.epam.training.alexandru_cruceat.utils;

import com.epam.training.alexandru_cruceat.config.DriverConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for explicit waits
 */
public class WaitUtils {
    private static final Logger logger = LoggerFactory.getLogger(WaitUtils.class);

    /**
     * Wait for an element to be visible
     * @param driver WebDriver instance
     * @param element WebElement to wait for
     * @return WebElement that is visible
     */
    public static WebElement waitForElementVisible(WebDriver driver, WebElement element) {
        logger.debug("Waiting for element to be visible: {}", element);
        WebDriverWait wait = new WebDriverWait(driver, DriverConfig.getExplicitWaitTimeout());
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait for an element to be clickable
     * @param driver WebDriver instance
     * @param element WebElement to wait for
     * @return WebElement that is clickable
     */
    public static WebElement waitForElementClickable(WebDriver driver, WebElement element) {
        logger.debug("Waiting for element to be clickable: {}", element);
        WebDriverWait wait = new WebDriverWait(driver, DriverConfig.getExplicitWaitTimeout());
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wait for text to be present in an element
     * @param driver WebDriver instance
     * @param element WebElement to check
     * @param text text to be present
     * @return true if text is present
     */
    public static boolean waitForTextPresent(WebDriver driver, WebElement element, String text) {
        logger.debug("Waiting for text to be present: {} in {}", text, element);
        WebDriverWait wait = new WebDriverWait(driver,  DriverConfig.getExplicitWaitTimeout());
        return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }
}
