package com.epam.training.alexandru_cruceat.pages;

import com.epam.training.alexandru_cruceat.utils.DriverManager;
import com.epam.training.alexandru_cruceat.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for all page objects using Page Factory
 */
public abstract  class BasePage {
    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected WebDriver driver;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(driver, this);
        logger.debug("Initialized page object: {}", this.getClass().getSimpleName());
    }

    /**
     * Get the page title
     * @return page title
     */
    public String getTitle() {
        return driver.getTitle();
    }

    /**
     * Navigate to a URL
     * @param url URL to navigate to
     */
    public void navigateTo(String url) {
        logger.info("Navigating to: {}", url);
        driver.get(url);
    }

    /**
     * Click on element with explicit wait
     * @param element element to click
     */
    protected void click(WebElement element) {
        logger.debug("Clicking on element: {}", element);
        WaitUtils.waitForElementClickable(driver, element).click();
    }

    /**
     * Type text into element
     * @param element element to type into
     * @param text text to type
     */
    protected void type(WebElement element, String text) {
        logger.debug("Typing '{}' into element: {}", text, element);
        WebElement visibleElement = WaitUtils.waitForElementVisible(driver, element);
        visibleElement.clear();
        visibleElement.sendKeys(text);
    }

    /**
     * Clear text from element
     * @param element element to clear
     */
    protected void clear(WebElement element) {
        logger.debug("Clearing text from element: {}", element);
        WaitUtils.waitForElementVisible(driver, element).clear();
    }

    /**
     * Get text from element
     * @param element element to get text from
     * @return element text
     */
    protected String getText(WebElement element) {
        return WaitUtils.waitForElementVisible(driver, element).getText();
    }

    /**
     * Check if element is displayed
     * @param element element to check
     * @return true if element is displayed
     */
    protected boolean isElementDisplayed(WebElement element) {
        try {
            return WaitUtils.waitForElementVisible(driver, element).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
