package pages;

import models.ProductData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CheckoutOverviewPage extends BasePage{

    private By cartItemName = By.className("inventory_item_name");
    private By cartItemPrice = By.className("inventory_item_price");
    private By cartItemsContainer = By.className("cart_item");
    private By finishButton = By.id("finish");

    public List<ProductData> getOverviewProducts() {
        List<ProductData> products = new ArrayList<>();
        List<WebElement> items = waitForVisibilityAll(cartItemsContainer);

        for (WebElement item : items) {
            String name = item.findElement(cartItemName).getText();
            String price = item.findElement(cartItemPrice).getText();
            products.add(new ProductData(name, price));
        }
        return products;
    }
    public void clickFinish() {
        click(finishButton);
    }
}
