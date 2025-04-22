
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Set;

public class NikeShoe {

    WebDriver driver;

    @BeforeMethod
    public void setUp() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        Thread.sleep(2000); // give browser time to maximize
    }


    @Test(priority = 1)
    public void navigateToShoeOne() throws InterruptedException {
        // Essentially as if a customer was navigating to a product
        // then changed their mind
        // go to nike.com
        driver.get("http://nike.com");
        Thread.sleep(3000);
        // go to women section
        driver.findElement(By.xpath("//*[@id=\"gen-nav-commerce-header-v2\"]/nav/header/div/div/div[2]/nav/ul/li[3]/div/a")).click();
        Thread.sleep(3000);
        // click on shoes
        driver.findElement(By.xpath("//*[@id=\"da387304-ef63-4e2e-a4ea-1af1d1222d26\"]/div/div/nav/div[1]/ul/li[1]/a")).click();
        Thread.sleep(3000);
        // scroll to find "Nike Wildhorse 10 Women's Trail Running Shoes"
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // our product //switch out with actual name
        String targetName = "Nike Wildhorse 10";

        // enough scrolls to reach
        int scrollStep = 20; // pixels per scroll
        int maxScroll = 100000; // limit total scroll range
        int currentScroll = 0; // current scroll

        // scroll to product
        while (currentScroll < maxScroll) {
            try { // try to find product
                WebElement target = driver.findElement(By.linkText(targetName));

                // check for shoe product based on boundaries
                Boolean isInView = (Boolean) js.executeScript(
                        "var rect = arguments[0].getBoundingClientRect();" +
                                "return (rect.top >= 0 && rect.bottom <= window.innerHeight);",
                        target
                );
                // click on product if in view
                if (isInView) {
                    js.executeScript("arguments[0].click();", target);
                    break;
                }
                // catches potienal issues
            } catch (NoSuchElementException | StaleElementReferenceException ignored) {}
            // scroll by pre defined pixels (for more natural scroll)
            js.executeScript("window.scrollBy(0, arguments[0]);", scrollStep);
            currentScroll += scrollStep; // how much scrolled
            Thread.sleep(0);
        }
        // look through product images (8)
        for (int i = 1; i <= 8; i++) {
            driver.findElement(By.xpath("//*[@id=\"hero-image\"]/div[10]/button[2]")).click();
            Thread.sleep(1000);
        }
        Thread.sleep(2000);
        //driver.quit();
    }

    @Test(priority = 2)
    public void checkDifferentShoe() throws InterruptedException {
        // like a customer checking a shoe, customizing it
        // and adding to bag (not here, nike has bot detection)
        driver.get("https://www.nike.com/w/womens-shoes-5e1x6zy7ok");
        // click sort by
        driver.findElement(By.xpath("//*[@id=\"dropdown-btn\"]/span[1]/span[1]")).click();
        Thread.sleep(2000);
        // click low to high
        driver.findElement(By.xpath("//*[@id=\"sort-options\"]/button[4]")).click();
        Thread.sleep(2000);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // our product
        String targetLink = "Nike Renew In-Season TR 12";

        int scrollStep = 20; // pixels per scroll
        int maxScroll = 100000; // limit total scroll range
        int currentScroll = 0; // current scroll

        while (currentScroll < maxScroll) {
            try { // try to find product
                WebElement target = driver.findElement(By.linkText(targetLink));

                // check for shoe product based on boundaries
                Boolean isInView = (Boolean) js.executeScript(
                        "var rect = arguments[0].getBoundingClientRect();" +
                                "return (rect.top >= 0 && rect.bottom <= window.innerHeight);",
                        target
                );
                // click on product if in view
                if (isInView) {
                    js.executeScript("arguments[0].click();", target);
                    break;
                }
            } catch (NoSuchElementException | StaleElementReferenceException ignored) {}
            // scroll by pre defined pixels (for more natural scroll)
            js.executeScript("window.scrollBy(0, arguments[0]);", scrollStep);
            currentScroll += scrollStep; // how much scrolled
            Thread.sleep(0);
        }
    Thread.sleep(3000);
        // click to go to product video
    driver.findElement(By.xpath("//button[@aria-label='Next product image']")).click();
    Thread.sleep(3000);
    driver.findElement(By.xpath("//button[@aria-label='Next product image']")).click();
    // click to go to size guide link
    driver.findElement(By.xpath("//*[@id=\"size-selector\"]/fieldset/legend/a/span")).click();
    // window handling
    Set<String> allWindows = driver.getWindowHandles();
    ArrayList<String> windowList = new ArrayList<>(allWindows);

    String firstWindow = windowList.get(0);
    String newWindow = windowList.get(1);

    // switch to new window
    driver.switchTo().window(newWindow);
    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 1500);");
    Thread.sleep(1500);
    // close window
    driver.close();
    // back to og window
    driver.switchTo().window(firstWindow);
    Thread.sleep(2000);
    // select show size
    driver.findElement(By.xpath("//*[@id=\"size-selector\"]/fieldset/div[1]/div/div[5]/label")).click();
    Thread.sleep(1000);
    // add to bag (not really, error cause bot detection)
    driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div[2]/div[3]/div[5]/div[1]/button")).click();
    Thread.sleep(5000);
    //driver.quit();

    }

    @Test(priority = 3)
    public void confirmCartShoe() throws InterruptedException, IOException {
        driver.get("https://nike.com");
        // click cart button
        driver.findElement(By.xpath("//*[@id=\"gen-nav-commerce-header-v2\"]/nav/header/div/div/div[3]/div/a[2]")).click();
        Thread.sleep(2000);
        // take screenshot of cart
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File src = screenshot.getScreenshotAs(OutputType.FILE);
        // store ss in downloads
        File des = new File("screenshot\\shoeincart.png");
        FileHandler.copy(src, des);
        Thread.sleep(1000);
        //driver.quit();

    }
    @Test(priority = 4)
    public void uploadPicture() throws InterruptedException, IOException {
        driver.get("https://www.nike.com/t/renew-in-season-tr-12-womens-training-shoes-bttk75/DD9301-005");
        //driver.navigate().back();
        Thread.sleep(3000);
        // scroll down to load page
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 1500);");
        Thread.sleep(5000);
        driver.switchTo().defaultContent(); // to prepare for iframe switching


        // find iframe (to upload photo)
        WebElement widgetIframe = driver.findElement(By.cssSelector("iframe[id^='pixlee_widget_iframe']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", widgetIframe); // centered
        Thread.sleep(1000);
        // switch to iframe to interact with elements within it
        driver.switchTo().frame(widgetIframe);
        // click upload button
        driver.findElement(By.cssSelector("#upload_now")).click();
        Thread.sleep(2000);
        // for more iframe switching
        driver.switchTo().defaultContent();
        // wait for i frame to load in or its not gonna work
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait2.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe[id^='pixlee_uploader']")));
        Thread.sleep(2000);
        // click to upload photo from desktop
        driver.findElement(By.cssSelector("#source_desktop")).click();
        Thread.sleep(2000);
        // upload image from downloads
        WebElement upload = driver.findElement(By.xpath("//input[@type='file']"));
        String imagePath = System.getProperty("user.home") + "\\Downloads\\shoeimage.png";
        upload.sendKeys(imagePath);
        Thread.sleep(2000);
        // type review in
        driver.findElement(By.xpath("//*[@id=\"social_review_wrapper\"]/div/div[1]/textarea"))
                .sendKeys("SUPER NICE SHOES! I love them!");
        Thread.sleep(2000);
        // click next
        driver.findElement(By.xpath("//*[@id=\"next\"]")).click();
        Thread.sleep(2000);
        // enter fake name
        driver.findElement(By.xpath("//*[@id=\"fullname_input_text\"]")).sendKeys("Fake N.");
        Thread.sleep(2000);
        // enter fake email
        driver.findElement(By.xpath("//*[@id=\"email_input_text\"]")).sendKeys("FakeEmail@gmail.com");
        Thread.sleep(2000);
        // enter country
        driver.findElement(By.xpath("//*[@id=\"form_wrapper\"]/div[4]/input")).sendKeys("US");
        Thread.sleep(2000);
        // agree to conditions
        driver.findElement(By.xpath("//*[@id=\"client_tos_checkbox\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"additional_tos_field_0\"]")).click();
        Thread.sleep(2000);
        // exit out of review (just testing to input it)
        driver.findElement(By.xpath("//button[@id='close' and @aria-label='Close']")).click();
        Thread.sleep(2000);
        // confirm exit
        driver.findElement(By.xpath("//*[@id=\"yes\"]")).click();
        Thread.sleep(2000);
       // driver.quit();


    }
    @Test(priority = 5)
    public void testRecommendation() throws InterruptedException, IOException {
        driver.get("https://www.nike.com/t/renew-in-season-tr-12-womens-training-shoes-bttk75/DD9301-005");
        // Scroll to the recommendation section
        // our product
        JavascriptExecutor js = (JavascriptExecutor) driver;

        String targetLink = "#__next > main > div.nds-grid.pdp-grid.css-qqclnk.ehf3nt20 > div.recommendations-carousel.mt12-sm.css-1lmtre9.e4lt99o0.nds-grid-item";

        int scrollStep = 20; // pixels per scroll
        int maxScroll = 100000; // limit total scroll range
        int currentScroll = 0; // current scroll

        while (currentScroll < maxScroll) {
            try {
                WebElement target = driver.findElement(By.cssSelector(targetLink));

                // check for shoe product based on boundaries, center so we can see scroll bar
                Boolean isInView = (Boolean) js.executeScript(
                        "var rect = arguments[0].getBoundingClientRect();" +
                                "var viewCenter = (window.innerHeight / 10) - 200;" +
                                "return (rect.top <= viewCenter && rect.bottom >= viewCenter);",
                        target
                );

                if (isInView) {
                    // use JavaScript click to avoid intercept issue
                    js.executeScript("arguments[0].click();", target);
                    break;
                }
                // for issues
            } catch (NoSuchElementException | StaleElementReferenceException ignored) {}
            // scroll by pre defined pixels (for more natural scroll)
            js.executeScript("window.scrollBy(0, arguments[0]);", scrollStep);
            currentScroll += scrollStep; // how much scrolle
            Thread.sleep(0);
        }

        Thread.sleep(2000);
        // slider at bottom
        WebElement slider = driver.findElement(By.cssSelector("ul.slider"));
        // slide to right
        for (int i = 0; i < 6; i++) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft += 300;", slider);
            Thread.sleep(1500);
        }
        // slide to left
        for (int i = 0; i < 6; i++) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft -= 300;", slider);
            Thread.sleep(1500);
        }
        //driver.quit();
    }

    @AfterMethod
    public void closeBrowser () throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();

    }
}