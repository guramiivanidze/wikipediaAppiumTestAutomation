package com.example.utils;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Utility class for Allure reporting
 * Contains methods for attaching files and adding steps to Allure reports
 */
public class AllureUtils {
    
    private static final Logger logger = LogManager.getLogger(AllureUtils.class);
    
    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] attachScreenshot(byte[] screenshot) {
        return screenshot;
    }
    
    @Attachment(value = "Text Attachment", type = "text/plain")
    public static String attachText(String text) {
        return text;
    }
    
    @Attachment(value = "Log File", type = "text/plain")
    public static byte[] attachLogFile(String filePath) {
        try {
            return Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            logger.error("Failed to attach log file: " + filePath, e);
            return new byte[0];
        }
    }
    
    @Step("Add info step: {message}")
    public static void addInfoStep(String message) {
        logger.info(message);
    }
    
    @Step("Add warning step: {message}")
    public static void addWarningStep(String message) {
        logger.warn(message);
    }
    
    @Step("Add error step: {message}")
    public static void addErrorStep(String message) {
        logger.error(message);
    }
}
