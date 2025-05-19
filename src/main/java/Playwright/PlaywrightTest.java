package Playwright;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.SelectOption;
import org.testng.annotations.*;
import static org.testng.Assert.assertEquals;
public class PlaywrightTest {


        private static Playwright playwright;
        private static Browser browser;
        private BrowserContext context;
        private Page page;

        @BeforeClass
        public void setupClass() {
            playwright = Playwright.create();
            browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(false));
        }

        @BeforeMethod
        public void setUp() {
            context = browser.newContext();
            page = context.newPage();
        }

        @Test
        public void testBuyArgusAllWeatherTank() {
            page.navigate("https://magento.softwaretestingboard.com/");
            page.waitForSelector("#accept-btn");

            Locator acceptBtn = page.locator("#accept-btn");
            if (acceptBtn.isVisible()) {
                acceptBtn.click();
            }

            page.locator("input[name='q']").click();
            page.fill("input[name='q']", "argus");
            page.keyboard().press("Enter");

            page.locator("img[alt='Argus All-Weather Tank']").click();

            page.locator("#option-label-size-143-item-166").click();
            page.locator("#option-label-color-93-item-52").click();
            page.locator("#product-addtocart-button").click();

            page.waitForSelector("div.message-success");

            page.locator("a.action.showcart").click();
            page.locator("span:has-text('View and Edit Cart')").click();
            page.waitForTimeout(1000);

            page.locator("span:has-text('Proceed to Checkout')").click();

            page.waitForSelector("input#customer-email");

            page.fill("input#customer-email", "john.doe@test.com");
            page.fill("input[name='firstname']", "John");
            page.fill("input[name='lastname']", "Doe");
            page.fill("input[name='street[0]']", "123 Test Street");
            page.fill("input[name='city']", "Testville");
            page.fill("input[name='postcode']", "12345");
            page.fill("input[name='telephone']", "1234567890");
            page.selectOption("select[name='country_id']", new SelectOption().setLabel("United States"));
            page.selectOption("select[name='region_id']", new SelectOption().setLabel("New York"));

            page.locator("input[name='ko_unique_1']").click();
            page.locator("button.continue").click();

            page.waitForLoadState();
            page.locator("button[title='Place Order']").click();
            page.waitForTimeout(3000);
            page.waitForSelector("h1.page-title");
            String confirmationText = page.textContent("h1.page-title").trim();
            assertEquals("Thank you for your purchase!", confirmationText);
        }

        @AfterMethod
        public void tearDown() {
            if (context != null) {
                context.close();
            }
        }

        @AfterClass
        public void cleanup() {
            if (browser != null) {
                browser.close();
            }
            if (playwright != null) {
                playwright.close();
            }
        }
    }

