Feature: Login and Product Search Automation

  Scenario: Login with valid credentials
    Given the user is on the login page
    When the user logs in with valid credentials
    Then the user should be on the products page

  @requiresLogin
  Scenario: Add an item to the cart
    Given the user is logged in
    When the user adds "Sauce Labs Onesie" to the cart
    Then the cart should contain 1 item

  @requiresLogin
  Scenario: Logout from the application
    Given the user is logged in
    When the user clicks on the menu button
    And the user selects Logout
    Then the user should be redirected to the login page

