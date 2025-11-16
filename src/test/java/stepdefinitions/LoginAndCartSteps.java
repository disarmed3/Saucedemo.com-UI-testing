package stepdefinitions;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.LoginPage;
import pages.ProductsPage;
import utils.ConfigReader;
import utils.DriverManager;

public class LoginAndCartSteps {

    private LoginPage loginPage;
    private ProductsPage productsPage;

    @Given("the user is on the login page")
    public void theUserIsOnTheLoginPage() {
        loginPage = new LoginPage(); // opens login page
    }

    @When("the user logs in with valid credentials")
    public void theUserLogsInWithValidCredentials() {
        String username = ConfigReader.get("username");
        String password = ConfigReader.get("password");

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        productsPage = loginPage.clickLogin();
    }

    @Then("the user should be on the products page")
    public void theUserShouldBeOnTheProductsPage() {
        // Use getter from BasePage instead of accessing driver directly
        String currentUrl = productsPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory.html"),
                "Expected to be on products page");
    }


    @Given("the user is logged in")
    public void theUserIsLoggedIn() {
        // At this point, the @Before("@requiresLogin") hook already performed login.
        // Ensure productsPage is initialized for further actions
        loginPage = new LoginPage(); // for getCurrentUrl if needed
        productsPage = new ProductsPage();

        // We can optionally verify the login state by checking the current URL
        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        if (!currentUrl.contains("inventory.html")) {
            throw new IllegalStateException("User is not logged in, something went wrong in the login hook.");
        }
    }

    @When("the user adds {string} to the cart")
    public void theUserAddsProductToTheCart(String productName) {
        // For simplicity, we have only Sauce Labs Onesie implemented in ProductsPage
        productsPage.addSauceLabsOnesieToCart();
    }

    @Then("the cart should contain {int} item")
    public void theCartShouldContainItem(int expectedCount) {
        int actualCount = productsPage.getCartItemCount();
        Assert.assertEquals(actualCount, expectedCount,
                "Cart item count mismatch");

    }

    @When("the user clicks on the menu button")
    public void theUserClicksOnTheMenuButton() {
        productsPage.clickMenuButton();
    }

    @And("the user selects Logout")
    public void theUserSelects() {
        productsPage.clickLogoutButton();
    }

    @Then("the user should be redirected to the login page")
    public void theUserShouldBeRedirectedToTheLoginPage() {
        LoginPage loginPage = new LoginPage();
        Assert.assertTrue(loginPage.isLoginButtonVisible(),
                "Expected login button to be visible, meaning we are on the login page");
    }

}
