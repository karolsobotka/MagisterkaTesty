const { test, expect, chromium} = require('@playwright/test');

test.describe('Buy Argus All-Weather Tank - Magento Demo', () => {
    test('should search and buy Argus All-Weather Tank', async () => {
        const browser = await chromium.launch({ headless: false });
        const context = await browser.newContext();  
        const page = await context.newPage();

        await page.goto('https://magento.softwaretestingboard.com/');
        await page.waitForTimeout(1000);


        const acceptBtn = page.locator('#accept-btn');
        if (await acceptBtn.isVisible()) {
            await acceptBtn.click();
        }

        const searchBox = page.locator('input[name="q"]');
        await searchBox.fill('argus');
        await searchBox.press('Enter');

        await page.locator('img[alt="Argus All-Weather Tank"]').click();

        await page.locator('#option-label-size-143-item-166').click();
        await page.locator('#option-label-color-93-item-52').click();

        await page.locator('#product-addtocart-button').click();

        await page.locator('div.message-success').waitFor();
        await page.locator('a.action.showcart').click();
        await page.locator('//span[text()="View and Edit Cart"]').click();
        await page.waitForTimeout(1000);

        await page.locator('span:has-text(\'Proceed to Checkout\')').click();

        await page.waitForTimeout(5000);

        await page.fill('//*[@id="customer-email"]', 'john.doe@test.com');
        await page.locator('input[name="firstname"]').fill('John');
        await page.locator('input[name="lastname"]').fill('Doe');
        await page.locator('input[name="street[0]"]').fill('123 Test Street');
        await page.locator('input[name="city"]').fill('Testville');
        await page.locator('input[name="postcode"]').fill('12345');
        await page.locator('input[name="telephone"]').fill('1234567890');
        await page.locator('select[name="country_id"]').selectOption('US');
        await page.locator('select[name="region_id"]').selectOption({ label: 'New York' });

        await page.locator('input[name="ko_unique_1"]').check();
        await page.locator('button.continue').click();

        await page.waitForTimeout(2000);
        await page.locator('//button[@title="Place Order"]').click();

        const successMsg = await page.locator('h1.page-title');
        await expect(successMsg).toHaveText('Thank you for your purchase!');
    });
});
