package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;

public class SignupPage {

    @FindBy(css="#inputFirstName")
    private WebElement inputFirstName;

    @FindBy(css="#inputLastName")
    private WebElement inputLastName;

    @FindBy(css="#inputUsername")
    private WebElement inputUsername;

    @FindBy(css="#inputPassword")
    private WebElement inputPassword;

    @FindBy(css="#buttonSignUp")
    private WebElement buttonSignUp;

    public SignupPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void signup(String firstName, String lastName, String username, String password) {
        this.inputFirstName.sendKeys(firstName);
        this.inputLastName.sendKeys(lastName);
        this.inputUsername.sendKeys(username);
        this.inputPassword.sendKeys(password);
        this.buttonSignUp.click();
    }
}
