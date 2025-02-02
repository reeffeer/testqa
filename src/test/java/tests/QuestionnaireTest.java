package tests;

import org.example.manager.DriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.*;
import org.example.pages.AuthorizationPage;
import org.example.pages.QuestionnairePage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class QuestionnaireTest extends BaseTests {
    static DriverManager driverManager = DriverManager.getInstance();
    static WebDriver driver = driverManager.getDriver();
    static String url = "file:///C:/Users/reefs/Desktop/it/qa-test%20protei.html";
    static String wrongEmailFormat = "src/test/resources/invalid_email_format.txt";
    static AuthorizationPage authPage = new AuthorizationPage(driver);
    QuestionnairePage questionPage = new QuestionnairePage(driver);

    @BeforeAll
    public static void before() {
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        authPage.getLoginField().sendKeys("test@protei.ru");
        authPage.getPasswordField().sendKeys("test");
        authPage.getBtnEnter().click();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data_for_questionnaire_tests/fill_form_data.csv")
    public void fillTheFormTest(String email, String name) {
        Assertions.assertTrue(questionPage.getEmail().isDisplayed());
        questionPage.getEmail().sendKeys(email);

        Assertions.assertTrue(questionPage.getName().isDisplayed());
        questionPage.getName().sendKeys(name);

        Assertions.assertTrue(questionPage.getGender().isDisplayed());
        questionPage.getGender().click();
        questionPage.getGender().sendKeys(Keys.ARROW_DOWN);

        Assertions.assertTrue(questionPage.getDataCheck11().isDisplayed());
        questionPage.getDataCheck11().click();

        List<WebElement> radioBtns = questionPage.getRadioBtns();
        Assertions.assertTrue(questionPage.getRadioBtn().isDisplayed());
        radioBtns.get(1).click();
        Assertions.assertTrue(questionPage.getBtnAdd().isDisplayed());
        questionPage.getBtnAdd().click();

        questionPage.waitUntilElementToBeVisible(questionPage.getModalAlert());
        Assertions.assertEquals(questionPage.getModalAlert().getText(), "Данные добавлены.");
        questionPage.getOkBtn().click();

        questionPage.waitUntilElementToBeVisible(questionPage.getRow());
        List<WebElement> records = questionPage.getRows();
        Assertions.assertFalse(questionPage.getRows().isEmpty(), "No records");

        boolean dataFound = false;
        for (WebElement record : records) {
            List<WebElement> cells = record.findElements(By.tagName("td"));
            if (cells.get(0).getText().contains(email) && cells.get(1).getText().contains(name)) {
                dataFound = true;
                break;
            }
        }
        Assertions.assertTrue(dataFound, "Запись не найдена");
    }

    @Test
    public void blankNameErrorTest() {
        questionPage.getEmail().sendKeys("email@mail.com");
        questionPage.getBtnAdd().click();
        Assertions.assertTrue(questionPage.getBlankNameError().isDisplayed());
        Assertions.assertEquals("Поле имя не может быть пустым", questionPage.getBlankNameError().getText());
    }

    @ParameterizedTest
    @MethodSource("provideCredentialsFromTxtForEmailFormatErrorTest")
    public void emailFormatErrorTest() {
        questionPage.getEmail().sendKeys("email@mail.com");
        questionPage.getBtnAdd().click();
        Assertions.assertTrue(questionPage.getBlankNameError().isDisplayed());
        Assertions.assertEquals("Поле имя не может быть пустым", questionPage.getBlankNameError().getText());
    }

    @Test
    public void blankEmailTest() {
        questionPage.getName().sendKeys("name");
        questionPage.getBtnAdd().click();
        Assertions.assertTrue(questionPage.getEmailFormatError().isDisplayed());
        Assertions.assertEquals("Неверный формат E-Mail", questionPage.getEmailFormatError().getText());
    }

    static Stream<String[]> provideCredentialsFromTxtForEmailFormatErrorTest() throws IOException {
        return getCredentialsFromTxt(wrongEmailFormat);
    }

    @AfterAll
    static void after() {
        driver.quit();
    }
}
