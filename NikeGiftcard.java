
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.*;
import java.io.File;
import java.io.IOException;


public class NikeGiftcard {
    WebDriver driver;

    @BeforeMethod
    public void setUp() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        Thread.sleep(2000); // give browser time to maximize
    }

    @Test(priority = 1)
    public void navigateToGiftcardLinks() throws InterruptedException, IOException {
        // navigating to giftcard through links
        driver.get("https://www.nike.com/");
        driver.findElement(By.linkText("Gift Cards")).click();
        Thread.sleep(2000);
        driver.findElement(By.linkText("Email a Gift Card")).click();
        Thread.sleep(2000);
        // confirm it went to links
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File src = screenshot.getScreenshotAs(OutputType.FILE);
        // store ss in downloads
        File des = new File("screenshot\\giftcard1.png");
        FileHandler.copy(src, des);
        Thread.sleep(1000);
    }

    @Test(priority = 2)
    public void navigateToGiftcardSearch() throws InterruptedException, IOException {
        // navigate to giftcard through search
        driver.get("https://www.nike.com/");
        driver.findElement(By.xpath("//*[@id=\"gn-search-input\"]")).sendKeys("giftcard",Keys.ENTER);
        Thread.sleep(3000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement target = driver.findElement(By.linkText("Nike Digital Gift Card"));
        js.executeScript("arguments[0].click();", target);
        Thread.sleep(2000);
        // confirm it arrived to giftcard
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File src = screenshot.getScreenshotAs(OutputType.FILE);
        // store ss in downloads
        File des = new File("screenshot\\giftcard2.png");
        FileHandler.copy(src, des);
        Thread.sleep(1000);
    }
    @Test(priority = 3)
    public void customizeGiftcard() throws InterruptedException, IOException {
        // navigate to giftcard and then customize it
        driver.get("https://www.nike.com/");
        // //*[@id="gn-search-input"]
        driver.findElement(By.xpath("//*[@id=\"gn-search-input\"]")).sendKeys("giftcard",Keys.ENTER);
        Thread.sleep(3000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement target = driver.findElement(By.linkText("Nike Digital Gift Card"));
        // deals with interceptions
        js.executeScript("arguments[0].click();", target);
        Thread.sleep(2000);

        // choose card design
        driver.findElement(By.cssSelector("#colorway-picker-container > a:nth-child(5)")).click();
        Thread.sleep(1000);
        // input custom amount
        driver.findElement(By.xpath("//*[@id=\"gc-custom-amount\"]")).sendKeys("80");
        Thread.sleep(1000);
        // input name
        driver.findElement(By.xpath("//*[@id=\"gc-recipientFirstName\"]")).sendKeys("Fake");
        Thread.sleep(1000);
        // name
        driver.findElement(By.xpath("//*[@id=\"gc-recipientLastName\"]")).sendKeys("Name");
        Thread.sleep(1000);
        // input email address
        driver.findElement(By.xpath("//*[@id=\"gc-recipientEmailAddress\"]")).sendKeys("FakeEmail@gmail.com");
        Thread.sleep(1000);
        // input message
        driver.findElement(By.xpath("//*[@id=\"optional-message\"]"))
                .sendKeys("Happy super fake birthday! I hope you have a great " +
                        "fake birthday!");
        // ss customization confirmaiton
        Thread.sleep(1000);
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File src = screenshot.getScreenshotAs(OutputType.FILE);
        // store ss in downloads
        File des = new File("screenshot\\giftcardCustom.png");
        FileHandler.copy(src, des);
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div[2]/div[3]/div[5]/div/button")).click();


    }

    @Test(priority = 4)
    public void customizeGiftcardError() throws InterruptedException, IOException {
        // customize giftcard with mistakes (should show error messages)
        driver.get("https://www.nike.com/");
        // //*[@id="gn-search-input"]
        driver.findElement(By.xpath("//*[@id=\"gn-search-input\"]")).sendKeys("giftcard", Keys.ENTER);
        Thread.sleep(3000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement target = driver.findElement(By.linkText("Nike Digital Gift Card"));
        js.executeScript("arguments[0].click();", target);
        Thread.sleep(2000);
        // design choice
        driver.findElement(By.cssSelector("#colorway-picker-container > a:nth-child(5)")).click();
        Thread.sleep(1000);
        // out of range amount
        driver.findElement(By.xpath("//*[@id=\"gc-custom-amount\"]")).sendKeys("800");
        Thread.sleep(1000);
        // no first name input
        driver.findElement(By.xpath("//*[@id=\"gc-recipientFirstName\"]")).sendKeys("F",Keys.BACK_SPACE);
        Thread.sleep(1000);
        // no lasgt name input
        driver.findElement(By.xpath("//*[@id=\"gc-recipientLastName\"]")).sendKeys("N",Keys.BACK_SPACE);
        Thread.sleep(1000);
        // mistake in email input
        driver.findElement(By.xpath("//*[@id=\"gc-recipientEmailAddress\"]")).sendKeys("FakeEmail@ gmail.co");
        Thread.sleep(1000);
        // ss error messages
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File src = screenshot.getScreenshotAs(OutputType.FILE);
        // store ss in downloads
        File des = new File("screenshot\\giftcardCustomErrors.png");
        FileHandler.copy(src, des);
        Thread.sleep(1000);

    }
    @Test(priority = 5)
    public void viewTermsAndConditions() throws InterruptedException, IOException {
        // navigate to terms and conditions
        driver.get("https://www.nike.com/");
        driver.findElement(By.xpath("//*[@id=\"gen-nav-footer\"]/footer/div[2]/div[2]/a[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.linkText("Terms and Conditions")).click();
        Thread.sleep(4000);
        // ss page
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File src = screenshot.getScreenshotAs(OutputType.FILE);
        // store ss in downloads
        File des = new File("screenshot\\giftcardTerms.png");
        FileHandler.copy(src, des);
        Thread.sleep(1000);
        // search for help
        driver.findElement(By.xpath("//*[@id=\"searchInput\"]")).sendKeys("how to use giftcard",Keys.ENTER);
        Thread.sleep(2000);
        driver.findElement(By.linkText("How Do I Use My Nike Gift Card?")).click();
        Thread.sleep(2000);
        // ss of help guide
        TakesScreenshot screenshot2 = (TakesScreenshot) driver;
        File src2 = screenshot2.getScreenshotAs(OutputType.FILE);
        // store ss in downloads
        File des2 = new File("screenshot\\giftcardUse.png");
        FileHandler.copy(src2, des2);
        Thread.sleep(2000);
    }



    @AfterMethod
    public void closeBrowser () throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();

    }
}