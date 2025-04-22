// File: FooterLinkTests.java
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

public class FooterLinkTests {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.nike.com");

        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));

        try {
            WebElement cookieBanner = driver.findElement(By.cssSelector("button[id*='cookie']"));
            if (cookieBanner.isDisplayed()) {
                cookieBanner.click();
            }
        } catch (NoSuchElementException ignored) {}
    }

    @Test(priority = 1)
    public void verifyHelpLinkWorks() {
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(1000);
            WebElement helpLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("a[aria-label='Help']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", helpLink);
            wait.until(ExpectedConditions.elementToBeClickable(helpLink));
            Thread.sleep(800);
            try {
                helpLink.click();
            } catch (ElementClickInterceptedException e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", helpLink);
            }

            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("help"),
                    ExpectedConditions.titleContains("Help")
            ));

            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("searchInput")));
            searchInput.sendKeys("birthday");
            searchInput.sendKeys(Keys.ENTER);

            WebElement article = wait.until(ExpectedConditions.elementToBeClickable(
                    By.partialLinkText("Does Nike Offer a Birthday Discount")));
            article.click();

            Assert.assertTrue(driver.getTitle().toLowerCase().contains("birthday"));

            returnToNikeHome();
        } catch (Exception e) {
            takeScreenshot("screenshot\\verifyHelpLinkWorks.png");
            throw new RuntimeException("Help link test failed", e);
        }
    }

    @Test(priority = 2)
    public void verifyStudentDiscountInteraction() {
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(1000);
            WebElement studentDiscount = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.partialLinkText("Student")));
            studentDiscount.click();

            wait.until(ExpectedConditions.urlContains("student-discount"));

            WebElement verifyStatusButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Verify Status')]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", verifyStatusButton);
            Thread.sleep(500);
            verifyStatusButton.click();

            Thread.sleep(2000);
            String originalWindow = driver.getWindowHandle();
            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(originalWindow)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }

            wait.until(ExpectedConditions.urlContains("sheerid"));
            driver.close();
            driver.switchTo().window(originalWindow);
            wait.until(ExpectedConditions.urlContains("student-discount"));
        } catch (Exception e) {
            takeScreenshot("screenshot\\verifyStudentDiscountInteraction.png");
            throw new RuntimeException("Student discount interaction failed", e);
        }
    }

    @Test(priority = 3)
    public void verifyPrivacyPolicyLink() {
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(1000);
            WebElement privacyLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.partialLinkText("Privacy")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", privacyLink);
            Thread.sleep(800);
            Assert.assertTrue(privacyLink.isDisplayed());
            privacyLink.click();

            wait.until(ExpectedConditions.urlContains("privacyPolicy"));
            driver.navigate().back();
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("nike.com"),
                    ExpectedConditions.titleContains("Nike")
            ));
        } catch (Exception e) {
            takeScreenshot("screenshot\\verifyPrivacyPolicyLink.png");
            throw new RuntimeException("Privacy Policy link test failed", e);
        }
    }

    @Test(priority = 4)
    public void verifyTermsOfSaleLink() {
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(1000);
            WebElement termsLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.partialLinkText("Terms of Sale")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", termsLink);
            Thread.sleep(800);
            Assert.assertTrue(termsLink.isDisplayed());
            termsLink.click();

            wait.until(ExpectedConditions.urlContains("agreementType=termsOfSale"));
            driver.navigate().back();
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("nike.com"),
                    ExpectedConditions.titleContains("Nike")
            ));
        } catch (Exception e) {
            takeScreenshot("screenshot\\verifyTermsOfSaleLink.png");
            throw new RuntimeException("Terms of Sale link test failed", e);
        }
    }

    @Test(priority = 5)
    public void verifyInstagramIconViaAboutPage() {
        try {
            driver.get("https://about.nike.com/en");
            JavascriptExecutor js = (JavascriptExecutor) driver;

            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(2000);

            WebElement instaIcon = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("svg.css-c2rigk[aria-hidden='true']")));
            WebElement parentLink = instaIcon.findElement(By.xpath("ancestor::a"));

            js.executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", parentLink);

            Thread.sleep(3000);
            takeScreenshot("screenshot\\verifyInstagramIconLink.png");

        } catch (Exception e) {
            takeScreenshot("screenshot\\verifyInstagramIconLink.png");
            throw new RuntimeException("Instagram icon link test failed", e);
        }
    }

    public void returnToNikeHome() {
        try {
            WebElement logo = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("a[aria-label='Nike Home Page']")));
            logo.click();
            wait.until(ExpectedConditions.urlToBe("https://www.nike.com/"));
        } catch (Exception e) {
            System.out.println("Could not return to Nike homepage.");
        }
    }

    public void takeScreenshot(String filePath) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);
            File dest = new File(filePath);
            FileHandler.copy(src, dest);
        } catch (IOException ex) {
            System.out.println("Screenshot failed: " + filePath);
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}