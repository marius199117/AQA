Feature: TopMenu Section - Smoke Tests

  Scenario: Startup
    Given I open the browser

  Scenario: Top Menu - Contact Option
    Given I Navigate To "home" Page
    When I Click On "topmenu.button.contact" Button
    Then I Verify That "page.contact" Page Is Loaded

  Scenario Outline: Top Menu - <expectedResult> result Search Option
    Given I Navigate To "home" Page
    When I Click On "topmenu.button.search" Button
    And I Insert "<textToSearchFor>" Keywords Into "topmenu.textbox.search"
    And I Press "Enter" Key
    Then I Verify That "<expectedResult>" Results Are Found

    Examples:
      | textToSearchFor | expectedResult |
      | xmp             | 0              |
      | Testing         | Any            |

  Scenario: Search Results Page - Valid Search
    Given I Navigate To "home" Page
    When I Click On "topmenu.button.search" Button
    And I Insert "Testing" Keywords Into "topmenu.textbox.search"
    And I Press "Enter" Key
    And I Insert "Software Outsourcing" Keywords Into "searchPage.searchTextbox"
    And I Press "Enter" Key
    Then I Verify That "Any" Results Are Found

  Scenario: Seach Results Page - Back To Home Page when No Search Results Found
    Given I Navigate To "home" Page
    When I Click On "topmenu.button.search" Button
    And I Insert "xmp" Keywords Into "topmenu.textbox.search"
    And I Press "Enter" Key
    And I Click On "topmenu.button.backToHomepage" Button
    Then I Verify That "home" Page Is Loaded

  Scenario: Top Menu - Load Home Page From Fortech Logo
    Given I Navigate To "home" Page
    When I Click On "topmenu.button.contact" Button
    And I Click On Fortech Logo
    Then I Verify That "home" Page Is Loaded

  Scenario: Top Menu - Dropdown Menu
    Given I Navigate To "home" Page
    When I Click On "topmenu.button.dropDown" Button
    Then I Verify That "page.dropDown" Element Is Loaded

  Scenario: Teardown
    Given I close the browser