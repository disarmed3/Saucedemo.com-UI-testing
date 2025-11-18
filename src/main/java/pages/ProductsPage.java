package pages;

import org.openqa.selenium.By;

public class ProductsPage extends BasePage{

    private By onesieAddToCartButton = By.id("add-to-cart-sauce-labs-onesie");
    private By cartBadge = By.className("shopping_cart_badge");
    private By productsHeader = By.cssSelector("[data-test='title']");


    public ProductsPage() {
        super();
    }

    public void addSauceLabsOnesieToCart() {
        click(onesieAddToCartButton);
    }

    public CartPage clickCartBadge() {
        click(cartBadge);
        return new CartPage();
    }

    public String getProductsHeaderText() {
        return waitForVisibility(productsHeader).getText();
    }

}
