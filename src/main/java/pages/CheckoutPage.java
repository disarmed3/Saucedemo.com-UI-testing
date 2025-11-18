package pages;

import org.openqa.selenium.By;

public class CheckoutPage extends BasePage {

    private By firstNameField = By.id("first-name");
    private By lastNameField = By.id("last-name");
    private By postalCodeField = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By errorMessage = By.cssSelector("[data-test='error']");

    public CheckoutPage() { super(); }

    public void fillCheckoutForm(String firstName, String lastName, String postalCode) {
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(postalCodeField, postalCode);
    }

    public void clickContinueButton() {
        click(continueButton);
    }

    public boolean isStepOneDisplayed() {
        return waitForVisibility(firstNameField).isDisplayed();
    }

    public String getErrorMessage() {
        return waitForVisibility(errorMessage).getText();
    }}

