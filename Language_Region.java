import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class Language_Region {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().window().maximize();
        driver.get("https://www.nike.com/?georedirect=no");

        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    private void scrollToFooter() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void takeScreenshot(String fileName) {
        try {
            if (((RemoteWebDriver) driver).getSessionId() != null) {
                File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String screenshotPath = "C:\\Users\\gabri\\IdeaProjects\\Software_Testing_Project\\screenshot";
                Files.createDirectories(Paths.get(screenshotPath));
                Files.copy(srcFile.toPath(), Paths.get(screenshotPath, fileName));
            }
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }

    @Test(priority = 1)
    public void openAndCloseRegionSelector() {
        try {
            // Scroll to footer area to ensure region button is visible
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(3000); // allow time for footer elements to appear

            // Click the region selector using aria-label
            WebElement regionBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button[aria-label^='Selected Location:']")));
            regionBtn.click();

            // Give overlay time to appear
            Thread.sleep(2000);

            // Click the close button for the overlay
            WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button[aria-label='Close Pop Up']")));
            closeBtn.click();

            Thread.sleep(1000); // wait to see overlay close
        } catch (Exception e) {
            takeScreenshot("openCloseRegionOverlay_failed.png");
            Assert.fail("Test failed: openAndCloseRegionSelector – " + e.getMessage());
        }
    }

    @Test(priority = 2)
    public void checkSpanishSiteContent() {
        try {
            driver.get("https://www.nike.com/es/");
            wait.until(ExpectedConditions.urlContains("/es"));
            Assert.assertTrue(driver.getPageSource().toLowerCase().contains("hombre"));
            System.out.println("Spanish site content verified.");
        } catch (Exception e) {
            takeScreenshot("checkSpanishSiteContent_failed.png");
            System.err.println("Warning: Could not validate Spanish site. " + e.getMessage());
            Assert.fail("Test failed: checkSpanishSiteContent – " + e.getMessage());
        }
    }

    @Test(priority = 3)
    public void checkJapaneseSiteContent() {
        try {
            driver.get("https://www.nike.com/jp/");
            wait.until(ExpectedConditions.urlContains("/jp"));
            Assert.assertTrue(driver.getPageSource().contains("メンズ"));
            System.out.println("Japanese site content verified.");
        } catch (Exception e) {
            takeScreenshot("checkJapaneseSiteContent_failed.png");
            System.err.println("Warning: Could not validate Japanese site. " + e.getMessage());
            Assert.fail("Test failed: checkJapaneseSiteContent – " + e.getMessage());
        }
    }

    @Test(priority = 4)
    public void checkEnglishSiteContent() {
        try {
            driver.get("https://www.nike.com/us/");
            wait.until(ExpectedConditions.urlContains("/us"));
            Assert.assertTrue(driver.getPageSource().toLowerCase().contains("men"));
            System.out.println("English site content verified.");
        } catch (Exception e) {
            takeScreenshot("checkEnglishSiteContent_failed.png");
            System.err.println("Warning: Could not validate English site. " + e.getMessage());
            Assert.fail("Test failed: checkEnglishSiteContent – " + e.getMessage());
        }
    }

    @Test(priority = 5)
    public void verifyURLLanguageCodes() {
        try {
            driver.get("https://www.nike.com/es/");
            Assert.assertTrue(driver.getCurrentUrl().contains("es"));

            driver.get("https://www.nike.com/jp/");
            Assert.assertTrue(driver.getCurrentUrl().contains("jp"));

            driver.get("https://www.nike.com/us/");
            Assert.assertTrue(driver.getCurrentUrl().contains("us"));

            System.out.println("Language code URLs verified.");
        } catch (Exception e) {
            takeScreenshot("verifyURLLanguageCodes_failed.png");
            Assert.fail("Test failed: verifyURLLanguageCodes – " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}