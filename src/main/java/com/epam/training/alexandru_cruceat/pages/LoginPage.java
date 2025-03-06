package com.epam.training.alexandru_cruceat.pages;

import com.epam.training.alexandru_cruceat.config.DriverConfig;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Page object for the login page using Page Factory
 */
public class LoginPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

    // Page Factory locators
    @FindBy(xpath = "//input[@data-test='username']")
    private WebElement usernameInput;

    @FindBy(xpath = "//input[@data-test='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//input[@data-test='login-button']")
    private WebElement loginButton;

    @FindBy(xpath = "//h3[@data-test='error']")
    private WebElement errorMessage;

    @FindBy(xpath = "//div[@data-test='login-credentials']")
    private WebElement loginCredentials;

    @FindBy(xpath = "//div[@data-test='login-password']")
    private WebElement loginPassword;

    /**
     * Navigate to the login page
     * @return LoginPage instance
     */
    public LoginPage open() {
        logger.info("Opening login page");
        navigateTo(DriverConfig.getBaseUrl());
        return this;
    }

    /**
     * Enter username
     * @param username username to enter
     * @return LoginPage instance
     */
    public LoginPage enterUsername(String username) {
        logger.info("Entering username: {}", username);
        type(usernameInput, username);
        return this;
    }

    /**
     * Enter password
     * @param password password to enter
     * @return LoginPage instance
     */
    public LoginPage enterPassword(String password) {
        logger.info("Entering password");
        type(passwordInput, password);
        return this;
    }

    /**
     * Clear username
     * @return LoginPage instance
     */
    public LoginPage clearUsername() {
        logger.info("Clearing username");
        clear(usernameInput);
        return this;
    }

    /**
     * Clear password
     * @return LoginPage instance
     */
    public LoginPage clearPassword() {
        logger.info("Clearing password");
        clear(passwordInput);
        return this;
    }

    /**
     * Click login button
     * @return InventoryPage or LoginPage instance based on success
     */
    public Object clickLoginButton() {
        logger.info("Clicking login button");
        click(loginButton);

        // Check if error message is displayed
        try {
            if (errorMessage.isDisplayed()) {
                logger.info("Login failed, staying on LoginPage");
                return this;
            }
        } catch (Exception e) {
            // No error message, login successful
        }

        logger.info("Login successful, navigating to InventoryPage");
        return new InventoryPage();
    }

    /**
     * Get error message
     * @return error message text
     */
    public String getErrorMessage() {
        logger.info("Getting error message");
        return getText(errorMessage);
    }

    /**
     * Extract valid usernames from the login page
     * @return list of valid usernames
     */
    public List<String> getValidUsernames() {
        logger.info("Extracting valid usernames from login page");
        String credentialsText = getText(loginCredentials);
        List<String> usernames = new ArrayList<>();

        // Split by line break and skip the header
        String[] lines = credentialsText.split("\n");
        for (int i = 1; i < lines.length; i++) {
            if (!lines[i].trim().isEmpty()) {
                usernames.add(lines[i].trim());
                logger.debug("Found username: {}", lines[i].trim());
            }
        }

        return usernames;
    }

    /**
     * Extract valid password from the login page
     * @return valid password
     */
    public String getValidPassword() {
        logger.info("Extracting valid password from login page");
        String passwordText = getText(loginPassword);

        // Extract the password from the text
        //Searches for Password for all users and all white spaces and gets the text after that
        Pattern pattern = Pattern.compile("Password for all users:\\s*(.+)");
        Matcher matcher = pattern.matcher(passwordText);

        if (matcher.find()) {
            String password = matcher.group(1).trim();
            logger.debug("Found password: {}", password);
            return password;
        }

        logger.warn("Could not extract password from page");
        return "";
    }

    /**
     * Login with credentials
     * @param username username
     * @param password password
     * @return InventoryPage or LoginPage instance based on success
     */
    public Object login(String username, String password) {
        logger.info("Performing login with username: {}", username);
        enterUsername(username);
        enterPassword(password);
        return clickLoginButton();
    }
}
