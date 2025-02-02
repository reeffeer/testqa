package tests;

import org.example.manager.DriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.example.pages.AuthorizationPage;


import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static tests.BaseTests.getCredentialsFromTxt;

public class AuthFormTest {
    static DriverManager driverManager = DriverManager.getInstance();
    static WebDriver driver = driverManager.getDriver();
    static String url = "file:///C:/Users/reefs/Desktop/it/qa-test%20protei.html";
    static String wrongEmailFormat = "src/test/resources/wrong-email-format.txt";

    AuthorizationPage authPage = new AuthorizationPage(driver);

    @BeforeEach
    void before() {
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data_for_autorization_tests/positive-test-auth-data.csv", numLinesToSkip = 1)
    public void authorizationPositiveTest(String login, String password) {
        Assertions.assertTrue(authPage.getLoginField().isDisplayed());
        authPage.getLoginField().sendKeys(login);
        Assertions.assertTrue(authPage.getPasswordField().isDisplayed());
        authPage.getPasswordField().sendKeys(password);
        Assertions.assertTrue(authPage.getBtnEnter().isDisplayed());
        authPage.getBtnEnter().click();
        Assertions.assertTrue(
                driver.findElements(By.xpath("//*[@id='emailFormatError']")).isEmpty(),
                "Сообщение об ошибке отображается, авторизация не удалась");
        Assertions.assertTrue(
                driver.findElements(By.xpath("//*[@id='invalidEmailPassword']")).isEmpty(),
                "Сообщение об ошибке отображается, авторизация не удалась");
    }

    @Test
    public void emptyFieldsTest() {
        Assertions.assertTrue(authPage.getBtnEnter().isDisplayed());
        authPage.getBtnEnter().click();
        Assertions.assertTrue(authPage.getEmailFormatError().isDisplayed());
        Assertions.assertEquals("Неверный формат E-Mail", authPage.getEmailFormatError().getText());
    }

    @Test
    public void emptyPasswordTest() {
        Assertions.assertTrue(authPage.getLoginField().isDisplayed());
        authPage.getLoginField().sendKeys("test@protei.ru");
        Assertions.assertTrue(authPage.getBtnEnter().isDisplayed());
        authPage.getBtnEnter().click();
        Assertions.assertTrue(authPage.getInvalidEmailPassword().isDisplayed());
        Assertions.assertEquals("Неверный E-Mail или пароль", authPage.getInvalidEmailPassword().getText());
    }

    @ParameterizedTest
    @MethodSource("provideCredentialsFromTxtForEmailFormatErrorTest")
    public void emailFormatErrorTest(String login, String password) {
        Assertions.assertTrue(authPage.getLoginField().isDisplayed());
        authPage.getLoginField().sendKeys(login);
        Assertions.assertTrue(authPage.getPasswordField().isDisplayed());
        authPage.getPasswordField().sendKeys(password);
        Assertions.assertTrue(authPage.getBtnEnter().isDisplayed());
        authPage.getBtnEnter().click();
        Assertions.assertTrue(authPage.getEmailFormatError().isDisplayed());
        Assertions.assertEquals("Неверный формат E-Mail", authPage.getEmailFormatError().getText());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data_for_autorization_tests/wrong-data.csv")
    public void invalidEmailPasswordTest(String login, String password) {
        Assertions.assertTrue(authPage.getLoginField().isDisplayed());
        authPage.getLoginField().sendKeys(login);
        Assertions.assertTrue(authPage.getPasswordField().isDisplayed());
        authPage.getPasswordField().sendKeys(password);
        Assertions.assertTrue(authPage.getBtnEnter().isDisplayed());
        authPage.getBtnEnter().click();
        Assertions.assertTrue(authPage.getInvalidEmailPassword().isDisplayed());
        Assertions.assertEquals("Неверный E-Mail или пароль", authPage.getInvalidEmailPassword().getText());
    }

    static Stream<String[]> provideCredentialsFromTxtForEmailFormatErrorTest() throws IOException {
        return getCredentialsFromTxt(wrongEmailFormat);
    }

    @AfterAll
    static void after() {
        driver.quit();
    }
}
