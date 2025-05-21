using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Edge;
using OpenQA.Selenium.Support.UI;
using SeleniumExtras.WaitHelpers;  
using System;
using WebDriverManager;
using WebDriverManager.DriverConfigs.Impl;

namespace SeleniumTests
{
    [TestFixture]
    public class SeleniumTest
    {
        private IWebDriver driver;
        private WebDriverWait wait;

        [OneTimeSetUp]
        public void SetupClass()
        {
            new DriverManager().SetUpDriver(new EdgeConfig());
        }

        [SetUp]
        public void SetUp()
        {
            driver = new EdgeDriver();
            driver.Manage().Window.Maximize();
            wait = new WebDriverWait(driver, TimeSpan.FromSeconds(10));
        }

        [Test]
        public void TestBuyArgusAllWeatherTank()
        {
            driver.Navigate().GoToUrl("https://magento.softwaretestingboard.com/");

            var acceptBtn = wait.Until(ExpectedConditions.ElementToBeClickable(By.Id("accept-btn")));
            acceptBtn.Click();

            var search = driver.FindElement(By.Name("q"));
            search.Click();
            search.SendKeys("argus" + Keys.Return);

            var productImage = wait.Until(ExpectedConditions.ElementToBeClickable(By.XPath("//*[@id=\"maincontent\"]/div[3]/div[1]/div[2]/div[2]/ol/li/div/a/span/span/img")));
            productImage.Click();

            wait.Until(ExpectedConditions.ElementIsVisible(By.Id("option-label-size-143-item-166")));
            driver.FindElement(By.Id("option-label-size-143-item-166")).Click();
            driver.FindElement(By.Id("option-label-color-93-item-52")).Click();

            driver.FindElement(By.Id("product-addtocart-button")).Click();

            wait.Until(ExpectedConditions.ElementIsVisible(By.CssSelector("div.message-success")));
            wait.Until(ExpectedConditions.ElementToBeClickable(By.CssSelector("a.action.showcart"))).Click();

            var viewAndEditCart = wait.Until(ExpectedConditions.ElementIsVisible(By.XPath("//span[text()='View and Edit Cart']")));
            viewAndEditCart.Click();

            var proceedToCheckout = wait.Until(ExpectedConditions.ElementIsVisible(By.XPath("//*[@id=\"maincontent\"]/div[3]/div/div[2]/div[1]/ul/li[1]/button")));
            proceedToCheckout.Click();

            wait.Until(ExpectedConditions.ElementIsVisible(By.Name("street[0]"))).SendKeys("123 Test Street");
            driver.FindElement(By.Id("customer-email")).SendKeys("john.doe@test.com");
            driver.FindElement(By.Name("firstname")).SendKeys("John");
            driver.FindElement(By.Name("lastname")).SendKeys("Doe");
            driver.FindElement(By.Name("city")).SendKeys("Testville");
            driver.FindElement(By.Name("postcode")).SendKeys("12345");
            driver.FindElement(By.Name("telephone")).SendKeys("1234567890");

            var countryDropdown = driver.FindElement(By.Name("country_id"));
            countryDropdown.SendKeys("United States");

            var regionDropdown = driver.FindElement(By.Name("region_id"));
            regionDropdown.SendKeys("New York");

            driver.FindElement(By.Name("ko_unique_1")).Click();

            wait.Until(ExpectedConditions.ElementToBeClickable(By.CssSelector("button.continue"))).Click();

            System.Threading.Thread.Sleep(2000);

            var placeOrderButton = wait.Until(ExpectedConditions.ElementToBeClickable(By.XPath("//*[@title='Place Order']")));
            placeOrderButton.Click();

            System.Threading.Thread.Sleep(4000);

            var successHeading = wait.Until(ExpectedConditions.ElementIsVisible(By.CssSelector("h1.page-title")));
            string confirmationText = successHeading.Text.Trim();

            Assert.That(confirmationText, Is.EqualTo("Thank you for your purchase!"));
        }

        [TearDown]
        public void TearDown()
        {
            driver?.Quit();
            driver?.Dispose();
        }
    }
}
