package selenium;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.time.Duration;

import static org.testng.AssertJUnit.assertEquals;

public class SeleniumTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeTest
    static void setupClass() {
        WebDriverManager.edgedriver().setup();
    }


    @BeforeMethod
    public void setUp() {
        // Set path to chromedriver if necessary
        driver = new EdgeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void testBuyArgusAllWeatherTank() throws InterruptedException {
        driver.get("https://magento.softwaretestingboard.com/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("accept-btn")));

        element.click();
        WebElement search = driver.findElement(By.name("q"));
        search.click();
        search.sendKeys("argus"+Keys.RETURN);

        driver.findElement(By.xpath("//*[@id=\"maincontent\"]/div[3]/div[1]/div[2]/div[2]/ol/li/div/a/span/span/img")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("option-label-size-143-item-166")));
        driver.findElement(By.id("option-label-size-143-item-166")).click();
        driver.findElement(By.id("option-label-color-93-item-52")).click();

        driver.findElement(By.id("product-addtocart-button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.message-success")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.action.showcart"))).click();

        WebElement checkoutBtn = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//span[text()='View and Edit Cart']")));
        checkoutBtn.click();

        WebElement proceed = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "//*[@id=\"maincontent\"]/div[3]/div/div[2]/div[1]/ul/li[1]/button")));
        proceed.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("street[0]"))).sendKeys("123 Test Street");
        driver.findElement(By.id("customer-email")).sendKeys("john.doe@test.com");
        driver.findElement(By.name("firstname")).sendKeys("John");
        driver.findElement(By.name("lastname")).sendKeys("Doe");
        driver.findElement(By.name("city")).sendKeys("Testville");
        driver.findElement(By.name("postcode")).sendKeys("12345");
        driver.findElement(By.name("telephone")).sendKeys("1234567890");


        WebElement countryDropdown = driver.findElement(By.name("country_id"));
        countryDropdown.sendKeys("United States");

        WebElement regionDropdown = driver.findElement(By.name("region_id"));
        regionDropdown.sendKeys("New York");

        driver.findElement(By.name("ko_unique_1")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.continue"))).click();


        Thread.sleep(2000);
        WebElement placeOrderButton =
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@title='Place Order']")));
        placeOrderButton.click();

        Thread.sleep(12000);

        WebElement successHeading = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("h1.page-title")));

        String confirmationText = successHeading.getText().trim();
        assertEquals("Thank you for your purchase!", confirmationText);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
