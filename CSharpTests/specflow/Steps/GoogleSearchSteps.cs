using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;
using TechTalk.SpecFlow;
using System.Threading;


    [Binding]
    public class GoogleSearchSteps
    {
        private IWebDriver driver;

        [Given(@"I open Google homepage")]
        public void GivenIOpenGoogleHomepage()
        {
            driver = new ChromeDriver();
            driver.Navigate().GoToUrl("https://www.google.com");
        }

        [When(@"I search for ""(.*)""")]
        public void WhenISearchFor(string query)
        {
            driver.FindElement(By.xpath("//*[@id=\"L2AGLb\"]/div")).Click();

            var searchBox = driver.FindElement(By.Name("q"));
            searchBox.SendKeys(query);
            searchBox.Submit();
        }

        [Then(@"the page title should contain ""(.*)""")]
        public void ThenThePageTitleShouldContain(string query)
        {
            Thread.Sleep(2000);
            Assert.IsTrue(driver.Title.Contains(query));
            driver.Quit();
        }
    }

