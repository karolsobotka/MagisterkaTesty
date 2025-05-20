const { Given, When, Then } = require('@cucumber/cucumber');
const { Builder, By, until } = require('selenium-webdriver');
const assert = require('assert');

let driver;

Given('I open Google homepage', async function () {
    driver = await new Builder().forBrowser('edge').build();
    await driver.get('https://www.google.com');
});

When('I search for {string}', async function (query) {
    const searchBox = await driver.findElement(By.name('q'));
    await searchBox.sendKeys(query);
    await searchBox.submit();
});

Then('the page title should contain {string}', async function (query) {
    await driver.wait(until.titleContains(query), 5000);
    const title = await driver.getTitle();
    assert.ok(title.includes(query));
    await driver.quit();
});
