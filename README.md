# Saucedemo.com UI Automation Framework

This project contains **robust UI automation tests** for the e-commerce demo site **https://www.saucedemo.com**.

It goes beyond basic script execution by implementing best practices, including Self-Healing Tests, Configurable Video Recording, Allure Reporting, and Data Consistency Checks.

The framework is built using:
-   **Java 17+**
-   **Selenium WebDriver 4**
-   **Cucumber 7 (BDD)**
-   **TestNG** (Runner & Assertions)
-   **Allure Report** (Visualization)
-   **Monte Screen Recorder** (Video Evidence)
-   **WebDriverManager**

The tests follow a strict **Page Object Model (POM)**.

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
│           └── DriverManager.java
│           
│
└── test
    ├── java
    │   ├── hooks
    │   │   └── Hooks.java         
    │   ├── runners
    │   │   └── RunCucumberTest.java
    │   │   └── RunParallelCucumberTest.java
    |   ├── utils
    |   |   └── VideoManager.java
    │   └── stepdefinitions        
    │       └── LoginAndCartSteps.java
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

The framework uses **Monte Media Library** for video recording. To optimize performance and disk usage, video behavior is fully configurable via the `video.mode` property.

- **`NONE` (Default):** No video recording. Best for fast parallel execution.
- **`FAILED_ONLY`:** Records the test execution but automatically deletes the video if the test passes. The video is kept and attached to the Allure report only if the test fails. Recommended only in sequential execution.
- **`ALWAYS`:** Records and keeps video evidence for every test, regardless of the result. Recommended only in sequential execution.

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
- **Video recordings** (based on configuration).

### **How to view the report:**

1.  Run the tests (see commands below).
2.  Generate and serve the report:
    ```bash
    mvn allure:serve
    ```

-----

## ⚙️ Configuration

- **Browser:** Firefox 
- **URL:** https://www.saucedemo.com
- **Artifacts:** Videos and Allure results are stored in `target/` and excluded from Git via `.gitignore`.

-----

## ▶️ How to Run

The framework supports **Parallel Execution** (for speed) and **Sequential Execution** (for video evidence/debugging).

| Mode | Description | Command (PowerShell) |
| :--- | :--- | :--- |
| **Speed Run** (Default) | **Parallel** (4 threads), No Video. Best for CI/CD and daily regression. | `mvn clean test` |
| **Smart Debug** | **Sequential**, Video on Fail. Best for fixing flaky tests. | `mvn clean test "-Dsequential" "-Dvideo.mode=FAILED_ONLY"` |
| **Evidence Run** | **Sequential**, Video Always. Best for demos or audits. | `mvn clean test "-Dsequential" "-Dvideo.mode=ALWAYS"` |
| **Safe Mode** | **Sequential**, No Video. Best for troubleshooting race conditions. | `mvn clean test "-Dsequential"` |

```
```