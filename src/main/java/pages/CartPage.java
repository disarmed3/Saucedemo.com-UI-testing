package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage{

    private By cartHeader = By.cssSelector("[data-test='title']");
    private By checkoutButton = By.cssSelector("[data-test='checkout']");
    private By cartItems = By.cssSelector(".cart_item");
    private By inventoryItemName = By.cssSelector("[data-test='inventory-item-name']");
    private By cartQuantity = By.cssSelector("[data-test='cart-quantity']");

    public CartPage() { super();}

    public String getCartHeaderText() {
        return waitForVisibility(cartHeader).getText();
    }

    public List<WebElement> getCartItems() {
        return waitForVisibilityAll(cartItems);
    }

    public String getProductName(WebElement cartItem) {
        return waitForVisibility(inventoryItemName).getText();
    }

    public String getProductQuantity(WebElement cartItem) {
        return waitForVisibility(cartQuantity).getText();
    }

    public void clickCheckoutButton() {
        click(checkoutButton);
    }
}
