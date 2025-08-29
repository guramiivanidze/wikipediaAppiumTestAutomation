package com.example.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.*;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

/**
 * Base test class containing common setup and teardown methods
 * Implements driver management and screenshot capture for failed tests
 */
public class BaseTest {
    
    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    
    protected AppiumDriver driver;
    
    // Wikipedia app package and activity
    private static final String APP_PACKAGE = "org.wikipedia";
    private static final String APP_ACTIVITY = "org.wikipedia.main.MainActivity";
    private static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723";
    
    @BeforeMethod
    @Step("Initialize mobile driver and launch Wikipedia app")
    public void setUp() {
        logger.info("Setting up test environment");
        try {
            UiAutomator2Options options = new UiAutomator2Options();
            options.setPlatformName("Android");
            options.setDeviceName("Android Device"); // This will use any available device
            options.setAppPackage(APP_PACKAGE);
            options.setAppActivity(APP_ACTIVITY);
            options.setNoReset(false); // Reset app state between tests
            options.setNewCommandTimeout(Duration.ofSeconds(60));
   
            // Additional capabilities for better stability
            options.setCapability("autoGrantPermissions", true);
            options.setCapability("automationName", "UiAutomator2");
            
            URL serverUrl = URI.create(APPIUM_SERVER_URL).toURL();
            driver = new AndroidDriver(serverUrl, options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            
            logger.info("Driver initialized successfully");
            

        } catch (MalformedURLException e) {
            logger.error("Invalid Appium server URL: " + APPIUM_SERVER_URL, e);
            throw new RuntimeException("Failed to initialize driver", e);
        } catch (Exception e) {
            logger.error("Failed to initialize driver", e);
            throw new RuntimeException("Failed to initialize driver", e);
        }
    }
    
    @AfterMethod
    @Step("Close mobile driver and cleanup")
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.warn("Test failed: " + result.getName());
            captureScreenshot(result.getName());
        }
        
        if (driver != null) {
            try {
                driver.quit();
                logger.info("Driver closed successfully");
            } catch (Exception e) {
                logger.error("Error closing driver", e);
            }
        }
    }
    
    /**
     * Captures screenshot and attaches it to Allure report
     * @param testName Name of the failed test
     */
    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] captureScreenshot(String testName) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            
            // Also save to file system
            String screenshotDir = "target/screenshots";
            new File(screenshotDir).mkdirs();
            String filePath = screenshotDir + "/" + testName + "_" + System.currentTimeMillis() + ".png";
            Files.write(Paths.get(filePath), screenshot);
            
            logger.info("Screenshot captured: " + filePath);
            return screenshot;
        } catch (Exception e) {
            logger.error("Failed to capture screenshot", e);
            return new byte[0];
        }
    }
    
    /**
     * Wait utility method
     * @param milliseconds Time to wait in milliseconds
     */
    protected void waitFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Wait interrupted", e);
        }
    }
}
