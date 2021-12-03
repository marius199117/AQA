Feature: Footer Section - Smoke Tests

  Scenario: Startup
    Given I open the browser

  Scenario Outline: Footer Section - Verify Top Footer Link <anchorToClickOn>
    Given I Navigate To "home" Page
    When I Scroll To The "footer" Area
    And I Click On "<anchorToClickOn>" Footer Link
    Then I Verify That "<expectedPage>" Page Is Loaded

    Examples:
      | anchorToClickOn        | expectedPage     |
      | top.anchor.outsourcing | page.outsourcing |
      | top.anchor.expertise   | page.expertise   |
      | top.anchor.company     | page.company     |
      | top.anchor.careers     | page.careers     |
      | top.anchor.contact     | page.contact     |

  Scenario Outline: Footer Section - Verify Bottom Footer Icon <classToClickOn>
    Given I Navigate To "home" Page
    When I Scroll To The "footer" Area
    And I Click On "<classToClickOn>" Footer Icon
    Then I Verify That "<expectedHost>" Page Is Loaded In Another Tab

    Examples:
      | classToClickOn        | expectedHost        |
      | bottom.linkedin.class | bottom.linkedin.url |
      | bottom.xing.class     | bottom.xing.url     |
      | bottom.facebook.class | bottom.facebook.url |
      | bottom.twitter.class  | bottom.twitter.url  |

  Scenario: Teardown
    Given I close the browser
	