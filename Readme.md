# SauceDemo EPAM Test Automation

This project implements a test automation for the SauceDemo website (https://www.saucedemo.com/) using Selenium WebDriver with Page Factory, JUnit 5, AssertJ, and Cucumber with BDD approach.

## Task Description

The Project automates the following test cases:

1. **UC-1**: Test Login form with empty credentials
    - Type any credentials into "Username" and "Password" fields
    - Clear the inputs
    - Hit the "Login" button
    - Check the error message: "Username is required"

2. **UC-2**: Test Login form with credentials by passing Username
    - Type any credentials in username
    - Enter password
    - Clear the "Password" input
    - Hit the "Login" button
    - Check the error message: "Password is required"

3. **UC-3**: Test Login form with credentials by passing Username & Password
    - Extract valid usernames from the login page
    - Extract password from the login page
    - Login with valid credentials
    - Validate the title "Swag Labs" in the dashboard

## Technology Stack

- **Test Automation Tool**: Selenium WebDriver
- **Project Builder**: Maven
- **Browsers**: Firefox and Edge
- **Locators**: XPath
- **Test Runner**: JUnit 5
- **Test Automation Approach**: BDD with Cucumber
- **Assertions**: AssertJ
- **Loggers**: SLF4J with Logback

## Design Patterns Used

1. **Singleton Pattern**: Used for the WebDriver manager, configuration manager, and test data provider
2. **Factory Method Pattern**: Used for creating WebDriver instances
3. **Abstract Factory Pattern**: Used for creating browser-specific WebDriver factories
4. **Page Object Model**: Used for modeling web pages as objects
5. **Page Factory**: Used for initializing WebElements with @FindBy annotations

## Features

- **Parallel Test Execution**: Tests can run in parallel for faster execution
- **Cross-Browser Testing**: Supports both Firefox and Edge browsers
- **Data Provider**: Parameterized test data for reusability
- **Logging**: Comprehensive logging using SLF4J and Logback
- **Clean Architecture**: Modular design for maintainability and extensibility
- **Dynamic Test Data**: Extracts valid credentials from the login page at runtime

## Project Structure

- `src/main/java/com/saucedemo/config`: Configuration classes
- `src/main/java/com/saucedemo/constants`: Constants used throughout the project
- `src/main/java/com/saucedemo/factory`: WebDriver factory implementations
- `src/main/java/com/saucedemo/pages`: Page objects representing web pages
- `src/main/java/com/saucedemo/utils`: Utility classes for WebDriver and wait operations
- `src/main/java/com/saucedemo/data`: Test data provider
- `src/test/java/com/saucedemo/runners`: Cucumber test runners
- `src/test/java/com/saucedemo/steps`: Cucumber step definitions
- `src/test/java/com/saucedemo/tests`: JUnit 5 test classes
- `src/test/resources/features`: Cucumber feature files
- `src/test/resources/logback.xml`: Logging configuration
- `src/main/resources/config.properties`: Configuration properties

## How to Run

1. Clone the repository
2. Install Maven if not already installed
3. Run the JUnit 5 tests using the command: `mvn clean test -Dtest=*Test -DexcludedGroups=cucumber`
4. Run the Cucumber tests using the command: `mvn clean test -Dtest=TestRunner`
5. Run the Cucumber AND Junit 5 tests using the command: `mvn clean test`
# Configuration

The project is configurable through two separate configuration files:

## 1. `main/resources/config.properties`

This file contains WebDriver and test execution settings:

- `browser`: Specifies which browser to use for testing (options: firefox or edge)
- `headless`: Controls whether to run tests in headless mode without a visible browser UI (default: false)
- `implicit.wait.timeout`: Sets the implicit wait timeout in seconds, which is the time WebDriver will wait for elements to appear
- `explicit.wait.timeout`: Sets the explicit wait timeout in seconds, used when explicitly waiting for specific conditions
- `page.load.timeout`: Sets the maximum time in seconds allowed for page loads before timing out

## 2. `test/resources/junit-platform.properties`

This file configures test execution parameters:

- `junit.jupiter.execution.parallel.enabled=true`: Enables parallel execution of JUnit tests
- `junit.jupiter.execution.parallel.mode.default=concurrent`: Sets the default parallel execution mode to concurrent
- `junit.jupiter.execution.parallel.config.strategy=fixed`: Uses a fixed parallelism strategy for JUnit tests
- `junit.jupiter.execution.parallel.config.fixed.parallelism=6`: Sets the number of concurrent threads for JUnit test execution to 6
- `cucumber.publish.quiet=true`: Suppresses Cucumber publishing messages in the console
- `cucumber.plugin=pretty, html:target/cucumber-reports.html`: Configures Cucumber reporting plugins (console output and HTML report)
- `cucumber.execution.parallel.enabled=true`: Enables parallel execution of Cucumber scenarios
- `cucumber.execution.parallel.config.strategy=fixed`: Uses a fixed parallelism strategy for Cucumber execution
- `cucumber.execution.parallel.config.fixed.parallelism=6`: Sets the number of concurrent threads for Cucumber scenario execution to 6

## Reports

After running the tests, reports can be found in:
- Cucumber HTML Report: `target/cucumber-reports.html`
- JUni 5 Logs: `target/test-execution.log`
