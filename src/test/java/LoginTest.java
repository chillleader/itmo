import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.MainPage;

import java.util.concurrent.TimeUnit;
import static org.example.PropertyReader.*;

public class LoginTest {

    private static WebDriver chromeDriver;
    private static WebDriver firefoxDriver;

    @BeforeAll
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", getProperty("driver.chrome"));
        System.setProperty("webdriver.gecko.driver", getProperty("driver.gecko"));
        chromeDriver = new ChromeDriver();
        chromeDriver.manage().window().maximize();
        chromeDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        firefoxDriver = new FirefoxDriver();
        firefoxDriver.manage().window().maximize();
        firefoxDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void loginTestChrome() {
        MainPage mainPage = new MainPage(chromeDriver);
        mainPage.login(getProperty("first.login"), getProperty("first.password"));
        mainPage.getLogoutButton().click();
        Assertions.assertTrue(mainPage.getLoginButton().isDisplayed());
    }

    @Test
    public void loginTestFirefox() {
        MainPage mainPage = new MainPage(firefoxDriver);
        mainPage.login(getProperty("first.login"), getProperty("first.password"));
        mainPage.getLogoutButton().click();
        Assertions.assertTrue(mainPage.getLoginButton().isDisplayed());
    }

    @AfterAll
    public static void tearDown() {
        chromeDriver.quit();
        firefoxDriver.quit();
    }
}
