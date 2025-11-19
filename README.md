Here is the complete, updated content for your `README.md`. You can copy and paste this directly into your file.

````markdown
# Saucedemo.com UI Automation Framework

This project contains **robust UI automation tests** for the e-commerce demo site **https://www.saucedemo.com**.

It goes beyond basic script execution by implementing **Enterprise-Grade Best Practices**, including Self-Healing Tests, Video Recording, Allure Reporting, and Data Consistency Checks.

The framework is built using:
-   **Java 17+**
-   **Selenium WebDriver 4**
-   **Cucumber 7 (BDD)**
-   **TestNG** (Runner & Assertions)
-   **Allure Report** (Visualization)
-   **Monte Screen Recorder** (Video Evidence)
-   **WebDriverManager**

The tests follow a strict **Page Object Model (POM)** with **Fluent Interface** design patterns.

---

## 📁 Project Structure

```text
src
│
├── main
│   └── java
│       ├── models             
│       │   └── ProductData.java
│       │
│       ├── pages              
│       │   ├── BasePage.java
│       │   ├── LoginPage.java
│       │   ├── ProductsPage.java
│       │   ├── CartPage.java
│       │   ├── CheckoutPage.java
│       │   └── CheckoutOverviewPage.java
│       │
│       └── utils
│           ├── ConfigReader.java
│           ├── DriverManager.java
│           └── VideoManager.java  
│
└── test
    ├── java
    │   ├── hooks
    │   │   └── Hooks.java         
    │   ├── runners
    │   │   └── RunCucumberTest.java
    │   └── stepdefinitions        
    │       ├── LoginSteps.java
    │       ├── CartSteps.java
    │       └── CheckoutSteps.java
    │
    └── resources
        ├── features
        │   └── checkout_validation.feature
        ├── allure.properties      
        └── config.properties
````

-----

## 🧪 Key Features & Best Practices

### **1. Smart "Stay-on-Page" Validation Loop**

Instead of restarting the browser for every negative test case, the framework navigates to the checkout page **once** and loops through multiple error combinations (Empty Name, Empty Zip, etc.) dynamically.

- **Benefit:** Reduces test execution time by 70%.
- **Resilience:** Includes "Self-Healing" logic to recover if a negative test accidentally proceeds to the next page.

### **2. Video Recording Strategy**

- **On Failure:** The test execution video is attached to the Allure Report for debugging.
- **On Success:** The video is automatically deleted to save disk space.
- **Tool:** Monte Media Library.

### **3. Strict Data Consistency (POJOs)**

Uses a custom `ProductData` object to "snapshot" the cart state (Name & Price) and compares it against the Checkout Overview page using an overridden `.equals()` method. This ensures 100% data integrity between screens.



-----

## 📋 Test Scenarios

### **1. Checkout Step One: Mandatory Field Validation**

**Type:** Data-Driven (Negative Testing)
**Logic:**

1.  Adds a product and proceeds to checkout.
2.  Iterates through a DataTable of invalid inputs (e.g., Missing Name, Missing Zip).
3.  Uses **Soft Assertions** to collect all errors without stopping execution.
4.  Verifies specific error messages for each field.

### **2. Checkout Step Two: End-to-End Validation**

**Type:** Functional / Data Integrity
**Logic:**

1.  Adds **2 Random Products** to the cart (ensures different data every run).
2.  Captures product details (Name/Price) from the Cart page.
3.  Completes checkout with valid dynamic data.
4.  **Verification:** deep compares the list of items on the "Overview" page against the captured "Cart" data to ensure no data loss or price changes.

-----

## 📊 Reporting (Allure)

This project uses **Allure** for interactive HTML reports containing:

- Step-by-step execution logs.
- **Screenshots** on failure.
- **Video recordings** on failure.

### **How to view the report:**

1.  Run the tests:
    ```bash
    mvn clean test
    ```
2.  Generate and serve the report:
    ```bash
    mvn allure:serve
    ```

-----

## ⚙️ Configuration

- **Browser:** Configured in `config.properties` (Default: Chrome).
- **URL:** https://www.saucedemo.com
- **Artifacts:** Videos and Allure results are stored in `target/` and excluded from Git via `.gitignore`.

-----

## ▶️ How to Run

**Run via Maven:**

```bash
mvn clean test
```

**Run via IntelliJ:**
Right-click `RunCucumberTest.java` and select **Run**.

```
```