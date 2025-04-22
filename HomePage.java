import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class HomePage {
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;

    // Setup method to run before each test method
    @BeforeMethod
    void loadPage() {
        // Launch a new Chrome browser
        driver = new ChromeDriver();
        // Maximize the browser window
        driver.manage().window().maximize();
        // Navigate to Nike homepage
        driver.get("https://nike.com/");

        // Initialize WebDriverWait and Actions
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        actions = new Actions(driver);
    }

    // Cleanup method to run after each test method
    @AfterMethod
    void closePage() {
        // Close the browser
        driver.quit();
    }

    // Test to print current URL and title of the page
    @Test(priority = 1)
    void urlAndTitle() {
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page Title: " + driver.getTitle());
    }

    // Test to interact with the video banner at the top of the homepage
    @Test(priority = 2)
    void videoBanner() throws InterruptedException {
        // Wait until the pause button is clickable
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"swiper-c3b05571-1fb9-4478-bd23-af634d016821\"]/div[3]/button")));
        Thread.sleep(1000);

        // Click to pause the video
        WebElement pauseButton = driver.findElement(By.xpath("//*[@id=\"swiper-c3b05571-1fb9-4478-bd23-af634d016821\"]/div[3]/button"));
        pauseButton.click();
        Thread.sleep(1000);

        // Click the 'next' button on the video banner multiple times
        WebElement nextButton = driver.findElement(By.xpath("//*[@id=\"swiper-c3b05571-1fb9-4478-bd23-af634d016821\"]/div[3]/div/button[2]"));
        nextButton.click();
        Thread.sleep(1000);
        nextButton.click();
        Thread.sleep(1000);

        // Click the 'previous' button to go back one card
        WebElement prevButton = driver.findElement(By.xpath("//*[@id=\"swiper-c3b05571-1fb9-4478-bd23-af634d016821\"]/div[3]/div/button[1]"));
        prevButton.click();
        Thread.sleep(1000);

        // Click next and wait to let the video iterate on its own
        nextButton.click();
        Thread.sleep(8000);
    }

    // Test to hover over each item in the top navigation menu
    @Test(priority = 3)
    void topMenu() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"gen-nav-commerce-header-v2\"]/nav/header/div/div/div[2]/nav/ul/li[1]/div/a")));

        // Hover actions for each top menu item
        WebElement newMenuItem = driver.findElement(By.xpath("//*[@id=\"gen-nav-commerce-header-v2\"]/nav/header/div/div/div[2]/nav/ul/li[1]/div/a"));
        actions.moveToElement(newMenuItem).perform();
        Thread.sleep(1000);

        WebElement menMenuItem = driver.findElement(By.xpath("//*[@id=\"gen-nav-commerce-header-v2\"]/nav/header/div/div/div[2]/nav/ul/li[2]/div/a"));
        actions.moveToElement(menMenuItem).perform();
        Thread.sleep(1000);

        WebElement womenMenuItem = driver.findElement(By.xpath("//*[@id=\"gen-nav-commerce-header-v2\"]/nav/header/div/div/div[2]/nav/ul/li[3]/div/a"));
        actions.moveToElement(womenMenuItem).perform();
        Thread.sleep(1000);

        WebElement kidsMenuItem = driver.findElement(By.xpath("//*[@id=\"gen-nav-commerce-header-v2\"]/nav/header/div/div/div[2]/nav/ul/li[4]/div/a"));
        actions.moveToElement(kidsMenuItem).perform();
        Thread.sleep(1000);

        WebElement jordanMenuItem = driver.findElement(By.xpath("//*[@id=\"gen-nav-commerce-header-v2\"]/nav/header/div/div/div[2]/nav/ul/li[5]/div/a"));
        actions.moveToElement(jordanMenuItem).perform();
        Thread.sleep(1000);

        WebElement sportMenuItem = driver.findElement(By.xpath("//*[@id=\"gen-nav-commerce-header-v2\"]/nav/header/div/div/div[2]/nav/ul/li[6]/div/button"));
        actions.moveToElement(sportMenuItem).perform();
        Thread.sleep(1000);
    }

    @Test(priority = 4)
    void slider() throws InterruptedException, IOException {
        // wait for the page to load
        wait.until(ExpectedConditions.elementToBeClickable(By.className("swiper-button-next")));

        // move to the next swiper button and take a screenshot of the Air Jordan 1 card
        WebElement swiperButtonNext = driver.findElement(By.className("swiper-button-next"));
        actions.moveToElement(swiperButtonNext).perform();
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File src1 = screenshot.getScreenshotAs(OutputType.FILE);
        File des1 = new File("screenshot\\Air Jordan 1.png");
        FileHandler.copy(src1, des1);

        // iterate through to the Dunk card and take a screenshot
        swiperButtonNext.click();
        Thread.sleep(1000);

        File src2 = screenshot.getScreenshotAs(OutputType.FILE);
        File des2 = new File("screenshot\\Dunk.png");
        FileHandler.copy(src2, des2);

        // iterate through to the Air Force 1 card and take a screenshot
        swiperButtonNext.click();
        Thread.sleep(1000);

        File src3 = screenshot.getScreenshotAs(OutputType.FILE);
        File des3 = new File("screenshot\\Field General.png");
        FileHandler.copy(src3, des3);

        // iterate through to the Vomero card and take a screenshot
        swiperButtonNext.click();
        Thread.sleep(1000);

        File src4 = screenshot.getScreenshotAs(OutputType.FILE);
        File des4 = new File("screenshot\\Air Force 1.png");
        FileHandler.copy(src4, des4);

        // iterate through to the Cortez card and take a screenshot
        swiperButtonNext.click();
        Thread.sleep(1000);

        File src5 = screenshot.getScreenshotAs(OutputType.FILE);
        File des5 = new File("screenshot\\Air Max.png");
        FileHandler.copy(src5, des5);

        // iterate through to the Air Max card and take a screenshot
        swiperButtonNext.click();
        Thread.sleep(1000);

        File src6 = screenshot.getScreenshotAs(OutputType.FILE);
        File des6 = new File("screenshot\\Vomero.png");
        FileHandler.copy(src6, des6);

        // iterate through to the Blazer card and take a screenshot
        swiperButtonNext.click();
        Thread.sleep(1000);

        File src7 = screenshot.getScreenshotAs(OutputType.FILE);
        File des7 = new File("screenshot\\Cortez.png");
        FileHandler.copy(src7, des7);

        // iterate through to the Blazer card and take a screenshot
        swiperButtonNext.click();
        Thread.sleep(1000);

        File src8 = screenshot.getScreenshotAs(OutputType.FILE);
        File des8 = new File("screenshot\\C1TY.png");
        FileHandler.copy(src8, des8);

        // iterate back through the slider using the previous swiper button
        WebElement swiperButtonPrev = driver.findElement(By.className("swiper-button-prev"));
        swiperButtonPrev.click();
        Thread.sleep(500);

        swiperButtonPrev.click();
        Thread.sleep(500);

        swiperButtonPrev.click();
        Thread.sleep(500);

        swiperButtonPrev.click();
        Thread.sleep(500);

        swiperButtonPrev.click();
        Thread.sleep(500);

        swiperButtonPrev.click();
        Thread.sleep(500);

        swiperButtonPrev.click();
        Thread.sleep(1000);
    }

    // Test to interact with the bottom menu
    @Test(priority = 5)
    void bottomMenu() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Vomero")));

        // Hover over the Vomero menu item to trigger the dropdown
        WebElement vomeroMenuItem = driver.findElement(By.linkText("Vomero"));
        actions.moveToElement(vomeroMenuItem).perform();
        Thread.sleep(2500);

        // Scroll down slightly to show bottom content
        JavascriptExecutor exe = (JavascriptExecutor) driver;
        exe.executeScript("window.scrollBy(0, 250);");
        Thread.sleep(2500);

        // Hover over the 'Find A Store' link
        WebElement findAStoreMenuItem = driver.findElement(By.xpath("//*[@id=\"5f0c77c8-1e19-44eb-afd3-0086047dd829\"]/div/div/nav/div[1]/ul/li[1]/a"));
        actions.moveToElement(findAStoreMenuItem).perform();
        Thread.sleep(2500);
    }
}