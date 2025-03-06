package tests;

import com.epam.training.alexandru_cruceat.Constants.Constants;
import com.epam.training.alexandru_cruceat.data.TestDataProvider;
import com.epam.training.alexandru_cruceat.pages.InventoryPage;
import com.epam.training.alexandru_cruceat.pages.LoginPage;
import com.epam.training.alexandru_cruceat.utils.DriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Login Page Tests")
class LoginTest {
    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);
    private LoginPage loginPage;
    private final TestDataProvider dataProvider = TestDataProvider.getInstance();

    @BeforeEach
    void setUp() {
        logger.info("Setting up test");
        loginPage = new LoginPage();
        loginPage.open();

        // Initialize dynamic test data from login page
        dataProvider.initializeDynamicData(loginPage);
    }

    @AfterEach
    void tearDown() {
        logger.info("Tearing down test");
        DriverManager.quitDriver();
    }

    @Test
    @DisplayName("UC-1: Test Login with empty credentials")
    void testLoginWithEmptyCredentials() {
        logger.info("Running test: Login with empty credentials");

        // Click login
        loginPage.clearUsername();
        loginPage.clearPassword();
        loginPage.clickLoginButton();

        // Assert error message
        String errorMessage = loginPage.getErrorMessage();
        assertThat(errorMessage).contains(Constants.USERNAME_REQUIRED_ERROR);
    }

    @Test
    @DisplayName("UC-2: Test Login with username but empty password")
    void testLoginWithUsernameButEmptyPassword() {
        logger.info("Running test: Login with username but empty password");

        // Get a valid username from the page
        String validUsername = dataProvider.getStringData("validUsername");

        // Enter username but clear password
        loginPage.enterUsername(validUsername);
        loginPage.clearPassword();

        // Click login
        loginPage.clickLoginButton();

        // Assert error message
        String errorMessage = loginPage.getErrorMessage();
        assertThat(errorMessage).contains(Constants.PASSWORD_REQUIRED_ERROR);
    }

    @Test
    @DisplayName("UC-3: Test Login with valid credentials")
    void testLoginWithValidCredentials() {
        logger.info("Running test: Login with valid credentials");

        // Get valid credentials from the page
        String validUsername = dataProvider.getStringData("validUsername");
        String validPassword = dataProvider.getStringData("validPassword");

        // Login with valid credentials
        Object page = loginPage.login(validUsername, validPassword);

        // Assert successful login
        assertThat(page).isInstanceOf(InventoryPage.class);

        InventoryPage inventoryPage = (InventoryPage) page;
        assertThat(inventoryPage.isLoggedIn()).isTrue();
        assertThat(inventoryPage.getAppLogoText()).isEqualTo(Constants.DASHBOARD_TITLE);
    }

    @ParameterizedTest
    @MethodSource("provideValidUsernames")
    @DisplayName("Test Login with different valid users")
    void testLoginWithDifferentValidUsers(String username) {
        logger.info("Running test: Login with valid user: {}", username);

        // Get valid password from the page
        String validPassword = dataProvider.getStringData("validPassword");

        // Login with different valid users
        Object page = loginPage.login(username, validPassword);

        // Assert successful login
        assertThat(page).isInstanceOf(InventoryPage.class);

        InventoryPage inventoryPage = (InventoryPage) page;
        assertThat(inventoryPage.isLoggedIn()).isTrue();
    }

    static Stream<String> provideValidUsernames() {
        // This will be initialized at runtime when the test runs
        TestDataProvider dataProvider = TestDataProvider.getInstance();

        // If the data provider hasn't been initialized yet (during test discovery phase)
        if (dataProvider.getValidUsernames() == null) {
            // Return a default value for test discovery
            return Stream.of("standard_user");
        }

        return dataProvider.getValidUsernames().stream();
    }
}