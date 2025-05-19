import pytest
from playwright.sync_api import sync_playwright, expect

@pytest.fixture(scope="session")
def playwright_browser():
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=False)
        yield browser
        browser.close()

@pytest.fixture()
def page(playwright_browser):
    context = playwright_browser.new_context()
    page = context.new_page()
    yield page
    context.close()

def test_buy_argus_all_weather_tank(page):
    page.goto("https://magento.softwaretestingboard.com/")
    page.wait_for_selector("#accept-btn")

    accept_btn = page.locator("#accept-btn")
    if accept_btn.is_visible():
        accept_btn.click()

    page.locator("input[name='q']").click()
    page.fill("input[name='q']", "argus")
    page.keyboard.press("Enter")

    page.locator("img[alt='Argus All-Weather Tank']").click()

    page.locator("#option-label-size-143-item-166").click()
    page.locator("#option-label-color-93-item-52").click()

    page.locator("#product-addtocart-button").click()

    page.wait_for_selector("div.message-success")

    page.locator("a.action.showcart").click()
    page.locator("span:has-text('View and Edit Cart')").click()
    page.wait_for_timeout(1000)

    page.locator("span:has-text('Proceed to Checkout')").click()

    page.wait_for_selector("input#customer-email")
    page.fill("input#customer-email", "john.doe@test.com")
    page.fill("input[name='firstname']", "John")
    page.fill("input[name='lastname']", "Doe")
    page.fill("input[name='street[0]']", "123 Test Street")
    page.fill("input[name='city']", "Testville")
    page.fill("input[name='postcode']", "12345")
    page.fill("input[name='telephone']", "1234567890")
    page.select_option("select[name='country_id']", label="United States")
    page.select_option("select[name='region_id']", label="New York")

    page.locator("input[name='ko_unique_1']").click()
    page.locator("button.continue").click()

    page.wait_for_load_state("networkidle")

    page.locator("button[title='Place Order']").click()

    page.wait_for_timeout(3000)
    page.wait_for_selector("h1.page-title")

    confirmation_text = page.locator("h1.page-title").text_content().strip()
    assert confirmation_text == "Thank you for your purchase!"
