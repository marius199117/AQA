package ro.fortech.aqa.fortechweb.smoketest;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ro.fortech.aqa.fortechweb.Environment;
import ro.fortech.aqa.fortechweb.Utility;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class CommonDefinitions {
    public static final String SCREENSHOTS_PATH = "./test-logs";

    public static WebDriver driver = null;
    public static Properties properties = Utility.getPropertiesFromFile("ro/fortech/aqa/fortechweb/config.properties");
    public static Environment environment = Environment.newInstance("ro/fortech/aqa/fortechweb/context.json");

    public void wait10AndClick(String xpath) {
        WebDriverWait waiter = new WebDriverWait(driver, 10);
        waiter.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        driver.findElement(By.xpath(xpath)).click();
    }

    public void wait10AndType(String xpathForStatedTextBox, String stringToType) {
        WebDriverWait waiter = new WebDriverWait(driver, 10);
        waiter.until(ExpectedConditions
                .elementToBeClickable(By.xpath(xpathForStatedTextBox)));
        WebElement statedTextBox = findElementByXPath(xpathForStatedTextBox);
        Assert.assertNotNull(statedTextBox);
        statedTextBox.sendKeys(stringToType);
    }

    public void policyProcessing() {
        WebElement acceptAndClose = findElementByXPath(environment.resolve("policy"));
        if (acceptAndClose != null && acceptAndClose.isDisplayed()) {
            acceptAndClose.click();
        }
    }

    public boolean isPageOnHost(String host) {
        boolean isOnHost = false;
        try {
            isOnHost = new URL(driver.getCurrentUrl()).getHost().contains(host);
        } catch (MalformedURLException e) {
        }
        return isOnHost;
    }

    public WebElement findElementByXPath(String xPathToSearchFor) {
        try {
            return driver.findElement(By.xpath(xPathToSearchFor));
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
