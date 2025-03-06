package steps;

import com.epam.training.alexandru_cruceat.data.TestDataProvider;
import com.epam.training.alexandru_cruceat.pages.InventoryPage;
import com.epam.training.alexandru_cruceat.pages.LoginPage;
import com.epam.training.alexandru_cruceat.utils.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginSteps {
    private static final Logger logger = LoggerFactory.getLogger(LoginSteps.class);
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private final TestDataProvider dataProvider = TestDataProvider.getInstance();

    @Before
    public void setup() {
        logger.info("Setting up test");
        loginPage = new LoginPage();
    }

    @After
    public void tearDown() {
        logger.info("Tearing down test");
        DriverManager.quitDriver();
    }

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        logger.info("Navigating to login page");
        loginPage.open();
        dataProvider.initializeDynamicData(loginPage);
    }

    @When("I enter empty credentials")
    public void iEnterEmptyCredentials() {
        logger.info("Entering empty credentials");
        loginPage.clearUsername();
        loginPage.clearPassword();
    }

    @When("I enter only username")
    public void iEnterOnlyUsername() {
        logger.info("Entering only username");
        String validUsername = dataProvider.getStringData("validUsername");
        loginPage.enterUsername(validUsername);
        loginPage.clearPassword();
    }

    @When("I enter valid credentials")
    public void iEnterValidCredentials() {
        logger.info("Entering valid credentials");
        String username = dataProvider.getStringData("validUsername");
        String password = dataProvider.getStringData("validPassword");
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @And("I click the login button")
    public void iClickTheLoginButton() {
        logger.info("Clicking login button");
        Object page = loginPage.clickLoginButton();
        if (page instanceof InventoryPage) {
            inventoryPage = (InventoryPage) page;
        }
    }

    @Then("I should see the error message {string}")
    public void iShouldSeeTheErrorMessage(String errorMessage) {
        logger.info("Verifying error message: {}", errorMessage);
        String actualError = loginPage.getErrorMessage();
        assertThat(actualError).contains(errorMessage);
    }

    @Then("I should be logged in successfully")
    public void iShouldBeLoggedInSuccessfully() {
        logger.info("Verifying successful login");
        assertThat(inventoryPage.isLoggedIn()).isTrue();
    }

    @And("I should see {string} in the dashboard")
    public void iShouldSeeInTheDashboard(String title) {
        logger.info("Verifying dashboard title: {}", title);
        String appLogo = inventoryPage.getAppLogoText();
        assertThat(appLogo).isEqualTo(title);
    }
}
