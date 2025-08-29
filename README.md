# Wikipedia Appium Test Automation

A comprehensive mobile test automation framework for Wikipedia Android app using Appium, TestNG, and Allure reporting with Page Object Model (POM) design pattern.

## 🏗️ Framework Architecture

This framework follows industry best practices and includes:

- **Page Object Model (POM)** - Organized page classes for better maintainability
- **TestNG** - Test execution framework with annotations and parallel execution support
- **Allure Reports** - Beautiful and comprehensive test reporting
- **Log4j2** - Structured logging with multiple appenders
- **AssertJ** - Fluent assertions for better readability
- **Maven** - Dependency management and build automation

## 📁 Project Structure

```
src/
├── test/
│   ├── java/
│   │   └── com/example/
│   │       ├── base/
│   │       │   └── BaseTest.java          # Base test class with driver management
│   │       ├── pages/
│   │       │   ├── BasePage.java          # Base page class
│   │       │   ├── WikipediaMainPage.java # Main page objects
│   │       │   ├── WikipediaSearchPage.java # Search page objects
│   │       │   └── WikipediaArticlePage.java # Article page objects
│   │       ├── tests/
│   │       │   └── WikipediaSearchTest.java # Test cases
│   │       └── utils/
│   │           ├── AllureUtils.java       # Allure reporting utilities
│   │           └── TestUtils.java         # Common test utilities
│   └── resources/
│       ├── testng.xml                     # TestNG configuration
│       ├── log4j2.xml                     # Logging configuration
│       └── allure.properties              # Allure configuration
```

## 🚀 Getting Started

### Prerequisites

1. **Java 11+** - Ensure Java is installed and JAVA_HOME is set
2. **Android Studio** - Install Android Studio with Android SDK
3. **Appium Server** - Install Appium server globally
4. **Android Device/Emulator** - Set up Android device or emulator
5. **Wikipedia App** - Install Wikipedia app on your Android device

### Installation

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd wikipediaAppiumTestAutomation
   ```

2. **Install dependencies:**
   ```bash
   mvn clean install
   ```

3. **Start Appium Server:**
   ```bash
   appium --port 4723
   ```
   Or start Appium Desktop/Studio

4. **Connect Android Device:**
   - Enable Developer Options and USB Debugging
   - Connect device via USB or start emulator
   - Verify connection: `adb devices`

5. **Install Wikipedia App:**
   - Download from Google Play Store, or
   - Install APK manually: `adb install wikipedia.apk`

## 🧪 Running Tests

### Command Line Execution

**Run all tests:**
```bash
mvn clean test
```

**Run specific test class:**
```bash
mvn clean test -Dtest=WikipediaSearchTest
```

**Run specific test method:**
```bash
mvn clean test -Dtest=WikipediaSearchTest#testSearchJavaProgramming
```

### VS Code Task Execution

Use the predefined VS Code task:
- **Ctrl+Shift+P** → **Tasks: Run Task** → **Run Wikipedia Tests**

### TestNG XML Execution

```bash
mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml
```

## 📊 Test Reports

### Allure Reports

**Generate and open Allure report:**
```bash
mvn allure:serve
```

**Generate Allure report to directory:**
```bash
mvn allure:report
```

Reports include:
- Test execution timeline
- Step-by-step execution details
- Screenshots for failed tests
- Logs and attachments
- Trend analysis
- Environment information

### Surefire Reports

Standard TestNG reports are available at:
```
target/surefire-reports/
```

## 🧪 Test Cases

### Test Case 1: Search Java Programming
**Objective:** Verify search functionality and article navigation
**Steps:**
1. Launch Wikipedia app
2. Skip onboarding if present
3. Tap on search box
4. Enter "Java programming" as search term
5. Verify search results are displayed
6. Click on first search result
7. Verify article loads successfully
8. Verify article content is displayed
9. Scroll down to test interaction

**Expected Results:**
- Search results should be displayed
- Article should load without errors
- Article content should be visible
- All assertions should pass

### Test Case 2: Search and Navigate Back
**Objective:** Verify search and back navigation workflow
**Steps:**
1. Launch Wikipedia app
2. Skip onboarding if present
3. Tap on search box
4. Search for "Android" and select first result
5. Verify article loads successfully
6. Perform scroll interaction
7. Navigate back to search results
8. Verify search results are still displayed

**Expected Results:**
- Article navigation should work correctly
- Back button should return to search results
- Search results should persist after navigation
- All assertions should pass

## 🔧 Configuration

### Appium Configuration

Key capabilities set in `BaseTest.java`:
```java
options.setPlatformName("Android");
options.setDeviceName("Android Device");
options.setAppPackage("org.wikipedia");
options.setAppActivity("org.wikipedia.main.MainActivity");
options.setNoReset(false);
options.setNewCommandTimeout(Duration.ofSeconds(60));
```

### TestNG Configuration

Configured in `testng.xml`:
- Test suite organization
- Class execution order
- Parallel execution settings

### Logging Configuration

Configured in `log4j2.xml`:
- Console and file logging
- Rolling file appender
- Different log levels for different packages

## 🛠️ Troubleshooting

### Common Issues

1. **Appium Server Connection Failed**
   - Ensure Appium server is running on port 4723
   - Check if port is available: `netstat -an | grep 4723`

2. **Device Not Found**
   - Verify device connection: `adb devices`
   - Ensure USB debugging is enabled
   - Try restarting ADB: `adb kill-server && adb start-server`

3. **App Not Found**
   - Verify Wikipedia app is installed: `adb shell pm list packages | grep wikipedia`
   - Install app if missing: `adb install <path-to-apk>`

4. **Element Not Found**
   - Check if app UI has changed
   - Update element locators in page objects
   - Use Appium Inspector to verify element properties

5. **Test Timeouts**
   - Increase implicit wait timeout in BaseTest
   - Add explicit waits for slow-loading elements
   - Check device performance and network connectivity

### Debug Mode

Enable debug logging by modifying `log4j2.xml`:
```xml
<Logger name="com.example" level="DEBUG" additivity="false">
```


## 📈 Extending the Framework

### Adding New Tests
1. Create new test methods in existing test classes
2. Follow naming convention: `test<FeatureName>`
3. Add appropriate Allure annotations
4. Use AssertJ assertions for validation

### Adding New Page Objects
1. Create new page class extending `BasePage`
2. Define element locators using `@AndroidFindBy`
3. Implement page-specific methods
4. Add logging and error handling

### Adding New Utilities
1. Create utility classes in `utils` package
2. Make methods static for easy access
3. Add appropriate logging
4. Write unit tests for complex utilities

