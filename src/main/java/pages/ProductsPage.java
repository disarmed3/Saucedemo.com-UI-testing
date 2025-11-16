package pages;

import org.openqa.selenium.By;

public class ProductsPage extends BasePage{

    private By onesieAddToCartButton = By.id("add-to-cart-sauce-labs-onesie");
    private By cartBadge = By.className("shopping_cart_badge");
    private By menuButton = By.id("react-burger-menu-btn");
    private By logoutButton = By.id("logout_sidebar_link");

    public ProductsPage() {
        super();
    }

    public ProductsPage addSauceLabsOnesieToCart() {
        click(onesieAddToCartButton);
        return this;
    }

    public int getCartItemCount() {
        try {
            String countText = waitForVisibility(cartBadge).getText();
            return Integer.parseInt(countText);
        } catch (Exception e) {
            // If badge is not visible (cart empty), return 0
            return 0;
        }
    }

    public void clickMenuButton() {
        click(menuButton);
    }

    public LoginPage clickLogoutButton() {
        click((logoutButton));
        return new LoginPage();
    }

}
