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
    service = EdgeService()
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
    driver.get("https://magento.softwaretestingboard.com/")


    accept_button = WebDriverWait(driver, 10).until(
    EC.element_to_be_clickable((By.ID, "accept-btn")))
    accept_button.click()

    search_box = WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.ID, "search"))
    )
    search_box.send_keys("Argus All-Weather Tank")
    search_box.submit()

    product = WebDriverWait(driver, 10).until(
        EC.element_to_be_clickable((By.LINK_TEXT, "Argus All-Weather Tank"))
    )
    product.click()

    size = wait_until_clickable(driver, By.ID, "option-label-size-143-item-166")
    size.click()

    color = wait_until_clickable(driver, By.ID, "option-label-color-93-item-52")
    color.click()

    add_to_cart = wait_until_clickable(driver, By.ID, "product-addtocart-button")
    add_to_cart.click()

    WebDriverWait(driver, 10).until(
        EC.text_to_be_present_in_element((By.CLASS_NAME, "counter-number"), "1")
    )

    cart_icon = wait_until_clickable(driver, By.CLASS_NAME, "showcart")
    cart_icon.click()

    checkout_button = wait_until_clickable(driver, By.XPATH, "//button[contains(., 'Proceed to Checkout')]")
    checkout_button.click()

    assert "checkout" in driver.current_url.lower()


    email_input = WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.ID, "customer-email"))
    )
    email_input.send_keys("john.doe@test.com")

    firstname_input = WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.NAME, "firstname"))
    )
    firstname_input.send_keys("John")

    lastname_input = WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.NAME, "lastname"))
    )
    lastname_input.send_keys("Doe")

    street_input = WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.NAME, "street[0]"))
    )
    street_input.send_keys("Test St")

    city_input = WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.NAME, "city"))
    )
    city_input.send_keys("Testville")

    postcode_input = WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.NAME, "postcode"))
    )
    postcode_input.send_keys("12345")

    telephone_input = WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.NAME, "telephone"))
    )
    telephone_input.send_keys("1234567890")

    country_input = WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.NAME, "country_id"))
    )
    country_input.send_keys("United States")

    region = WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.NAME, "region_id"))
    )
    region.send_keys("New York")

    shipping_method_radio_button = WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.NAME, "ko_unique_1"))
    )
    shipping_method_radio_button.click()

    continue_button = WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.CSS_SELECTOR, "button.continue"))
    )
    continue_button.click()

    order_button = WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.XPATH, "//*[@title='Place Order']"))
    )
    time.sleep(1)

    order_button.click()

    time.sleep(5)

    confirmation = WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.CSS_SELECTOR, "h1.page-title"))
    )

    assert "Thank you for your purchase!" in confirmation.text

