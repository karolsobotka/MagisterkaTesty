using Microsoft.Playwright;
using NUnit.Framework;
using System.Threading.Tasks;

namespace PlaywrightTests
{
    [TestFixture]
    public class PlaywrightTest
    {
        private static IPlaywright _playwright;
        private static IBrowser _browser;
        private IBrowserContext _context;
        private IPage _page;

        [OneTimeSetUp]
        public async Task SetupClass()
        {
            _playwright = await Playwright.CreateAsync();
            _browser = await _playwright.Chromium.LaunchAsync(new BrowserTypeLaunchOptions
            {
                Headless = false
            });
        }

        [SetUp]
        public async Task SetUp()
        {
            _context = await _browser.NewContextAsync();
            _page = await _context.NewPageAsync();
        }

        [Test]
        public async Task TestBuyArgusAllWeatherTank()
        {
            await _page.GotoAsync("https://magento.softwaretestingboard.com/");
            await _page.WaitForSelectorAsync("#accept-btn");

            var acceptBtn = _page.Locator("#accept-btn");
            if (await acceptBtn.IsVisibleAsync())
            {
                await acceptBtn.ClickAsync();
            }

            await _page.FillAsync("input[name='q']", "argus");
            await _page.Keyboard.PressAsync("Enter");

            await _page.Locator("img[alt='Argus All-Weather Tank']").ClickAsync();

            await _page.Locator("#option-label-size-143-item-166").ClickAsync();
            await _page.Locator("#option-label-color-93-item-52").ClickAsync();
            await _page.Locator("#product-addtocart-button").ClickAsync();

            await _page.WaitForSelectorAsync("div.message-success");

            await _page.Locator("a.action.showcart").ClickAsync();
            await _page.Locator("span:has-text('View and Edit Cart')").ClickAsync();
            await _page.WaitForTimeoutAsync(1000); 

            await _page.Locator("span:has-text('Proceed to Checkout')").ClickAsync();

            await _page.WaitForSelectorAsync("input#customer-email");

            await _page.FillAsync("input#customer-email", "john.doe@test.com");
            await _page.FillAsync("input[name='firstname']", "John");
            await _page.FillAsync("input[name='lastname']", "Doe");
            await _page.FillAsync("input[name='street[0]']", "123 Test Street");
            await _page.FillAsync("input[name='city']", "Testville");
            await _page.FillAsync("input[name='postcode']", "12345");
            await _page.FillAsync("input[name='telephone']", "1234567890");
            await _page.SelectOptionAsync("select[name='country_id']", new SelectOptionValue { Label = "United States" });
            await _page.SelectOptionAsync("select[name='region_id']", new SelectOptionValue { Label = "New York" });

            await _page.Locator("input[name='ko_unique_1']").ClickAsync();
            await _page.Locator("button.continue").ClickAsync();

            await _page.WaitForLoadStateAsync();

            await _page.Locator("button[title='Place Order']").ClickAsync();

            await _page.WaitForTimeoutAsync(3000);
            await _page.WaitForSelectorAsync("h1.page-title");

            var confirmationText = (await _page.TextContentAsync("h1.page-title")).Trim();

            Assert.That(confirmationText, Is.EqualTo("Thank you for your purchase!"));
        }

        [TearDown]
        public async Task TearDown()
        {
            if (_context != null)
            {
                await _context.CloseAsync();
            }
        }

        [OneTimeTearDown]
        public async Task Cleanup()
        {
            if (_browser != null)
            {
                await _browser.CloseAsync();
            }
            if (_playwright != null)
            {
                _playwright.Dispose();
            }
        }
    }
}
