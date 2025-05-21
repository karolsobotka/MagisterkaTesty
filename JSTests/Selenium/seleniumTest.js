const {Builder, By, Key, until} = require('selenium-webdriver');
const edge = require('selenium-webdriver/edge');
const assert = require('assert');

describe('Buy Argus All-Weather Tank - Magento Demo (Edge)', function () {
    this.timeout(60000);

    let driver;

    before(async () => {
        let options = new edge.Options();
        options.addArguments('start-maximized');

        driver = await new Builder()
            .forBrowser('MicrosoftEdge')
            .setEdgeOptions(options)
            .build();
    });

    after(async () => {
        if (driver) {
            await driver.quit();
        }
    });

    it('should search and buy Argus All-Weather Tank', async () => {
        await driver.get('https://magento.softwaretestingboard.com/');

        await driver.sleep(1000);
        try {
            const acceptBtn = await driver.wait(until.elementLocated(By.id('accept-btn')), 10000);
            await driver.wait(until.elementIsVisible(acceptBtn), 5000);
            await driver.wait(until.elementIsEnabled(acceptBtn), 5000);
            await acceptBtn.click();

        } catch (e) {
            console.log('Cookie banner not found or already accepted.');
        }

        const searchBox = await driver.findElement(By.name('q'));
        await searchBox.sendKeys('argus', Key.RETURN);

        const productImage = await driver.wait(
            until.elementLocated(By.css('img[alt="Argus All-Weather Tank"]')),
            10000
        );
        await productImage.click();

        await driver.wait(until.elementLocated(By.id('option-label-size-143-item-166')), 5000).click();
        await driver.findElement(By.id('option-label-color-93-item-52')).click();

        await driver.findElement(By.id('product-addtocart-button')).click();

        await driver.wait(until.elementLocated(By.css('div.message-success')), 10000);
        await driver.wait(until.elementIsVisible(driver.findElement(By.css('a.action.showcart'))), 5000).click();

        const viewCart = await driver.wait(
            until.elementLocated(By.xpath("//span[text()='View and Edit Cart']")),
            10000
        );
        await viewCart.click();


        const proceedToCheckout = await driver.wait(
            until.elementLocated(By.xpath("//*[@id=\"maincontent\"]/div[3]/div/div[2]/div[1]/ul/li[1]/button")),
            10000
        );
        await proceedToCheckout.click();

        await driver.wait(until.elementLocated(By.name('street[0]')), 10000).sendKeys('123 Test Street');
        await driver.findElement(By.id('customer-email')).sendKeys('john.doe@test.com');
        await driver.findElement(By.name('firstname')).sendKeys('John');
        await driver.findElement(By.name('lastname')).sendKeys('Doe');
        await driver.findElement(By.name('city')).sendKeys('Testville');
        await driver.findElement(By.name('postcode')).sendKeys('12345');
        await driver.findElement(By.name('telephone')).sendKeys('1234567890');
        await driver.findElement(By.name('country_id')).sendKeys('United States');
        await driver.findElement(By.name('region_id')).sendKeys('New York');

        await driver.findElement(By.name('ko_unique_1')).click();

        await driver.wait(until.elementLocated(By.css('button.continue')), 10000).click();

        await driver.sleep(2000);

        const placeOrder = await driver.wait(
            until.elementLocated(By.xpath("//*[@title='Place Order']")),
            10000
        );
        await placeOrder.click();
        await driver.sleep(4000);

        const successMsg = await driver.wait(
            until.elementLocated(By.css('h1.page-title')),
            15000
        );
        const text = await successMsg.getText();
        assert.strictEqual(text.trim(), 'Thank you for your purchase!');
    });
});
