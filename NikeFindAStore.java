import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class NikeFindAStore {
    static WebDriver driver;
    static int method_count = 1;



    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.nike.com");
        driver.findElement(By.linkText("Find a Store")).click();
        Thread.sleep(2000);

    }

    @BeforeMethod
    public void setUpBeforeMethod() throws Exception {
        System.out.println("Class NikeFindAStore method: " + method_count);

    }


    @Test (priority = 1)
    void moveMap() throws Exception {

        WebElement canvas = driver.findElement(By.tagName("canvas"));
        System.out.println("Canvas dimensions: " + canvas.getSize());

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("canvas")));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", canvas);
        Thread.sleep(1000);

        String script = """
            const canvas = arguments[0];
            const rect = canvas.getBoundingClientRect();
            const x = rect.left + rect.width / 2;
            const y = rect.top + rect.height / 2;
            const clickEvent = new MouseEvent('click', {
                clientX: x,
                clientY: y,
                bubbles: true,
                cancelable: true,
                view: window
            });
            canvas.dispatchEvent(clickEvent);
        """;

        ((JavascriptExecutor) driver).executeScript(script, canvas);

        Actions actions = new Actions(driver);
        actions.moveToElement(canvas)
                .clickAndHold()
                .moveByOffset(-200, 100)
                .pause(Duration.ofMillis(750))
                .moveByOffset(-200, 100)
                .release()
                .perform();

        Thread.sleep(3000);

//        WebElement zoomIn = wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.cssSelector("div.mk-bottom-right-controls-container.mk-controls-container-controls-larger > div > div.mk-zoom-in > svg > g > path")));
//        zoomIn.click();
//
//
//
//        Thread.sleep(3000);





    }

    @Test (priority = 2)
    void locationSearchBar() throws InterruptedException {
        WebElement searchLocation =  driver.findElement(By.id("ta-Location_input"));
        searchLocation.click();
        searchLocation.sendKeys("Florida");
        Thread.sleep(2000);
        searchLocation.sendKeys(Keys.ENTER);


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        List<WebElement> stores = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("section.locator-list > section")
        ));
        Thread.sleep(5000);
        stores.get(2).click();
        stores = driver.findElements(By.cssSelector("section.locator-list > a > section"));
        wait.until(ExpectedConditions.elementToBeClickable(stores.getFirst()));
        stores.getFirst().click();
        Thread.sleep(5000);

        driver.navigate().back();
        Thread.sleep(2000);

        searchLocation = driver.findElement(By.id("ta-Location_input"));
        searchLocation.click();
        Actions actions = new Actions(driver);
        actions.click(searchLocation)
                .keyDown(Keys.CONTROL)
                .sendKeys("a")
                .keyUp(Keys.CONTROL)
                .sendKeys(Keys.DELETE)
                .perform();
        searchLocation.sendKeys("New York");
        Thread.sleep(2000);
        searchLocation.sendKeys(Keys.ENTER);
        Thread.sleep(3000);


        stores = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("section.locator-list > section")
        ));
        Thread.sleep(5000);
        stores.get(1).click();
        Thread.sleep(2000);
        stores = driver.findElements(By.cssSelector("section.locator-list > a > section"));
        wait.until(ExpectedConditions.elementToBeClickable(stores.getFirst()));
        stores.getFirst().click();
        Thread.sleep(5000);
        driver.navigate().back();


    }

    @Test(priority = 3)
    void filterLocations() throws InterruptedException {

        WebElement searchLocation = driver.findElement(By.id("ta-Location_input"));
        searchLocation.click();
        Actions actions = new Actions(driver);
        actions.click(searchLocation)
                .keyDown(Keys.CONTROL)
                .sendKeys("a")
                .keyUp(Keys.CONTROL)
                .sendKeys(Keys.DELETE)
                .perform();
        searchLocation.sendKeys("Fort Myers");
        Thread.sleep(2000);
        searchLocation.sendKeys(Keys.ENTER);
        Thread.sleep(3000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement filterButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"store-locator\"]/article/div/section[1]/div[2]/button")
        ));
        filterButton.click();
        Thread.sleep(2000);
        List<WebElement> filters = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("section.locator-filter .storeSearchbar-filterPanel label")
        ));
        Thread.sleep(2000);
        filters.get(1).click();
        wait.until(ExpectedConditions.elementToBeClickable(filterButton));
        filterButton.click();

        List<WebElement> stores = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("section.locator-list > section")
        ));
        Thread.sleep(5000);
        stores.get(1).click();
        Thread.sleep(2000);
        stores = driver.findElements(By.cssSelector("section.locator-list > a > section"));
        wait.until(ExpectedConditions.elementToBeClickable(stores.getFirst()));
        stores.getFirst().click();
        Thread.sleep(5000);
    }

    @Test (priority = 4)
    void storePage() throws InterruptedException, IOException {


        String originalWindow = driver.getWindowHandle();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.findElement(By.linkText("Get Directions")).click();

        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }

        }

        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
        File dstFile = new File("screenshot\\StoreMapScreenShot.png");
        FileHandler.copy(srcFile, dstFile);
        Thread.sleep(2000);
        driver.close();
        driver.switchTo().window(originalWindow);

    }

    @Test (priority = 5)
    void internationalStoresPage() throws InterruptedException, IOException {
        driver.get("https://www.nike.com/retail");
        Thread.sleep(2000);

        driver.findElement(By.linkText("View All Stores")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"store-directory-main\"]/section/div[14]/a/span")).click();
        Thread.sleep(2000);
        WebElement area = driver.findElement(By.xpath("//*[@id=\"store-directory-country\"]/section/div[2]/a/span"));

        String areaText = area.getText();
        String numberOnly = areaText.replaceAll("[^0-9]", ""); // Removes everything that's not a digit
        int result = Integer.parseInt(numberOnly);
        area.click();
        Thread.sleep(3000);

        List<WebElement> stores = driver.findElements(By.className("image-img"));

        System.out.println("The number of store is: " + stores.size());
        Assert.assertEquals(stores.size(), result, "The number of shoes is not the expected one");
    }


    @AfterMethod
    public void incrementCount() throws Exception {
        method_count++;
    }

    @AfterClass
    void afterClass() throws InterruptedException {
        driver.quit();
    }

}
