import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ShoppingDemoSiteTest {

    public WebDriver driver;
    public WebDriverWait wait;
    public JavascriptExecutor jse;
    public Actions actions;

    @BeforeClass
    @Parameters({"browser"})
    void setUp(String browser) {
        if (browser.equals("Chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            System.out.println("***** Test initiazing : CHROME BROWSER *****");
        } else if (browser.equals("Edge")){
            WebDriverManager.edgedriver().setup();
            driver=new EdgeDriver();
            System.out.println("***** Test initiazing : EDGE BROWSER *****");
        }

        driver.get("http://automationpractice.com/index.php");
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        actions = new Actions(driver);
    }

    @Test(priority = 1)
    void casualDressesOpen() {
        List<WebElement> menuBar = driver.findElements(By.cssSelector(".sf-menu>li"));
        actions.moveToElement(menuBar.get(0)).perform();
        WebElement casualDresses = driver.findElement(By.xpath("(//a[@title='Casual Dresses'])[1]"));
        wait.until(ExpectedConditions.visibilityOf(casualDresses));
        casualDresses.click();
    }

    @Test(priority = 2)
    void casualDressSelect() {
        jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,500)", "");
        driver.findElement(By.cssSelector(".icon-th-list")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        driver.findElement(By.xpath("(//a[@itemprop='url'])[3]")).click();
    }

    @Test(priority = 3)
    void addToCard() {
        jse.executeScript("window.scrollBy(0,350)", "");
        List<WebElement> dressPhotos = driver.findElements(By.cssSelector("#thumbs_list_frame>li"));

        for (int i = 0; i < dressPhotos.size(); i++) {
            actions.moveToElement(dressPhotos.get(i)).perform();
        }
        driver.findElement(By.xpath("(//div[@class='attribute_list'])[1]")).click();

        actions.sendKeys(Keys.chord(Keys.DOWN, Keys.DOWN)).perform();

        driver.findElement(By.xpath("(//button[@type='submit'])[3]")).click();
        driver.findElement(By.xpath("//a[@title='Proceed to checkout']")).click();
        System.out.println(driver.findElement(By.cssSelector("span.heading-counter")).getText());
    }

    @AfterTest
    void finishTest(){
        System.out.println("***** Test Finished *****");
        driver.quit();
    }
}


