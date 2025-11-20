package pages;

import models.ProductData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage{

    private By cartHeader = By.cssSelector("[data-test='title']");
    private By checkoutButton = By.cssSelector("[data-test='checkout']");
    private By cartItems = By.cssSelector(".cart_item");
    private By inventoryItemName = By.cssSelector("[data-test='inventory-item-name']");
    private By cartQuantity = By.cssSelector("[data-test='cart-quantity']");
    private By inventoryItemPrice = By.className("inventory_item_price");

    public CartPage() { super();}

    public String getCartHeaderText() {
        return waitForVisibility(cartHeader).getText();
    }

    public List<WebElement> getCartItems() {
        return waitForVisibilityAll(cartItems);
    }

    public void clickCheckoutButton() {
        click(checkoutButton);
    }

    public List<ProductData> getCartProducts() {
        List<ProductData> products = new ArrayList<>();

        List<WebElement> items = waitForVisibilityAll(cartItems);


        for (WebElement item : items) {
            String name = item.findElement(inventoryItemName).getText();
            String price = item.findElement(inventoryItemPrice).getText();

            products.add(new ProductData(name, price));
        }
        return products;
    }
}

