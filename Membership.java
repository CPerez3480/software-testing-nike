import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.*;

import java.time.Duration;

public class Membership {
    // before every test load the page and instantiate objects of all the necessary tools
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;

    @BeforeMethod
    void loadPage() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.nike.com/membership");

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        actions = new Actions(driver);
    }

    // after every test close the page
    @AfterMethod
    void closePage() {
        driver.quit();
    }

    // fetch the current URL and page title
    @Test(priority = 1)
    void getCurrentUrl() {
        // fetch the current URL
        System.out.println("Current URL: " + driver.getCurrentUrl());

        // fetch the page title
        System.out.println("Page Title: " + driver.getTitle());
    }

    // scroll through the top and bottom carousels
    @Test(priority = 2)
    void carousels() throws InterruptedException {
        // wait for page to load
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"799c975e-c181-4b6b-b0ea-769e17198073\"]/div/section/div/div[2]/button[2]")));

        JavascriptExecutor exe = (JavascriptExecutor) driver;
        exe.executeScript("window.scrollBy(0, 1000);");
        Thread.sleep(500);

        WebElement carouselBtnNext1 = driver.findElement(By.xpath("//*[@id=\"799c975e-c181-4b6b-b0ea-769e17198073\"]/div/section/div/div[2]/button[2]"));

        carouselBtnNext1.click();
        Thread.sleep(500);

        carouselBtnNext1.click();
        Thread.sleep(500);

        WebElement carouselBtnPrev1 = driver.findElement(By.xpath("//*[@id=\"799c975e-c181-4b6b-b0ea-769e17198073\"]/div/section/div/div[2]/button[1]"));

        carouselBtnPrev1.click();
        Thread.sleep(500);

        carouselBtnPrev1.click();
        Thread.sleep(500);

        exe.executeScript("window.scrollBy(0, 1000);");
        Thread.sleep(500);

        WebElement carouselBtnNext2 = driver.findElement(By.xpath("//*[@id=\"1c647239-6d06-40e2-8f00-baeb97eccb09\"]/div/section/div/div[2]/button[2]"));

        carouselBtnNext2.click();
        Thread.sleep(500);

        carouselBtnNext2.click();
        Thread.sleep(500);

        WebElement carouselBtnPrev2 = driver.findElement(By.xpath("//*[@id=\"1c647239-6d06-40e2-8f00-baeb97eccb09\"]/div/section/div/div[2]/button[1]"));

        carouselBtnPrev2.click();
        Thread.sleep(500);

        carouselBtnPrev2.click();
        Thread.sleep(500);
    }

    // scroll through the top and bottom carousels
    @Test(priority = 3)
    void faq() throws InterruptedException {
        // wait for page to load
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"80f81ad0-339d-49ec-a096-2e6425918eea__286dbec1-feda-4f8b-b134-8ae948bcfd5e__panel\"]/summary/span/span[2]")));

        WebElement faqDropDown1 = driver.findElement(By.xpath("//*[@id=\"80f81ad0-339d-49ec-a096-2e6425918eea__286dbec1-feda-4f8b-b134-8ae948bcfd5e__panel\"]/summary/span/span[2]"));

        faqDropDown1.click();
        Thread.sleep(1000);

        WebElement faqDropDown2 = driver.findElement(By.xpath("//*[@id=\"80f81ad0-339d-49ec-a096-2e6425918eea__77c46060-33fb-4681-bd86-f4df71e8e55f__panel\"]/summary/span/span[2]"));

        faqDropDown2.click();
        Thread.sleep(1000);

        WebElement faqDropDown3 = driver.findElement(By.xpath("//*[@id=\"80f81ad0-339d-49ec-a096-2e6425918eea__4c93e329-8b23-47e4-b298-7202bbe086fd__panel\"]/summary/span/span[2]"));

        faqDropDown3.click();
        Thread.sleep(1000);

        JavascriptExecutor exe = (JavascriptExecutor) driver;
        exe.executeScript("window.scrollBy(0, 250);");
        Thread.sleep(500);

        faqDropDown3.click();
        Thread.sleep(1000);

        faqDropDown2.click();
        Thread.sleep(1000);

        faqDropDown1.click();
        Thread.sleep(1000);
    }

    @Test(priority = 4)
    void joinUs() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ea4b7f00-6dc9-4308-a283-1437995c384e\"]/div/div/nav/div[1]/ul/li[1]/a")));
        driver.findElement(By.xpath("//*[@id=\"ea4b7f00-6dc9-4308-a283-1437995c384e\"]/div/div/nav/div[1]/ul/li[1]/a")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("username")));
        WebElement email = driver.findElement(By.id("username"));

        email.sendKeys("fgcucen4072testemail");
        email.sendKeys(Keys.ENTER);
        Thread.sleep(1000);

        email.sendKeys("@gmail.com");
        Thread.sleep(1000);
        email.sendKeys(Keys.ENTER);
    }

    @Test(priority = 5)
    void signIn() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"gen-nav-commerce-header-v2\"]/nav/div[1]/div/div[2]/nav/ul/li[4]")));

        WebElement signIn = driver.findElement(By.xpath("//*[@id=\"gen-nav-commerce-header-v2\"]/nav/div[1]/div/div[2]/nav/ul/li[4]"));
        signIn.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("username")));
        WebElement email = driver.findElement(By.id("username"));

        email.sendKeys("cs48898");
        email.sendKeys(Keys.ENTER);
        Thread.sleep(1000);

        email.sendKeys("@gmail.com");
        Thread.sleep(1000);
        email.sendKeys(Keys.ENTER);

        Thread.sleep(1000);
    }
}

