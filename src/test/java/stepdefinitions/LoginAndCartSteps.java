package stepdefinitions;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;
import utils.ConfigReader;

public class LoginAndCartSteps {

    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;

    @Given("the user is on the login page")
    public void theUserIsOnTheLoginPage() {
        loginPage = new LoginPage(); // opens login page
    }

    @And("the user logs in with valid credentials")
    public void theUserLogsInWithValidCredentials() {
        String username = ConfigReader.get("username");
        String password = ConfigReader.get("password");

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        productsPage = loginPage.clickLogin();
        String currentUrl = productsPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory.html"),
                "Expected to be on products page");
        Assert.assertTrue(productsPage.getProductsHeaderText().contains("Products"));
    }

    @Given("the user has added a product to the cart")
    public void theUserHasAddedProductToTheCart() {
        // For simplicity, we have only Sauce Labs Onesie implemented in ProductsPage
        productsPage.addSauceLabsOnesieToCart();
    }

    @And("the user is on the cart page")
    public void theUserIsOnTheCartPage() {
        productsPage.clickCartBadge();
        String currentUrl = loginPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("cart.html"),
                "Expected to be on cart page");
        cartPage = new CartPage();
        Assert.assertTrue(cartPage.getCartHeaderText().contains("Your Cart"));
        Assert.assertTrue(cartPage.getInventoryItemName().contains("Sauce Labs Onesie"));
        Assert.assertEquals(cartPage.getItemQuantity(), "1");
    }

    @When("the user proceeds to checkout")
    public void theUserProceedsToCheckout() {


    }

    @And("the user submits the checkout form leaving required fields blank")
    public void theUserSubmitsTheCheckoutFormLeavingRequiredFieldsBlank() {


    }

    @Then("the user remains on the checkout information page")
    public void theUserRemainsOnTheCheckoutInformationPage() {


    }

    @And("the system displays a clear error message indicating which required fields are missing")
    public void theSystemDisplaysAClearErrorMessageIndicatingWhichRequiredFieldsAreMissing() {


    }
}
