package pages;

import org.openqa.selenium.By;

public class CartPage extends BasePage{

    private By cartHeader = By.cssSelector("[data-test='title']");
    private By inventoryItemName = By.cssSelector("[data-test='inventory-item-name']");
    private By itemQuantity = By.cssSelector("[data-test='item-quantity']");

    public CartPage() { super();}

    public String getCartHeaderText() {
        return waitForVisibility(cartHeader).getText();
    }

    public String getInventoryItemName() {
        return waitForVisibility(inventoryItemName).getText();
    }

    public String getItemQuantity() {
        return waitForVisibility(itemQuantity).getText();
    }
}
