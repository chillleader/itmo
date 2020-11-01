package pages;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class QuestionPage {

    private final WebDriver driver;

    private final static String URI = "https://otvet.mail.ru/question/";

    public QuestionPage(WebDriver driver, String id) {
        this.driver = driver;
        driver.get(URI + id);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[@title='Ответить']")
    private WebElement startAnswerButton;

    @FindBy(xpath = "//textarea[@name='inputBody']")
    private WebElement answerTextArea;

    @FindBy(xpath = "//a[@class='btn_3HCdDVEd btn_big_LcgInFTf btn_primary_1eJ3pHtF btn_2kRLDbyE btn__submit_jq0ldq9d']")
    private WebElement submitAnswerButton;

    public void answer(String answerText) {
        startAnswerButton.click();
        answerTextArea.sendKeys(answerText);
        submitAnswerButton.click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(
                "//div[text()[contains(.,'" + answerText + "')]]")));
    }
}
