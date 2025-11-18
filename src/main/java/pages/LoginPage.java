package pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage{

    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");

    public LoginPage() {
        super();
    }

    public void enterUsername(String username) {
       type(usernameField, username);
    }

    public void enterPassword(String password) {
        type(passwordField, password);
    }

    public ProductsPage clickLogin() {
        click(loginButton);
        return new ProductsPage();
    }


}
