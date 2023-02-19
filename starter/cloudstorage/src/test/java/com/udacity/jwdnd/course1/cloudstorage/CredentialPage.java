package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialPage {

    protected WebDriver webDriver;
    @FindBy(xpath="//button[text()='+ Add a New Credential']")
    private WebElement newCredentialButton;

    @FindBy(css="#credential-url")
    private WebElement urlInput;

    @FindBy(css="#credential-username")
    private WebElement usernameInput;

    @FindBy(css="#credential-password")
    private WebElement passwordInput;

    @FindBy(css="#credentialSubmit")
    private WebElement saveChangesBtn;

    public CredentialPage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver,this);
    }



    public WebElement getEditBtnByURL(String url) {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 2);
        return webDriverWait.until(webDriver -> webDriver.findElement(By.xpath("//td[contains(text(),"+url+")]/preceding-sibling::td/button")));
    }

    public WebElement getDeleteBtnByURL(String url){
        WebDriverWait webDriverWait = new WebDriverWait(webDriver,2);
        return webDriverWait.until(webDriver -> webDriver.findElement(By.xpath("//td[contains(text(),"+url+")]/preceding-sibling::td/a")));
    }

    public void saveNewCredential(String url, String username, String password){
        WebDriverWait webDriverWait = new WebDriverWait(webDriver,2);
        newCredentialButton.click();
        urlInput.sendKeys(url);
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        saveChangesBtn.click();
    }

    public String getUrlInputByURL() {
        return urlInput.getText();
    }

    public String getUsernameInputByURL() {
        return usernameInput.getText();
    }

    public String getPasswordInputByURL() {
        return passwordInput.getText();
    }
}
