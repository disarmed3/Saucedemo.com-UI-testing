package pages;

import org.openqa.selenium.By;

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

}
