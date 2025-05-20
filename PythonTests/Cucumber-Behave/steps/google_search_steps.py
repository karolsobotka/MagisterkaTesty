from behave import given, when, then
from selenium import webdriver
from selenium.webdriver.common.by import By
import time

@given('I open Google homepage')
def step_open_google(context):
    context.driver = webdriver.Chrome()
    context.driver.get("https://www.google.com")

@when('I search for "{query}"')
def step_search(context, query):
    context.driver.find_element(By.XPATH, "//*[@id=\"L2AGLb\"]/div").click()
    search_box = context.driver.find_element(By.NAME, "q")
    search_box.send_keys(query)
    search_box.submit()

@then('the page title should contain "{query}"')
def step_check_title(context, query):
    time.sleep(2)  # simple wait
    title = context.driver.title
    assert query in title
    context.driver.quit()
