package com.example.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class WikipediaSearchPage extends BasePage {
    
    @AndroidFindBy(id = "org.wikipedia:id/search_src_text")
    private WebElement searchInputField;

    @AndroidFindBy(id="org.wikipedia:id/search_empty_message")
    private WebElement emptySearchMessage;
    
    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='org.wikipedia:id/search_results_list']//android.widget.TextView")
    private List<WebElement> searchResultsList;
    
    @AndroidFindBy(id = "org.wikipedia:id/search_results_list")
    private WebElement searchResultsContainer;
    
    @AndroidFindBy(id = "org.wikipedia:id/page_list_item_title")
    private List<WebElement> searchResultTitles;
    
    @AndroidFindBy(xpath = "//*[@resource-id='org.wikipedia:id/view_page_title_text']")
    private WebElement articleTitle;
    
    @AndroidFindBy(xpath = "//*[@content-desc='Navigate up']")
    private WebElement backButton;
    
    public WikipediaSearchPage(AppiumDriver driver) {
        super(driver);
    }
    
    @Override
    @Step("Verify search page is displayed")
    public boolean isDisplayed() {
        try {
            boolean isDisplayed = wait.until(ExpectedConditions.visibilityOf(emptySearchMessage)) != null;
            logger.info("Search page displayed: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.error("Search page not displayed", e);
            return false;
        }
    }
    
    @Step("Enter search term: {searchTerm}")
    public WikipediaSearchPage enterSearchTerm(String searchTerm) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(searchInputField));
            searchInputField.clear();
            searchInputField.sendKeys(searchTerm);
            logger.info("Entered search term: " + searchTerm);
            waitFor(2000); // Wait for search results to appear
        } catch (Exception e) {
            logger.error("Failed to enter search term: " + searchTerm, e);
            throw new RuntimeException("Could not enter search term", e);
        }
        return this;
    }
    
    @Step("Verify search results are displayed")
    public boolean areSearchResultsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(searchResultsContainer));
            boolean hasResults = !searchResultsList.isEmpty() || !searchResultTitles.isEmpty();
            logger.info("Search results displayed: " + hasResults);
            return hasResults;
        } catch (Exception e) {
            logger.error("Search results not displayed", e);
            return false;
        }
    }
    
    @Step("Get number of search results")
    public int getSearchResultsCount() {
        try {
            wait.until(ExpectedConditions.visibilityOf(searchResultsContainer));
            // Try both possible result containers
            int count = Math.max(searchResultsList.size(), searchResultTitles.size());
            logger.info("Found " + count + " search results");
            return count;
        } catch (Exception e) {
            logger.error("Failed to count search results", e);
            return 0;
        }
    }
    
    @Step("Click on first search result")
    public WikipediaArticlePage clickFirstResult() {
        try {
            wait.until(ExpectedConditions.visibilityOf(searchResultsContainer));
            
            WebElement firstResult = null;
            if (!searchResultsList.isEmpty()) {
                firstResult = searchResultsList.get(0);
            } else if (!searchResultTitles.isEmpty()) {
                firstResult = searchResultTitles.get(0);
            } else {
                // Fallback - find any clickable result
                firstResult = driver.findElement(AppiumBy.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='org.wikipedia:id/search_results_list']//android.view.ViewGroup[1]"));
            }
            
            wait.until(ExpectedConditions.elementToBeClickable(firstResult));
            String resultText = firstResult.getText();
            firstResult.click();
            
            logger.info("Clicked on first search result: " + resultText);
            waitFor(3000); // Wait for article to load
        } catch (Exception e) {
            logger.error("Failed to click first search result", e);
            throw new RuntimeException("Could not click first search result", e);
        }
        return new WikipediaArticlePage(driver);
    }
    
    @Step("Search for term and click first result: {searchTerm}")
    public WikipediaArticlePage searchAndSelectFirstResult(String searchTerm) {
        return this.enterSearchTerm(searchTerm)
                  .clickFirstResult();
    }
}
