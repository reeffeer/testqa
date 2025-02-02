package org.example.pages;

import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Data
public class QuestionnairePage extends BasePage {

    public QuestionnairePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id='dataEmail']")
    WebElement email;

    @FindBy(xpath = "//*[@id='dataName']")
    WebElement name;

    @FindBy(xpath = "//*[@id='dataGender']")
    WebElement gender;

    @FindBy(xpath = "//*[@id='dataCheck11']")
    WebElement dataCheck11;

    @FindBy(xpath = "//*[@id='dataCheck12']")
    WebElement dataCheck12;

    @FindBy(xpath = "//*[@name='radioGroup']")
    WebElement radioBtn;

    List<WebElement> radioBtns;

    @FindBy(xpath = "//*[@id='dataSend']")
    WebElement btnAdd;

    @FindBy(xpath = "//*[@id='dataTable']")
    WebElement table;

    @FindBy(xpath = "//*[@id='dataTable']/tbody/tr")
    WebElement row;

    @FindBy(xpath = "//*[@id='dataTable']/tbody/tr/td")
    WebElement cell;

    List<WebElement> rows;

    List<WebElement> cells = driverManager.getDriver().findElements(By.xpath("//*[@id='dataTable']/tbody/tr/td"));

    @FindBy(xpath = "//*[@id='emailFormatError']")
    WebElement emailFormatError;

    @FindBy(xpath = "//*[@id='blankNameError']")
    WebElement blankNameError;

    @FindBy(xpath = "//*[@class='uk-margin uk-modal-content']")
    WebElement modalAlert;

    @FindBy(xpath = "//*[@class='uk-modal-footer uk-text-right']/button")
    WebElement okBtn;

    public List<WebElement> getRadioBtns() {
        return driverManager.getDriver().findElements(By.xpath("//*[@name='radioGroup']"));
    }

    public List<WebElement> getRows() {
        return driverManager.getDriver().findElements(By.xpath("//*[@id='dataTable']/tbody/tr"));
    }
}
