package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.WikipediaMainPage;
import com.example.pages.WikipediaSearchPage;
import com.example.pages.RegistrationPage;
import com.example.pages.WikipediaArticlePage;
import com.example.utils.AllureUtils;

import io.qameta.allure.*;

import java.time.Duration;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

/**
 * Wikipedia Search Test Class
 * Contains end-to-end test scenarios for Wikipedia mobile app
 * Tests cover search functionality and article navigation
 */
@Epic("Wikipedia Mobile App")
@Feature("Search and Navigation")
public class WikipediaSearchTest extends BaseTest {

    @Test(priority = 1, description = "Search for Java programming language and verify article content")
    @Story("Search Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test verifies that users can search for 'Java programming' and successfully navigate to the article")
    @Issue("WIKI-001")
    public void testSearchJavaProgramming() {
        AllureUtils.addInfoStep("Starting Java programming search test");
        WikipediaMainPage mainPage = new WikipediaMainPage(driver);
        mainPage.closeAlertIfPresent()
                .skipOnboardingIfPresent();
        Assertions.assertThat(mainPage.isDisplayed())
                .as("Wikipedia main page should be displayed")
                .isTrue();
        WikipediaSearchPage searchPage = mainPage.tapSearchBox();
        Assertions.assertThat(searchPage.isDisplayed())
                .as("Search page should be displayed")
                .isTrue();
        String searchTerm = "Java programming";
        searchPage.enterSearchTerm(searchTerm);
        Assertions.assertThat(searchPage.areSearchResultsDisplayed())
                .as("Search results should be displayed for: " + searchTerm)
                .isTrue();
        int resultsCount = searchPage.getSearchResultsCount();
        Assertions.assertThat(resultsCount)
                .as("Should have at least one search result")
                .isGreaterThan(0);

        AllureUtils.attachText("Search results count: " + resultsCount);
        WikipediaArticlePage articlePage = searchPage.clickFirstResult();
        mainPage.closeWikipediaGameModalIfPresent()
                .findButtonWithTextAndClick("Got it")
                .clickHeader3dotMenuElement();

        articlePage.scrollDown();

        AllureUtils.addInfoStep("Java programming search test completed successfully");
    }

    @Test(priority = 2, description = "veryfy login and logout functionality")
    @Story("Authentication Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("This test verifies the login and logout functionality")
    @Issue("WIKI-002")
    public void TestLoginLogout() {
        AllureUtils.addInfoStep("Starting login/logout test");
        WikipediaMainPage mainPage = new WikipediaMainPage(driver);

        mainPage.closeAlertIfPresent()
                .skipOnboardingIfPresent();

        Assertions.assertThat(mainPage.isDisplayed())
                .as("Wikipedia main page should be displayed")
                .isTrue();

        mainPage.clickMoreTab()
                .clickAccountContainer()
                .clickLoginBtnInCreatePage()
                .enterCredentialsAndLogin("appiumdemo", "appium123");
        mainPage.closeAlertIfPresent();

        Assertions.assertThat(mainPage.isLoginSuccessful())
                .as("Login should be successful")
                .isTrue();

        mainPage.clickMoreTab()
                .goToSettingsPage();
        mainPage.closeAlertIfPresent();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logoutBtn = mainPage.scrollToText("Log out");
        wait.until(ExpectedConditions.elementToBeClickable(logoutBtn)).click();
        mainPage.clickOkButtonIfPresent();

        Assertions.assertThat(mainPage.isLoginSuccessful())
                .as("Login should be successful")
                .isFalse();
        AllureUtils.addInfoStep("Login/logout test completed successfully");

    }

    @Test(priority = 3, description = "Verify registration form validation")
    @Story("Registration Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("This test verifies that the registration form shows validation errors for invalid inputs")
    @Issue("WIKI-003")
    public void TestRegistrationFormValidation() {
        AllureUtils.addInfoStep("Starting registration test");
        WikipediaMainPage mainPage = new WikipediaMainPage(driver);
        RegistrationPage registrationPage = new RegistrationPage(driver);

        mainPage.closeAlertIfPresent()
                .skipOnboardingIfPresent();

        Assertions.assertThat(mainPage.isDisplayed())
                .as("Wikipedia main page should be displayed")
                .isTrue();

        mainPage.clickMoreTab()
                .clickAccountContainer();

        registrationPage.enterUsername("testuser");

        registrationPage.enterPassword("short")
                .enterConfirmPassword("short");

        Assertions.assertThat(registrationPage.getErrorText())
                .as("Error message should be displayed for username")
                .isEqualTo("The user name \"testuser\" is not available. Please choose a different name.");
    }
}