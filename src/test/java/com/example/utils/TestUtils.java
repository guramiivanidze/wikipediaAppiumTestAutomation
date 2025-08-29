// package com.example.utils;

// import io.appium.java_client.AppiumDriver;
// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;
// import org.openqa.selenium.support.ui.WebDriverWait;

// import java.time.Duration;

// /**
//  * Utility class for common test operations
//  * Contains helper methods used across multiple test classes
//  */
// public class TestUtils {
    
//     private static final Logger logger = LogManager.getLogger(TestUtils.class);
    
//     /**
//      * Wait for a specific amount of time
//      * @param milliseconds Time to wait in milliseconds
//      */
//     public static void waitFor(long milliseconds) {
//         try {
//             Thread.sleep(milliseconds);
//             logger.debug("Waited for " + milliseconds + " milliseconds");
//         } catch (InterruptedException e) {
//             Thread.currentThread().interrupt();
//             logger.warn("Wait interrupted", e);
//         }
//     }
    
//     /**
//      * Create a WebDriverWait instance with specified timeout
//      * @param driver AppiumDriver instance
//      * @param timeoutSeconds Timeout in seconds
//      * @return WebDriverWait instance
//      */
//     public static WebDriverWait createWait(AppiumDriver driver, int timeoutSeconds) {
//         return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
//     }
    
//     /**
//      * Generate a timestamp string for unique file names
//      * @return Timestamp string
//      */
//     public static String getTimestamp() {
//         return String.valueOf(System.currentTimeMillis());
//     }
    
//     /**
//      * Check if a string is not null and not empty
//      * @param text Text to check
//      * @return true if text is valid
//      */
//     public static boolean isNotEmpty(String text) {
//         return text != null && !text.trim().isEmpty();
//     }
    
// }
