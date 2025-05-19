import pytest
from selenium import webdriver
from selenium.webdriver.edge.service import Service as EdgeService
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import time

@pytest.fixture
def driver():
    options = webdriver.EdgeOptions()
    options.add_argument('--start-maximized')
    service = EdgeService()  # Uses msedgedriver from PATH
    driver = webdriver.Edge(service=service, options=options)
    yield driver
    driver.quit()

def wait_until_clickable(driver, by, value, timeout=10):
    wait = WebDriverWait(driver, timeout)
    element = wait.until(EC.presence_of_element_located((by, value)))
    wait.until(EC.visibility_of(element))
    wait.until(EC.element_to_be_clickable((by, value)))
    return element

def test_buy_argus_tank(driver):
    driver.get("https://magento.softwaretestingboard.com/")  # Example URL

    # Example flow — Adjust as needed
    search_box = WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.ID, "search"))
    )
    search_box.send_keys("Argus All-Weather Tank")
    search_box.submit()

    # Click the product
    product = WebDriverWait(driver, 10).until(
        EC.element_to_be_clickable((By.LINK_TEXT, "Argus All-Weather Tank"))
    )
    product.click()

    # Choose size and color (adjust locators as necessary)
    size = wait_until_clickable(driver, By.ID, "option-label-size-143-item-166")
    size.click()

    color = wait_until_clickable(driver, By.ID, "option-label-color-93-item-50")
    color.click()

    add_to_cart = wait_until_clickable(driver, By.ID, "product-addtocart-button")
    add_to_cart.click()

    # Wait for cart update (short delay for UI update)
    WebDriverWait(driver, 10).until(
        EC.text_to_be_present_in_element((By.CLASS_NAME, "counter-number"), "1")
    )

    # Open mini cart
    cart_icon = wait_until_clickable(driver, By.CLASS_NAME, "showcart")
    cart_icon.click()

    # Click Proceed to Checkout
    checkout_button = wait_until_clickable(driver, By.XPATH, "//button[contains(., 'Proceed to Checkout')]")
    checkout_button.click()

    # Optional assertion (you can add more)
    assert "checkout" in driver.current_url.lower()
