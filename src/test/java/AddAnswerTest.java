import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.MainPage;
import pages.QuestionPage;

import java.util.concurrent.TimeUnit;

import static org.example.PropertyReader.getProperty;

public class AddAnswerTest {

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
    public void testAnswerFirefox() {
        MainPage mainPage = new MainPage(firefoxDriver);
        mainPage.login(getProperty("second.login"), getProperty("second.password"));
        QuestionPage questionPage = new QuestionPage(firefoxDriver, "222409534");
        questionPage.answer("Лучше всего использовать Selenium: можно писать автоматизацию " +
                "на любимом языке программирования или автоматизировать тестирование в Selenium IDE.");
    }

    @Test
    public void testAnswerChrome() {
        MainPage mainPage = new MainPage(chromeDriver);
        mainPage.login(getProperty("first.login"), getProperty("first.password"));
        QuestionPage questionPage = new QuestionPage(chromeDriver, "222409579");
        questionPage.answer("Лучше всего использовать Selenium: можно писать автоматизацию " +
                "на любимом языке программирования или автоматизировать тестирование в Selenium IDE.");
    }

    @AfterAll
    public static void tearDown() {
        chromeDriver.quit();
        firefoxDriver.quit();
    }
}
