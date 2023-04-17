package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(css="#inputUsername")
    private WebElement inputUsername;

    @FindBy(css="#inputPassword")
    private WebElement inputPassword;

    @FindBy(css="#login-button")
    private WebElement loginButton;

    public LoginPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver,this);
    }

    public void login(String username, String password){
        inputUsername.clear();
        inputUsername.sendKeys(username);
        inputPassword.clear();
        inputPassword.sendKeys(password);
        loginButton.click();
    }
}
