package org.example.pages;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Data
public class AuthorizationPage {
    public AuthorizationPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id='loginEmail']")
    private WebElement loginField;

    @FindBy(xpath = "//*[@id='loginPassword']")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@id='authButton']")
    private WebElement btnEnter;

    @FindBy(xpath = "//*[@id='emailFormatError']")
    private WebElement emailFormatError;

    @FindBy(xpath = "//*[@id='invalidEmailPassword']")
    private WebElement invalidEmailPassword;
}
