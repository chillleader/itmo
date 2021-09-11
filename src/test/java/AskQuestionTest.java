import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AskQuestionPage;
import pages.MainPage;

import java.util.concurrent.TimeUnit;

import static org.example.PropertyReader.*;

public class AskQuestionTest {

    private static WebDriver chromeDriver;
    private static WebDriver firefoxDriver;

    @BeforeAll
    public static void setUp() {
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
    public void testAskQuestionChrome() {
        MainPage mainPage = new MainPage(chromeDriver);
        mainPage.login(getProperty("first.login"), getProperty("first.password"));
        AskQuestionPage askQuestionPage = new AskQuestionPage(chromeDriver);
        askQuestionPage.askQuestionAboutComputers("Можно ли автоматизировать действия в браузере?",
                "Как написать программу, которая сама кликает мышкой?");
        WebDriverWait wait = new WebDriverWait(chromeDriver, 10);
        wait.until(ExpectedConditions.titleContains("Можно ли автоматизировать действия в браузере"));
    }

    @Test
    public void testAskQuestionFirefox() {
        MainPage mainPage = new MainPage(firefoxDriver);
        mainPage.login(getProperty("second.login"), getProperty("second.password"));
        AskQuestionPage askQuestionPage = new AskQuestionPage(firefoxDriver);
        askQuestionPage.askQuestionAboutComputers("Можно ли автоматизировать действия в браузере?",
                "Как написать программу, которая сама кликает мышкой?");
        WebDriverWait wait = new WebDriverWait(firefoxDriver, 10);
        wait.until(ExpectedConditions.titleContains("Можно ли автоматизировать действия в браузере"));
    }

    @AfterAll
    public static void tearDown() {
        chromeDriver.quit();
        firefoxDriver.quit();
    }
}
