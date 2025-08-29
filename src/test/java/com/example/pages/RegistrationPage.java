package com.example.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;

import java.time.Duration;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 
 * აქ იქნება ის ელემენტები და მეთოდები რაც რეგისტრაციის გვერდზე გვჭირდება
 */
public class RegistrationPage extends BasePage {

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Create an account']")
    private WebElement createAccountText;

    @AndroidFindBy(xpath = "//android.widget.EditText[@text='Username']")
    private WebElement usernameField;

    @AndroidFindBy(xpath = "//android.widget.EditText[@text='Password']")
    private WebElement passwordField;

    @AndroidFindBy(xpath = "//android.widget.EditText[@text='Repeat password']")
    private WebElement repeatPasswordField;

    @AndroidFindBy(xpath = "//android.widget.EditText[@text='Email (Optional)']")
    private WebElement emailField;

    @AndroidFindBy(id = "org.wikipedia:id/create_account_submit_button")
    private WebElement submitButton;

    @AndroidFindBy(id = "org.wikipedia:id/textinput_error")
    private WebElement errorMessage;

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

    public RegistrationPage(AppiumDriver driver) {
        super(driver);
    }

    @Override
    @Step("Verify registration page is displayed")
    public boolean isDisplayed() {
        try {
            boolean isDisplayed = wait.until(ExpectedConditions.visibilityOf(createAccountText)) != null;
            logger.info("Registration page displayed: {}", isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.error("Registration page not displayed", e);
            return false;
        }
    }

    @Step("Get error message text")
    public String getErrorText() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            String errorText = errorMessage.getText();
            logger.info("Error message retrieved: {}", errorText);
            return errorText;
        } catch (Exception e) {
            logger.error("Failed to get error message", e);
            return "";
        }
    }

    @Step("Enter username: {username} and expect error: {expectedError}")
    public RegistrationPage enterUsernameAndExpectError(String username, String expectedError) {
        logger.info("Entering username: {} and expecting error: {}", username, expectedError);
        
        clearAndEnterText(usernameField, username);
        waitForErrorAndValidate(expectedError);
        
        return this;
    }

    @Step("Enter email: {email}")
    public RegistrationPage enterEmail(String email) {
        logger.info("Entering email: {}", email);
        clearAndEnterText(emailField, email);
        return this;
    }

    @Step("Enter password")
    public RegistrationPage enterPassword(String password) {
        logger.info("Entering password");
        clearAndEnterText(passwordField, password);
        return this;
    }

    @Step("Enter confirm password")
    public RegistrationPage enterConfirmPassword(String confirmPassword) {
        logger.info("Entering confirm password");
        clearAndEnterText(repeatPasswordField, confirmPassword);
        return this;
    }

    @Step("Submit registration form and expect error: {expectedError}")
    public RegistrationPage submitRegistrationFormAndExpectError(String expectedError) {
        logger.info("Submitting registration form and expecting error: {}", expectedError);
        
        clickElement(submitButton);
        waitForErrorAndValidate(expectedError);
        
        return this;
    }

    @Step("Submit registration form successfully")
    public void submitRegistrationForm() {
        logger.info("Submitting registration form");
        clickElement(submitButton);
    }

    @Step("Fill complete registration form")
    public RegistrationPage fillRegistrationForm(String username, String password, String confirmPassword, String email) {
        logger.info("Filling complete registration form for username: {}", username);
        
        clearAndEnterText(usernameField, username);
        clearAndEnterText(passwordField, password);
        clearAndEnterText(repeatPasswordField, confirmPassword);
        
        if (email != null && !email.isEmpty()) {
            clearAndEnterText(emailField, email);
        }
        
        return this;
    }

    @Step("Verify no error message is displayed")
    public boolean isErrorMessageNotDisplayed() {
        try {
            return !errorMessage.isDisplayed();
        } catch (Exception e) {
            logger.debug("Error message element not found or not displayed");
            return true;
        }
    }

    // Helper methods
    private void clearAndEnterText(WebElement element, String text) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            logger.error("Failed to enter text into element", e);
            throw new RuntimeException("Failed to enter text: " + text, e);
        }
    }

    // Click element helper method
    private void clickElement(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        } catch (Exception e) {
            logger.error("Failed to click element", e);
            throw new RuntimeException("Failed to click element", e);
        }
    }

    // Wait for error message and validate its text
    private void waitForErrorAndValidate(String expectedError) {
        try {
            WebDriverWait errorWait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            WebElement errorElement = errorWait.until(ExpectedConditions.visibilityOf(errorMessage));
            
            String actualError = errorElement.getText();
            logger.info("Expected error: {}, Actual error: {}", expectedError, actualError);
            
            Assertions.assertThat(actualError)
                    .as("Error message should match expected text")
                    .isEqualTo(expectedError);
        } catch (Exception e) {
            logger.error("Failed to validate error message", e);
            throw new RuntimeException("Error message validation failed", e);
        }
    }
}