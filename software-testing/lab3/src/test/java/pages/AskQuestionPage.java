package pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class AskQuestionPage {

    private final static String URL = "https://otvet.mail.ru/ask";
    private final WebDriver driver;

    public AskQuestionPage(WebDriver driver) {
        this.driver = driver;
        driver.get(URL);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//textarea[@name='question_text']")
    private WebElement topicInput;

    @FindBy(xpath = "//textarea[@name='question_additional']")
    private WebElement textInput;

    @FindBy(xpath = "//div[@name='select_parents_categories']")
    private WebElement categoryPicker;

    @FindBy(xpath = "//div[div[span[text()[contains(.,'Программирование')]]]]")
    private WebElement computersCategory;

    @FindBy(xpath = "//div[@name='select_childs_categories']")
    private WebElement subcategoryPicker;

    @FindBy(xpath = "//div[div[span[text()[contains(.,'JavaScript')]]]]")
    private WebElement softwareSubcategory;

    @FindBy(xpath = "//a[@class='btn_3HCdDVEd btn_big_LcgInFTf btn_primary_1eJ3pHtF submit__btn_dqVdinFt']")
    private WebElement submitButton;

    public void askQuestionAboutComputers(String topic, String text) {
        topicInput.sendKeys(topic);
        textInput.sendKeys(text);
        categoryPicker.click();
        computersCategory.click();
        subcategoryPicker.click();
        softwareSubcategory.click();
        submitButton.click();
    }
}
