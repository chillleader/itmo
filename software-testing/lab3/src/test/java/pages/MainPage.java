package pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class MainPage {

    private final WebDriver driver;
    private final String URL = "https://otvet.mail.ru";

    public MainPage(WebDriver driver) {
        this.driver = driver;
        driver.get(URL);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[@id='PH_authLink']")
    private WebElement loginButton;

    @FindBy(xpath = "//iframe[@class='ag-popup__frame__layout__iframe']")
    private WebElement loginFrame;

    @FindBy(xpath = "//i[@id='PH_user-email']")
    private WebElement usernameField;

    @FindBy(xpath = "//a[@id='PH_logoutLink']")
    private WebElement logoutButton;

    public void login(String username, String password) {
        assert username != null;
        assert password != null;
        loginButton.click();
        driver.switchTo().frame(loginFrame);
        LoginPanelPopup loginPanel = new LoginPanelPopup(driver);
        String[] parts = username.split("@", 2);
        loginPanel.getLoginInput().sendKeys(parts[0]);
        loginPanel.getLoginInput().sendKeys("@");
        loginPanel.getLoginInput().sendKeys(parts[1]);
        loginPanel.getNextButton().click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(loginPanel.getPasswordInput()));
        loginPanel.getPasswordInput().sendKeys(password);
        loginPanel.getSubmitButton().click();
        driver.switchTo().defaultContent();

        wait.until(ExpectedConditions.textToBePresentInElement(
                usernameField, username));
    }
}
