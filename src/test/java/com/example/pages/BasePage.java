package com.example.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base page class containing common functionality for all page objects
 * Implements common methods and WebDriverWait initialization
 */
public abstract class BasePage {
    
    protected final Logger logger = LogManager.getLogger(this.getClass());
    protected final AppiumDriver driver;
    protected final WebDriverWait wait;
    
    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
        logger.info("Initialized page: " + this.getClass().getSimpleName());
    }
    
    /**
     * Wait for a specific amount of time
     * @param milliseconds Time to wait
     */
    @Step("Wait for {0} milliseconds")
    protected void waitFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Wait interrupted", e);
        }
    }
    
    /**
     * Check if the page is displayed
     * @return true if page is displayed
     */
    public abstract boolean isDisplayed();
}
