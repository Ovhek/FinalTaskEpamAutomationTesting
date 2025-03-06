Feature: Login Functionality
  As a user
  I want to log in to the Sauce Demo website
  So that I can access the inventory

  Scenario: Login with empty credentials
    Given I am on the login page
    When I enter empty credentials
    And I click the login button
    Then I should see the error message "Username is required"

  Scenario: Login with username only
    Given I am on the login page
    When I enter only username
    And I click the login button
    Then I should see the error message "Password is required"

  Scenario: Login with valid credentials
    Given I am on the login page
    When I enter valid credentials
    And I click the login button
    Then I should be logged in successfully
    And I should see "Swag Labs" in the dashboard