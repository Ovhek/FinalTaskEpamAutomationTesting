package com.epam.training.alexandru_cruceat.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page object for the inventory page using Page Factory
 */
public class InventoryPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(InventoryPage.class);

    // Page Factory locators
    @FindBy(xpath = "//div[@class='app_logo']")
    private WebElement appLogo;

    @FindBy(xpath = "//div[@id='inventory_container']")
    private WebElement inventoryContainer;

    /**
     * Check if user is logged in
     * @return true if user is logged in
     */
    public boolean isLoggedIn() {
        logger.info("Checking if user is logged in");
        return isElementDisplayed(inventoryContainer) && isElementDisplayed(appLogo);
    }

    /**
     * Get app logo text
     * @return app logo text
     */
    public String getAppLogoText() {
        logger.info("Getting app logo text");
        return getText(appLogo);
    }
}
