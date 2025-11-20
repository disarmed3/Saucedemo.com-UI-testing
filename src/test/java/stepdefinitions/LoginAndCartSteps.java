package stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import models.ProductData;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import pages.*;
import utils.ConfigReader;

import java.util.List;
import java.util.Map;

public class LoginAndCartSteps {

    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private List<ProductData> productsInCart;
    CheckoutOverviewPage overviewPage = new CheckoutOverviewPage();

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
        Assert.assertTrue(productsPage.getProductsHeaderText().contains("Products"));
        Assert.assertTrue(productsPage.getCurrentUrl().contains("inventory.html"),
                "Expected to be on products page");

    }

    @Given("the user has added a product to the cart")
    public void theUserHasAddedProductToTheCart() {
        productsPage.addFirstProductToCart();
    }

    @And("the user is on the cart page")
    public void theUserIsOnTheCartPage() {
        cartPage = new CartPage();
        productsPage.clickCartBadge();
        productsInCart = cartPage.getCartProducts();

        Assert.assertTrue(cartPage.getCartHeaderText().contains("Your Cart"));
        boolean productFound = !cartPage.getCartItems().isEmpty();
        Assert.assertTrue(productFound, "No products found in the cart");
        Assert.assertTrue(loginPage.getCurrentUrl().contains("cart.html"),
                "Expected to be on cart page");
    }

    @When("the user proceeds to checkout")
    public void theUserProceedsToCheckout() {
        cartPage.clickCheckoutButton();
        checkoutPage = new CheckoutPage();

    }

    @Then("the user verifies the following mandatory field errors:")
    public void theUserVerifiesTheFollowingMandatoryFieldErrors(DataTable dataTable) {
        SoftAssert softAssert = new SoftAssert();
        List<Map<String, String>> scenarios = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : scenarios) {
            String fName = row.get("FirstName");
            String lName = row.get("LastName");
            String zip = row.get("Zip");
            String expectedError = row.get("ErrorMessage");

            checkoutPage.clearForm();
            checkoutPage.fillCheckoutForm(fName, lName, zip);
            checkoutPage.clickContinueButton();

            if (!checkoutPage.isStepOneDisplayed()) {
                softAssert.fail("FAIL: we proceeded to Checkout: Overview. Fields Used:"+fName+","+lName+","+zip);
                checkoutPage.returnToCheckoutPage();
            } else {
                String actualError = checkoutPage.getErrorMessage();
                softAssert.assertEquals(actualError, expectedError,
                        "Error mismatch for inputs [" + fName + ", " + lName + ", " + zip + "]");
            }

        }
        softAssert.assertAll();

    }


    @Given("the user has added {int} random products to the cart")
    public void theUserHasAddedTwoRandomProductsToTheCart(int quantity) {
        productsPage.addRandomProducts(quantity);
    }

    @And("fills the checkout form with valid data")
    public void fillsTheCheckoutFormWithValidData() {
        checkoutPage.clearForm();
        checkoutPage.fillCheckoutForm("John", "Doe", "12345");
        checkoutPage.clickContinueButton();
    }

    @Then("the user proceeds to checkout step two page")
    public void theUserProceedsToCheckoutStepTwoPage() {
        Assert.assertTrue(overviewPage.getCheckoutOverviewText().contains("Overview"));
        Assert.assertTrue(loginPage.getCurrentUrl().contains("checkout-step-two.html"),
                "User did not reach Checkout Step Two page");

    }

    @And("the checkout step two page should display the correct products and prices")
    public void theCheckoutStepTwoPageShouldDisplayTheCorrectProductsAndPrices() {
        List<ProductData> productsInOverview = overviewPage.getOverviewProducts();
        Assert.assertEquals(productsInOverview.size(), productsInCart.size(), "Number of products in Overview does not match Cart!");
        Assert.assertTrue(productsInOverview.containsAll(productsInCart),
                "Product mismatch! \nExpected (Cart): " + productsInCart +
                        "\nActual (Overview): " + productsInOverview);
    }
}
