Feature: Checkout Step One and Two Information Validation

  Background:
    Given the user is on the login page
    And the user logs in with valid credentials

  Scenario: Checkout Step One Error Validation
    Given the user has added a product to the cart
    And the user is on the cart page
    When the user proceeds to checkout
    Then the user verifies the following mandatory field errors:
      | FirstName | LastName | Zip   | ErrorMessage                     |
      |           | Doe      | 12345 | Error: First Name is required    |
      | John      |          | 12345 | Error: Last Name is required     |
      | John      | Doe      |       | Error: Postal Code is required   |
      |           |          |       | Error: First Name is required    |


  Scenario: Checkout Step Two Page Validation
    Given the user has added two random products to the cart
    And the user is on the cart page
    When the user proceeds to checkout
    And fills the checkout form with valid data
    Then the user proceeds to checkout step two page
    And the checkout step two page should display the correct products and prices