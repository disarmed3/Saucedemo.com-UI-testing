[README.md](https://github.com/user-attachments/files/23570774/README.md)
# Saucedemo.com UI Testing (Selenium + Cucumber + TestNG)

This project contains **UI automation tests** for the e-commerce demo
site **https://www.saucedemo.com**, focusing on login functionality,
adding products to the cart, and validating logout behavior.

The framework is built using:

-   **Selenium WebDriver**
-   **Cucumber (BDD)**
-   **TestNG**
-   **Java 17+**
-   **WebDriverManager**
-   **Firefox (GeckoDriver)**

The tests follow the **Page Object Model (POM)** for clean and
maintainable code.

------------------------------------------------------------------------

## 📁 Project Structure

    src
    │
    ├── main
    │   └── java
    │       ├── pages
    │       │   ├── BasePage.java
    │       │   ├── LoginPage.java
    │       │   └── ProductsPage.java
    │       │
    │       └── utils
    │           ├── ConfigReader.java
    │           └── DriverManager.java
    │
    └── test
        ├── java
        │   ├── hooks
        │   │   └── Hooks.java
        │   ├── runners
        │   │   └── RunCucumberTest.java
        │   └── stepdefinitions
        │       └── LoginAndCartSteps.java
        │
        └── resources
            ├── features
            │   └── login_and_add_to_cart.feature
            └── config.properties

------------------------------------------------------------------------

## 🧪 Test Scenarios (Detailed Breakdown)

### **1. Login With Valid Credentials**

Verifies that the user can log in using correct credentials and is
redirected to the products page.

------------------------------------------------------------------------

### **2. Add "Sauce Labs Onesie" to Cart**

Uses `@requiresLogin` to auto-login. Confirms the product can be added
and the cart badge updates to **1**.

------------------------------------------------------------------------

### **3. Logout From The Application**

Ensures the user can log out through the sidebar and is returned to the
login page.

------------------------------------------------------------------------

## 🧰 Browser Used: Firefox

Tests run on **Firefox** using WebDriverManager to initialize
GeckoDriver.

------------------------------------------------------------------------

## ▶️ How to Run the Tests

Run with Maven:

    mvn clean test

Or run `RunCucumberTest.java` directly from IntelliJ.

------------------------------------------------------------------------
