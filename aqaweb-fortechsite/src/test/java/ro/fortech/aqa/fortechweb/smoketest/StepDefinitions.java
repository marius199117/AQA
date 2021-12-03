package ro.fortech.aqa.fortechweb.smoketest;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StepDefinitions extends CommonDefinitions {

    // GIVEN Steps

    @Given("^I open the browser$")
    public void initialize() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Given("^I close the browser$")
    public void tearDown() {
        driver.close();
        driver.quit();
    }

    @Given("^I Navigate To \"(.*)\" Page$")
    public void i_Navigate_To_Page(String value) {
        policyProcessing();
        // using properties like this means that we can accept either the url or a
        // property key for the url
        driver.navigate().to(properties.getProperty(value, value));
    }

    // WHEN Steps

    @When("^I Scroll To The \"(.*)\" Area$")
    public void i_Scroll_To_The_Area(String area) {
        // In order to bypass selenium click interception generated by javascripts
        // running in the background on the page, using javascriptExecutor in to scroll
        // into view a footer element targeted by xpath
        WebElement areaToGoTo = driver.findElement(By.xpath(environment.resolve(area)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", areaToGoTo);
    }

    @When("^I Click On \"(.*)\" Footer Link$")
    public void i_Click_On_Footer_Link(String text) {
        wait10AndClick(environment.resolve(text));
    }

    @When("^I Click On \"(.*)\" Footer Icon$")
    public void i_Click_On_Footer_Icon(String text) {
        wait10AndClick(environment.resolve(text));
    }

    @When("^I Click On \"(.*)\" Button$")
    public void i_Click_On_Button(String nameOfButtonToClickOn) {
        wait10AndClick(environment.resolve(nameOfButtonToClickOn));
    }

    @When("^I Insert \"(.*)\" Keywords Into \"(.*)\"$")
    public void i_Insert_Keywords(String stringToGoToTextBox, String statedTextBox) {
        wait10AndType(environment.resolve(statedTextBox), stringToGoToTextBox);
    }

    @When("^I Press \"(.*)\" Key$")
    public void i_Press(String keyboardKey) {
        if ("Enter".equals(keyboardKey)) {
            Actions action = new Actions(driver);
            action.sendKeys(Keys.ENTER).build().perform();
        } else {
            fail("Key " + keyboardKey + " not handled!");
        }
    }

    @When("^I Click On Fortech Logo$")
    public void i_Click_On_Fortech_Logo() {
        WebElement logo = findElementByXPath(environment.resolve("homepage.logo"));
        logo.click();
    }

    // THEN Steps

    @Then("^I Verify That \"(.*)\" Page Is Loaded$")
    public void i_Verify_That_Page_Is_Loaded(String currentPage) {
        Assert.assertEquals(properties.getProperty(currentPage, environment.resolve(currentPage)), driver.getCurrentUrl());
    }

    @Then("^I Verify That \"(.*)\" Page Is Loaded In Another Tab$")
    public void i_Verify_That_Page_Is_Loaded_In_Another_Tab(String page) {

        String initialTab = driver.getWindowHandle();
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        tabs.remove(initialTab);

        boolean wasFound = false;
        for (int i = 0; i < tabs.size() && !wasFound; i++) {

            driver.switchTo().window(tabs.get(i));
            wasFound = driver.getCurrentUrl().equalsIgnoreCase(environment.resolve(page));

            if (wasFound) {
                driver.close();
                driver.switchTo().window(initialTab);
            }
        }
        if (!wasFound) {
            fail("Could not find page in another tab");
        }
    }

    @Then("^I Verify That \"(.*)\" Results Are Found$")
    public void i_Verify_That_Expected_Results_Are_Found(String expectedResults) {
        final String XPATH_FOR_EXPECTED_TEXT_RESPONSE = "//span[contains(text(),'%s')]";
        final String RESULTS_FOUND = " Results Found";
        String xPathForExpectedTextResponse = "";
        if ("0".equals(environment.resolve(expectedResults))) {
            xPathForExpectedTextResponse = String.format(XPATH_FOR_EXPECTED_TEXT_RESPONSE, "No Results Found!");
        } else {
            try {
                String resultedTextDisplayed = findElementByXPath(
                        String.format(XPATH_FOR_EXPECTED_TEXT_RESPONSE, RESULTS_FOUND)).getText();
                resultedTextDisplayed = resultedTextDisplayed.substring(0,
                        resultedTextDisplayed.length() - RESULTS_FOUND.length());
                xPathForExpectedTextResponse = String.format(XPATH_FOR_EXPECTED_TEXT_RESPONSE,
                        resultedTextDisplayed + RESULTS_FOUND);
            } catch (NoSuchElementException e) {
                fail("Element <<" + String.format(XPATH_FOR_EXPECTED_TEXT_RESPONSE, RESULTS_FOUND) + ">> not found!");
            }
        }

        WebElement expectedTextResponse = findElementByXPath(xPathForExpectedTextResponse);
        Assert.assertNotNull(expectedTextResponse);
        Assert.assertTrue("Element <" + expectedTextResponse + "is not interactive!", expectedTextResponse.isDisplayed());
    }

    @Then("I Verify That \"(.*)\" Changes Color")
    public void verify_That_Element_Changed_Color(String css) {
        double x = Double.parseDouble(driver.findElement(By.cssSelector(environment.resolve(css))).getCssValue("opacity"));
        assertTrue(x >= 0.4 && x <= 1);
    }

    @Then("^I Verify That \"(.*)\" Element Is Loaded$")
    public void i_Verify_That_Element_Is_Loaded(String element) {
        Assert.assertTrue(findElementByXPath(environment.resolve(element)).isDisplayed());
    }

    @Then("^I Verify That \"(.*)\" was sent")
    public void i_verify_the_email_was_sent(String element) {
        Assert.assertTrue(findElementByXPath(environment.resolve(element)).isDisplayed());
    }

    //AND Steps

    @And("^I Hover Over \"(.*)\"")
    public void i_Hover_Over_An_Element(String hoverElement) throws InterruptedException {
        WebDriverWait waiter = new WebDriverWait(driver, 10);
        waiter.until(ExpectedConditions
                .elementToBeClickable(By.cssSelector(environment.resolve(hoverElement))));
        Actions actions = new Actions(driver);
        WebElement element = driver.findElement(By.cssSelector(environment.resolve(hoverElement)));
        actions.moveToElement(element).perform();
    }

    @After
    public void afterScenario(Scenario scenario) throws IOException {
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Path path = Paths.get(SCREENSHOTS_PATH, scenario.getName() + ".png");
            FileUtils.writeByteArrayToFile(path.toFile(), screenshot);
        }
    }
}

