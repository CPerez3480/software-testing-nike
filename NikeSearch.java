import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class NikeSearch {
    static WebDriver driver;
    static int method_count = 1;


    @BeforeClass
    public static void setUpBeforeClass() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.nike.com");
    }

    @BeforeMethod
    public void setUpBeforeMethod() {
        System.out.println("Class NikeSearch method: " + method_count);

    }

    @Test (priority = 1)
    void generalFilterSearchTest() throws InterruptedException {
        driver.findElement(By.id("gn-search-input")).click();

        Thread.sleep(2000);

        driver.findElement(By.id("gn-search-input")).clear();
        driver.findElement(By.xpath("//*[@id=\"gen-nav-commerce-header-v2\"]/nav/header/div/div/div[3]/div/search/form/div/div[4]/div/section/div/div/div/a[1]")).click();

        Thread.sleep(2000);

        JavascriptExecutor executor = (JavascriptExecutor) driver;

        executor.executeScript("window.scrollBy(0,7000)");
        Thread.sleep(2000);
        executor.executeScript("window.scrollBy(0,7000)");
        Thread.sleep(2000);
        executor.executeScript("window.scrollBy(0,7000)");
        Thread.sleep(2000);
        executor.executeScript("window.scrollBy(0,7000)");
        Thread.sleep(2000);
        executor.executeScript("window.scrollBy(0,9000)");
        Thread.sleep(2000);
        executor.executeScript("window.scrollBy(0,7000)");
        Thread.sleep(2000);
        executor.executeScript("window.scrollBy(0,5000)");
        Thread.sleep(2000);
        executor.executeScript("window.scrollBy(0,-1000)");


        List<WebElement> shoes = driver.findElements(By.className("product-card__hero-image"));
        System.out.println("The number of shoes is: " + shoes.size());

        String input = driver.findElement(By.xpath("//*[@id=\"Wall\"]/div/div[3]/header/div/h1/span[3]")).getText();
        String numberOnly = input.replaceAll("[^0-9]", ""); // Removes everything that's not a digit
        int result = Integer.parseInt(numberOnly);

        Assert.assertEquals(shoes.size(), result, "The number of shoes is not the expected one");


    }

    @Test (priority = 2)
    void filterTest() throws InterruptedException {
        WebElement searchBar = driver.findElement(By.id("gn-search-input"));
        searchBar.click();
        Thread.sleep(1000);
        Actions actions = new Actions(driver);
        actions.click(searchBar)
                .keyDown(Keys.CONTROL)
                .sendKeys("a")
                .keyUp(Keys.CONTROL)
                .sendKeys(Keys.DELETE)
                .perform();


        Thread.sleep(1000);
        searchBar.sendKeys("air force 1");
        searchBar.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        WebElement dropdown = driver.findElement(By.id("dropdown-btn"));
        dropdown.click();
        Thread.sleep(1000);
        List<WebElement> dropdownOptions = driver.findElements(By.cssSelector("#sort-options button")); // Create new Select object to select an option
        dropdownOptions.getLast().click(); // Select option with index
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"wallNavFIS\"]/div/div[2]/div/button")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"modal-root\"]/div/div/div/div/div/section/div[2]/div[2]/div/div/div[1]/div[2]/div/div/div[2]/div/ol/li[1]/div")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"modal-root\"]/div/div/div/div/div/section/div[2]/div[2]/div/div/div[2]/div[2]/button/span")).click();
        Thread.sleep(4000);


        List<WebElement> filters = driver.findElements(By.className("trigger-content"));
        filters.getFirst().click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"wallNavFG0\"]/a[1]/div")).click();
        Thread.sleep(2000);

        List<WebElement> shoes = driver.findElements(By.className("product-card__hero-image"));
        System.out.println("The number of shoes is: " + shoes.size());

        String input = driver.findElement(By.className("wall-header__item_count")).getText();
        String numberOnly = input.replaceAll("[^0-9]", ""); // Removes everything that's not a digit
        int result = Integer.parseInt(numberOnly);

        Assert.assertEquals(shoes.size(), result, "The number of shoes is not the expected one");


    }

    @Test (priority = 3)
    void ScreenshotEsteroStore() throws InterruptedException, IOException {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File source = screenshot.getScreenshotAs(OutputType.FILE);
        File destination = new File("screenshot\\shoes.png");
        FileHandler.copy(source, destination);

        Thread.sleep(2000);
    }

    @Test (priority = 4)
    void searchBarInputsTest() throws InterruptedException {
        driver.get("https://www.nike.com");

        WebElement searchBar = driver.findElement(By.id("gn-search-input"));
        searchBar.click();
        Thread.sleep(2000);
        searchBar.sendKeys("");
        Thread.sleep(2000);
        searchBar.sendKeys(Keys.ENTER);
        Thread.sleep(2000);

        searchBar.sendKeys("%$#^*@&(!");
        Thread.sleep(2000);
        searchBar.sendKeys(Keys.ENTER);
        Thread.sleep(2000);

        searchBar = driver.findElement(By.id("gn-search-input"));
        searchBar.click();
        Thread.sleep(2000);
        Actions actions = new Actions(driver);
        actions.click(searchBar)
                .keyDown(Keys.CONTROL)
                .sendKeys("a")
                .keyUp(Keys.CONTROL)
                .sendKeys(Keys.DELETE)
                .perform();
        searchBar.sendKeys("123456789");
        Thread.sleep(2000);
        searchBar.sendKeys(Keys.ENTER);



    }

    @Test (priority = 5)
    void airJordanAndConverseTest() throws InterruptedException {
        JavascriptExecutor executor = (JavascriptExecutor) driver;

        executor.executeScript("window.scrollBy(0,-2000)");
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"gen-nav-commerce-header-v2\"]/nav/div[1]/div/div[1]/nav/ul/li[1]/a")).click();
        Thread.sleep(2000);

        System.out.println("The url is: " + driver.getCurrentUrl());
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.nike.com/jordan");

        driver.findElement(By.xpath("//*[@id=\"gen-nav-commerce-header-v2\"]/nav/div[1]/div/div[1]/nav/ul/li[2]/a")).click();
        Thread.sleep(2000);

        System.out.println("The url is: " + driver.getCurrentUrl());
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.nike.com/w/converse-akmjx");

        driver.findElement(By.xpath("//*[@id=\"Wall\"]/div/div[3]/header/div/nav/button")).click();
        Thread.sleep(2000);

    }


    @AfterMethod
    public void incrementCount() {
        method_count++;
    }

    @AfterClass
    void afterClass() {
        driver.quit();
    }

}
