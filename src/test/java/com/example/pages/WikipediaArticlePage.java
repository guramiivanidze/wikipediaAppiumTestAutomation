package com.example.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Wikipedia Article Page Object
 * Contains elements and methods for interacting with Wikipedia articles
 */
public class WikipediaArticlePage extends BasePage {
    
    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Java (programming language)\"]")
    private WebElement articleTitle;
    
    @AndroidFindBy(id = "org.wikipedia:id/page_contents_container")
    private WebElement articleContent;
    
    @AndroidFindBy(xpath = "//*[@content-desc='Navigate up']")
    private WebElement backButton;
    
    @AndroidFindBy(id = "org.wikipedia:id/view_page_error")
    private WebElement errorMessage;
    
    @AndroidFindBy(className = "android.webkit.WebView")
    private WebElement webView;
    
    // Alternative selectors for article content
    @AndroidFindBy(xpath = "//android.webkit.WebView//android.view.View")
    private WebElement articleContentAlt;
    
    public WikipediaArticlePage(AppiumDriver driver) {
        super(driver);
    }
    
    @Override
    @Step("Verify article page is displayed")
    public boolean isDisplayed() {
        try {
            // Wait for either article content or title to be visible
            boolean isDisplayed = wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOf(articleTitle),
                ExpectedConditions.visibilityOf(articleContent),
                ExpectedConditions.visibilityOf(webView)
            )) != null;
            logger.info("Article page displayed: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.error("Article page not displayed", e);
            return false;
        }
    }
    
    @Step("Get article title")
    public String getArticleTitle() {
        try {
            wait.until(ExpectedConditions.visibilityOf(articleTitle));
            String title = articleTitle.getText();
            logger.info("Article title: " + title);
            return title;
        } catch (Exception e) {
            logger.error("Failed to get article title", e);
            return "";
        }
    }
    
    @Step("Verify article content is displayed")
    public boolean isArticleContentDisplayed() {
        try {
            boolean contentDisplayed = wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOf(articleContent),
                ExpectedConditions.visibilityOf(webView),
                ExpectedConditions.visibilityOf(articleContentAlt)
            )) != null;
            logger.info("Article content displayed: " + contentDisplayed);
            return contentDisplayed;
        } catch (Exception e) {
            logger.error("Article content not displayed", e);
            return false;
        }
    }
    
    @Step("Go back to previous page")
    public WikipediaSearchPage goBack() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(backButton));
            backButton.click();
            logger.info("Clicked back button");
            waitFor(2000);
        } catch (Exception e) {
            logger.error("Failed to go back", e);
            // Fallback - use device back button
            driver.navigate().back();
            logger.info("Used device back button");
        }
        return new WikipediaSearchPage(driver);
    }
    
    @Step("Check if article loaded successfully")
    public boolean isArticleLoadedSuccessfully() {
        try {
            // Check if there's no error message and content is visible
            boolean hasError = false;
            try {
                hasError = errorMessage.isDisplayed();
            } catch (Exception e) {
                // No error element found, which is good
            }
            
            boolean hasContent = isArticleContentDisplayed();
            boolean success = !hasError && hasContent;
            
            logger.info("Article loaded successfully: " + success);
            return success;
        } catch (Exception e) {
            logger.error("Failed to check article load status", e);
            return false;
        }
    }
    
    @Step("Scroll down on article")
    public WikipediaArticlePage scrollDown() {
        try {
            // Perform scroll gesture
            driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()"
            ));
            logger.info("Scrolled down on article");
            waitFor(1000);
        } catch (Exception e) {
            logger.warn("Failed to scroll down", e);
        }
        return this;
    }
}
