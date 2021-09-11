package pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class LoginPanelPopup {

    private final WebDriver driver;

    public LoginPanelPopup(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@name='username']")
    private WebElement loginInput;

    @FindBy(xpath = "//button[@data-test-id='next-button']")
    private WebElement nextButton;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@data-test-id='submit-button']")
    private WebElement submitButton;
}
