package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.ConfigReader;
import utils.DriverManager;
import pages.LoginPage;
import pages.ProductsPage;

public class Hooks {

    private static boolean isLoggedIn = false;
    private LoginPage loginPage;

    // This MUST run first (order = 0)
    @Before(order = 0)
    public void setUp() {
        DriverManager.initializeDriver();
        DriverManager.getDriver().manage().window().maximize();
        DriverManager.getDriver().get(ConfigReader.get("baseUrl"));
    }

    // Login only AFTER driver setup (order = 1)
    @Before(value = "@requiresLogin", order = 1)
    public void loginIfRequired() {

        if (!isLoggedIn) {
            loginPage = new LoginPage();

            String username = ConfigReader.get("username");
            String password = ConfigReader.get("password");

            loginPage.enterUsername(username);
            loginPage.enterPassword(password);
            loginPage.clickLogin();

            isLoggedIn = true;
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        DriverManager.quitDriver();
        isLoggedIn = false;
    }
}
