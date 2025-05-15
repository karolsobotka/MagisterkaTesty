package selenium;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.time.Duration;
public class SeleniumTest {

    WebDriver driver;
    @BeforeTest
    static void setupClass() {
        WebDriverManager.edgedriver().setup();
    }

    @BeforeMethod
    public void setup() {

        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void testEndToEnd(){

        driver.get("https://ultimateqa.com/complicated-page");

        // Fill in the first name
        WebElement firstName = driver.findElement(By.id("et_pb_contact_name_0"));
        firstName.sendKeys("John");

        // Fill in the message
        WebElement message = driver.findElement(By.id("et_pb_contact_message_0"));
        message.sendKeys("This is a test message.");

        // Click on the captcha checkbox (if present)
        WebElement captchaQuestion = driver.findElement(By.className("et_pb_contact_captcha_question"));
        String questionText = captchaQuestion.getText(); // e.g., "3 + 7"
        int answer = evaluateMathExpression(questionText);

        WebElement captchaInput = driver.findElement(By.name("et_pb_contact_captcha_0"));
        captchaInput.sendKeys(String.valueOf(answer));

        // Submit the form
        WebElement submitButton = driver.findElement(By.cssSelector(".et_pb_contact_submit"));
        submitButton.click();
    }
    private static int evaluateMathExpression(String expression) {
        String[] parts = expression.split(" ");
        int num1 = Integer.parseInt(parts[0]);
        String op = parts[1];
        int num2 = Integer.parseInt(parts[2]);

        return switch (op) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            default -> 0;
        };
    }


    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
