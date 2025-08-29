package com.example.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class WikipediaMainPage extends BasePage {

    @AndroidFindBy(id = "org.wikipedia:id/search_container")
    private WebElement searchContainer;

    @AndroidFindBy(id = "org.wikipedia:id/search_src_text")
    private WebElement searchBox;

    @AndroidFindBy(id = "org.wikipedia:id/alertTitle")
    private WebElement alertTitle;

    @AndroidFindBy(id = "android:id/button2")
    private WebElement cancelButton;

    @AndroidFindBy(id="org.wikipedia:id/dialog_checkbox")
    private WebElement dialogCheckbox;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Search Wikipedia']")
    private WebElement searchPlaceholder;

    @AndroidFindBy(id = "org.wikipedia:id/menu_overflow_button")
    private WebElement menuButton;

    @AndroidFindBy(id = "org.wikipedia:id/page_toolbar_button_show_overflow_menu")
    private WebElement heade3dotMenuElement;

    @AndroidFindBy(id = "org.wikipedia:id/page_web_view")
    private WebElement pageWebView;

    @AndroidFindBy(id = "org.wikipedia:id/nav_tab_more")
    private WebElement moreTab;

    @AndroidFindBy(id = "org.wikipedia:id/main_drawer_account_container")
    private WebElement accountContainer;

    @AndroidFindBy(id = "org.wikipedia:id/create_account_login_button")
    private WebElement loginBtnInCreatePage;

    @AndroidFindBy(xpath = "//android.widget.EditText[@text=\"Username\"]")
    private WebElement usernameFieldInLoginPage;

    @AndroidFindBy(xpath = "//android.widget.EditText[@text=\"Password\"]")
    private WebElement passwordFieldInLoginPage;

    @AndroidFindBy(id = "org.wikipedia:id/login_button")
    private WebElement loginButtonInLoginPage;

    @AndroidFindBy(id = "org.wikipedia:id/main_drawer_account_name")
    private WebElement usernameContainer;

    @AndroidFindBy(id = "org.wikipedia:id/main_drawer_settings_container")
    private WebElement settingsContainer;

    @AndroidFindBy(id = "android:id/button1")
    private WebElement okButton;

    // Alternative locators in case the above don't work
    @AndroidFindBy(accessibility = "Search Wikipedia")
    private WebElement searchContainerAlt;

    public WikipediaMainPage(AppiumDriver driver) {
        super(driver);
    }

    @Override
    @Step("Verify Wikipedia main page is displayed")
    public boolean isDisplayed() {
        try {
            boolean isDisplayed = wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOf(searchContainer),
                    ExpectedConditions.visibilityOf(searchContainerAlt),
                    ExpectedConditions.visibilityOf(searchPlaceholder))) != null;
            logger.info("Wikipedia main page displayed: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.error("Wikipedia main page not displayed", e);
            return false;
        }
    }

    @Step("Tap on search box")
    public WikipediaSearchPage tapSearchBox() {
        try {
            // Try different approaches to find and click the search
            if (searchContainer.isDisplayed()) {
                wait.until(ExpectedConditions.elementToBeClickable(searchContainer));
                searchContainer.click();
                logger.info("Clicked search container");
            } else if (searchBox.isDisplayed()) {
                wait.until(ExpectedConditions.elementToBeClickable(searchBox));
                searchBox.click();
                logger.info("Clicked search box");
            } else {
                // Fallback - try to find search by text
                WebElement searchElement = driver.findElement(
                        AppiumBy.xpath("//*[contains(@text, 'Search') or contains(@content-desc, 'Search')]"));
                searchElement.click();
                logger.info("Clicked search element using fallback");
            }
        } catch (Exception e) {
            logger.error("Failed to click search box", e);
            throw new RuntimeException("Could not interact with search box", e);
        }
        return new WikipediaSearchPage(driver);
    }

    @Step("Skip onboarding if present")
    public WikipediaMainPage skipOnboardingIfPresent() {
        try {
            // Look for skip or close buttons that might appear during onboarding
            WebElement skipButton = null;
            try {
                skipButton = driver.findElement(AppiumBy.xpath(
                        "//android.widget.Button[contains(@text, 'Skip') or contains(@text, 'Close') or contains(@text, 'Got it')]"));
            } catch (Exception e) {
                // Skip button not found, continue
            }

            if (skipButton != null && skipButton.isDisplayed()) {
                skipButton.click();
                logger.info("Skipped onboarding");
                waitFor(1000);
            }
        } catch (Exception e) {
            logger.info("No onboarding to skip or skip failed: " + e.getMessage());
        }
        return this;
    }

    public WebElement findElementByAccessibilityId(String id) {
        return driver.findElement(AppiumBy.accessibilityId(id));
    }

    @Step("Close alert dialog if present")
    public WikipediaMainPage closeAlertIfPresent() {
        try {
            if (alertTitle.isDisplayed()) {
                cancelButton.click();
                logger.info("Closed alert dialog");
                waitFor(1000);
            }
        } catch (Exception e) {
            logger.info("No alert dialog to close: " + e.getMessage());
        }
        return this;
    }

    @Step("Close Wikipedia game modal if present")
    public WikipediaMainPage closeWikipediaGameModalIfPresent() {
        try {
            WebElement closeButton = driver.findElement(AppiumBy.accessibilityId("Close"));
            if (closeButton.isDisplayed()) {
                closeButton.click();
                logger.info("Closed Wikipedia game modal");
                waitFor(1000);
            }
        } catch (Exception e) {
            logger.info("No Wikipedia game modal to close: " + e.getMessage());
        }
        return this;
    }

    @Step("Find button with text '{buttonText}' and click if present")
    public WikipediaMainPage findButtonWithTextAndClick(String buttonText) {
        try {
            WebElement button = driver.findElement(AppiumBy.xpath(
                    "//android.widget.Button[@text='" + buttonText + "']"));
            if (button.isDisplayed()) {
                button.click();
                logger.info("Clicked button with text: " + buttonText);
                waitFor(1000);
            }
        } catch (Exception e) {
            logger.info("No button with text '" + buttonText + "' to click: " + e.getMessage());
        }
        return this;
    }

    @Step("Click on header 3-dot menu element if present")
    public WikipediaMainPage clickHeader3dotMenuElement() {
        try {
            if (heade3dotMenuElement.isDisplayed()) {
                heade3dotMenuElement.click();
                logger.info("Clicked 3-dot menu element");
                waitFor(1000);
            }
        } catch (Exception e) {
            logger.info("No 3-dot menu element to click: " + e.getMessage());
        }
        return this;
    }

    @Step("Click on page web view if present")
    public WikipediaMainPage clickPageWebView() {
        try {
            if (pageWebView.isDisplayed()) {
                pageWebView.click();
                logger.info("Clicked on page web view");
                waitFor(1000);
            }
        } catch (Exception e) {
            logger.info("No page web view to click: " + e.getMessage());
        }
        return this;
    }

    // login related methods
    @Step("Click on More tab if present")
    public WikipediaMainPage clickMoreTab() {
        try {
            if (moreTab.isDisplayed()) {
                moreTab.click();
                logger.info("Clicked on More tab");
                waitFor(1000);
            }
        } catch (Exception e) {
            logger.info("No More tab to click: " + e.getMessage());
        }
        return this;
    }

    @Step("Click on Account container if present")
    public WikipediaMainPage clickAccountContainer() {
        try {
            if (accountContainer.isDisplayed()) {
                accountContainer.click();
                logger.info("Clicked on Account container");
                waitFor(1000);
            }
        } catch (Exception e) {
            logger.info("No Account container to click: " + e.getMessage());
        }
        return this;
    }

    @Step("Click on Login button in Create Account page if present")
    public WikipediaMainPage clickLoginBtnInCreatePage() {
        try {
            if (loginBtnInCreatePage.isDisplayed()) {
                loginBtnInCreatePage.click();
                logger.info("Clicked on Login button in Create Account page");
                waitFor(1000);
            }
        } catch (Exception e) {
            logger.info("No Login button in Create Account page to click: " + e.getMessage());
        }
        return this;
    }

    @Step("Perform login with username: {username} and password: {password}")
    public WikipediaMainPage enterCredentialsAndLogin(String username, String password) {
        try {

            usernameFieldInLoginPage.sendKeys(username);
            logger.info("Entered username: " + username);

            passwordFieldInLoginPage.sendKeys(password);
            logger.info("Entered password: " + password);

            loginButtonInLoginPage.click();
            logger.info("Clicked on Login button");
            return this;
        } catch (Exception e) {
            logger.info("Failed to enter credentials and login: " + e.getMessage());
            return this;
        }
    }

    @Step("veryfy login success by checking presence of Account container")
    public boolean isLoginSuccessful() {
        try {
            this.clickMoreTab();
            wait.until(ExpectedConditions.visibilityOf(usernameContainer));
            logger.info("Login successful, Username container is displayed");
            return true;
        } catch (Exception e) {
            logger.info("Login failed, Username container is not displayed: " + e.getMessage());
            return false;
        }
    }

    @Step("go to settings page")
    public WikipediaMainPage goToSettingsPage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(settingsContainer));
            settingsContainer.click();
            logger.info("Navigated to settings page");
        } catch (Exception e) {
            logger.info("Failed to navigate to settings page: " + e.getMessage());
        }
        return this;
    }

    public WebElement scrollToText(String text) {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().text(\"" + text + "\"))"));
    };

    @Step("check if user is logged in")
    public boolean isUserLoggedIn() {
        try {
            this.clickMoreTab();
            wait.until(ExpectedConditions.visibilityOf(usernameContainer));
            logger.info("User is logged in, Username container is displayed");
            try {
                WebElement modalOverlay = driver.findElement(AppiumBy.xpath("//android.view.View[@clickable='true' and not(@resource-id)]"));
                if (modalOverlay.isDisplayed()) {
                    modalOverlay.click();
                    logger.info("Clicked outside of modal to dismiss it");
                    waitFor(1000);
                }
            } catch (Exception ex) {
                logger.info("No modal overlay to click outside: " + ex.getMessage());
            }
            return true;
        } catch (Exception e) {
            logger.info("User is not logged in, Username container is not displayed: " + e.getMessage());
            return false;
        }
    }

    @Step("Click OK button if present")
    public WikipediaMainPage clickOkButtonIfPresent() {
        okButton.click();
        logger.info("Clicked OK button");
        return this;
    }


    
}