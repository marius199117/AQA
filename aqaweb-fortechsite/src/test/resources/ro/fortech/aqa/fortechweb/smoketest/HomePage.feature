Feature: Home Page Section - Smoke Tests

  Scenario: Startup
    Given I open the browser

  Scenario: Home Page - Fortech MainPage is Loaded
    Given I Navigate To "home" Page
    Then I Verify That "home" Page Is Loaded

  Scenario: Home Page - Software Outsourcing Page is Loaded
    Given I Navigate To "home" Page
    When I Click On "homepage.wedo" Button
    Then I Verify That "page.outsourcing" Page Is Loaded

  Scenario Outline: Home Page - They Choose Us over <anchorToHoverOn>
    Given I Navigate To "home" Page
    When I Scroll To The "theyChoseUs.title" Area
    And I Hover Over "<anchorToHoverOn>"
    Then I Verify That "<Element>" Changes Color

    Examples:
      | anchorToHoverOn                     | Element                             |
      | theyChoseUs.novotoxGroup            | theyChoseUs.novotoxGroup            |
      | theyChoseUs.enciteTechnologiesGroup | theyChoseUs.enciteTechnologiesGroup |
      | theyChoseUs.globalBrandsGroup       | theyChoseUs.globalBrandsGroup       |
      | theyChoseUs.leasecakeGroup          | theyChoseUs.leasecakeGroup          |
      | theyChoseUs.crmGroup                | theyChoseUs.crmGroup                |
      | theyChoseUs.stiConsultingGroup      | theyChoseUs.stiConsultingGroup      |

  Scenario: Home Page - Video Banner - Learn more
    Given I Navigate To "home" Page
    When I Scroll To The "videoBanner.masteryInSoftwareEngineering" Area
    And I Click On "videoBanner.learnMore" Button
    Then I Verify That "page.outsourcing" Page Is Loaded

  Scenario Outline: Home Page - We Can Help With - <elementToClickOn> is loaded
    Given I Navigate To "home" Page
    When I Scroll To The "weCanHelpWith.title" Area
    When I Click On "<elementToClickOn>" Button
    Then I Verify That "<url>" Page Is Loaded In Another Tab

    Examples:
      | elementToClickOn                               | url                                       |
      | weCanHelpWith.nearshoreSoftwareOutsourcing     | page.nearshoreSoftwareOutsourcing.url     |
      | weCanHelpWith.offshoreSoftwareOutsourcing      | page.offshoreSoftwareOutsourcing.url      |
      | weCanHelpWith.customWebDevelopment             | page.customWebDevelopment.url             |
      | weCanHelpWith.softwareTestingOutsourcing       | page.softwareTestingOutsourcing.url       |
      | weCanHelpWith.softwareForTheAutomotiveIndustry | page.softwareForTheAutomotiveIndustry.url |
      | weCanHelpWith.ioTSoftwareSolutions             | page.ioTSoftwareSolutions.url             |
      | weCanHelpWith.bespokeMobileApp                 | page.mobileAppDev.url                     |
      | weCanHelpWith.healthcareSoftwareDevelopment    | page.healthcareSoftwareDevelopment.url    |

  Scenario Outline: Home Page - <readMore> News & Insights
    Given I Navigate To "home" Page
    When I Scroll To The "newsAndInsights.title" Area
    And I Click On "<readMore>" Button
    And I Click On "newsAndInsights.backToAllPosts" Button
    Then I Verify That "page.blog" Page Is Loaded

    Examples:
      | readMore                             |
      | newsAndInsights.firstReadMoreButton  |
      | newsAndInsights.secondReadMoreButton |
      | newsAndInsights.thirdReadMoreButton  |

  Scenario Outline: Home Page - We Do - <elementToClickOn> - Learn more
    Given I Navigate To "home" Page
    When I Scroll To The "weDo.title" Area
    When I Click On "<elementToClickOn>" Button
    Then I Verify That "<url>" Page Is Loaded

    Examples:
      | elementToClickOn                           | url                       |
      | weDo.customerSoftwareArea.button.learnMore | page.customerSoftware.url |
      | weDo.devOpsArea.button.learnMore           | page.devOps.url           |

  Scenario: Home Page - Success Stories - Discover More
    Given I Navigate To "home" Page
    When I Scroll To The "successStories.area" Area
    And I Click On "successStories.discoverMore" Button
    Then I Verify That "page.softwareProjectsShowcase" Page Is Loaded

  Scenario: Home Page - Contact Page - Submit Request with mandatory fields
    Given I Navigate To "home" Page
    When I Scroll To The "footer" Area
    When I Click On "top.anchor.contact" Button
    And I Insert "Name" Keywords Into "contactForm.yourName"
    And I Insert "Organization" Keywords Into "contactForm.organization"
    And I Insert "test@email.com" Keywords Into "contactForm.email"
    When I Scroll To The "contactForm.email" Area
    And I Scroll To The "contactForm.learnAboutUs" Area
    And I Click On "contactForm.consentCheckbox" Button
    And I Click On "contactForm.agreementCheckbox" Button
    When I Scroll To The "contactForm.consentCheckbox" Area
    And I Click On "contactForm.submitButton" Button
    Then I Verify That ""contactForm.emailNotification" was sent


  Scenario: Teardown
    Given I close the browser

	