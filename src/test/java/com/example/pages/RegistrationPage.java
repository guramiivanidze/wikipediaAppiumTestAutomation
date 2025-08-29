package com.example.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;

import java.time.Duration;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage extends BasePage {

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Create an account\"]")
    private WebElement createAccountText;

    @AndroidFindBy(xpath = "//android.widget.EditText[@text=\"Username\"]")
    private WebElement usernameField;

    @AndroidFindBy(xpath = "//android.widget.EditText[@text=\"Password\"]")
    private WebElement passwordField;

    @AndroidFindBy(xpath = "//android.widget.EditText[@text=\"Repeat password\"]")
    private WebElement repeatPasswordField;

    @AndroidFindBy(xpath = "//android.widget.EditText[@text=\"Email (Optional)\"]")
    private WebElement emailField;

    @AndroidFindBy(id = "org.wikipedia:id/create_account_submit_button")
    private WebElement submitButton;

    @AndroidBy(id =  "org.wikipedia:id/textinput_error")
    private WebElement errorMessage;


    public RegistrationPage(AppiumDriver driver) {
        super(driver);

    }

    @Override
    @Step("Verify registration page is displayed")
    public boolean isDisplayed() {
        try {
            boolean isDisplayed = wait.until(ExpectedConditions.visibilityOf(createAccountText)) != null;
            logger.info("Registration page displayed: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.error("Registration page not displayed", e);
            return false;
        }
    }

    public String getErrorText() {
        return errorMessage.getText();
    }

    public RegistrationPage enterUsername(String username) {
        usernameField.sendKeys(username);
        return this;
    }


    public RegistrationPage enterUsername(String username, String expectedError) {
        usernameField.sendKeys(username);

        // Wait for error message to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOf(errorMessage));

        Assertions.assertThat(errorElement.getText())
                .as("Error message should be displayed for username")
                .isEqualTo(expectedError);

        return this;
    }

    public RegistrationPage enterEmail(String email) {
        emailField.sendKeys(email);
        return this;
    }

    public RegistrationPage enterPassword(String password) {

        passwordField.sendKeys(password);
        return this;
    }

    public RegistrationPage enterConfirmPassword(String confirmPassword) {
        repeatPasswordField.sendKeys(confirmPassword);
        return this;
    }

    public void submitRegistrationForm() {
        submitButton.click();
    }

    public void submitRegistrationForm(String expectedError) {
        submitButton.click();
        Assertions.assertThat(errorMessage.getText())
                .as("Error message should be displayed for registration")
                .isEqualTo(expectedError);
    }
}
