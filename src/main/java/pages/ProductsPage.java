package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Collections;
import java.util.List;

public class ProductsPage extends BasePage{

    private By firstProductAddButton = By.cssSelector("button.btn_inventory");
    private By cartBadge = By.className("shopping_cart_badge");
    private By productsHeader = By.cssSelector("[data-test='title']");


    public ProductsPage() {
        super();
    }

    public void addFirstProductToCart() {
        click(firstProductAddButton);
    }

    public CartPage clickCartBadge() {
        click(cartBadge);
        return new CartPage();
    }

    public String getProductsHeaderText() {
        return waitForVisibility(productsHeader).getText();
    }

    public void addRandomProducts(int quantity) {
        List<WebElement> allButtons = waitForVisibilityAll(By.cssSelector(".btn_inventory"));
        Collections.shuffle(allButtons);
        int clicksToPerform = Math.min(quantity, allButtons.size());
        for (int i=0; i< clicksToPerform; i++) allButtons.get(i).click();
    }
}
