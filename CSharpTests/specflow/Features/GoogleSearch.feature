Feature: Google Search

  Scenario: Search for 'Cucumber'
    Given I open Google homepage
    When I search for "Cucumber"
    Then the page title should contain "Cucumber"
