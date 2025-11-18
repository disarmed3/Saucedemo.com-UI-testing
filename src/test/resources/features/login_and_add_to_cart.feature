Feature: Login and Product Search Automation

  Background:
    Given the user is on the login page
    And the user logs in with valid credentials

  Scenario: Checkout Step One Error Validation
    Given the user has added a product to the cart
    And the user is on the cart page
    When the user proceeds to checkout
    And the user submits the checkout form leaving required fields blank
    Then the user remains on the checkout information page
    And the system displays a clear error message indicating which required fields are missing


