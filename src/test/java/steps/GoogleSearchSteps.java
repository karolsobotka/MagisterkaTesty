package steps;

import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;

public class GoogleSearchSteps {

    private WebDriver driver;

    @Given("I open Google homepage")
    public void i_open_google_homepage() {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        driver.get("https://www.google.com");
    }

    @When("I search for {string}")
    public void i_search_for(String query) {
        driver.findElement(By.xpath("//*[@id=\"L2AGLb\"]/div")).click();
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys(query);
        searchBox.submit();
    }

    @Then("the page title should contain {string}")
    public void the_page_title_should_contain(String query) {
        // Wait a bit for page to load (simple wait)
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String title = driver.getTitle();
        Assert.assertTrue(title.contains(query));
        driver.quit();
    }
}
