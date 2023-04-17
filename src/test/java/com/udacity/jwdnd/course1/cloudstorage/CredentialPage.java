package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialPage {

    protected WebDriver webDriver;
    static WebDriverWait webDriverWait;
    @FindBy(xpath="//button[text()='+ Add a New Credential']")
    private WebElement newCredentialButton;

    @FindBy(css="#credential-url")
    private WebElement urlInput;

    @FindBy(css="#credential-username")
    private WebElement usernameInput;

    @FindBy(css="#credential-password")
    private WebElement passwordInput;

    @FindBy(xpath="//*[@id='credentialModal']/div/div/div/button[text()='Save changes']")
    private WebElement saveChangesBtn;

    public CredentialPage(WebDriver webDriver){
        this.webDriver = webDriver;
         this.webDriverWait= new WebDriverWait(webDriver,2);
        PageFactory.initElements(webDriver,this);
    }

    public WebElement getEditBtnByURL(String url) {
        String editBtnXpath = "//th[contains(text(),'"+url+"')]/preceding-sibling::td/button";
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(editBtnXpath)));
        return webDriverWait.until(webDriver -> webDriver.findElement(By.xpath(editBtnXpath)));
    }

    public WebElement getDeleteBtnByURL(String url){
        String delBtnXpath = "//th[contains(text(),'"+url+"')]/preceding-sibling::td/a";
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(delBtnXpath)));
        return webDriverWait.until(webDriver -> webDriver.findElement(By.xpath(delBtnXpath)));
    }

    public void saveNewCredential(String url, String username, String password){


        webDriverWait.until(ExpectedConditions.visibilityOf(urlInput));
        urlInput.clear();
        urlInput.sendKeys(url);
        usernameInput.clear();
        usernameInput.sendKeys(username);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        saveChangesBtn.click();
    }

    public String getUrlInputByURL() {
        webDriverWait.until(ExpectedConditions.visibilityOf(urlInput));
        return urlInput.getAttribute("value");
    }

    public String getUsernameInput() {
        webDriverWait.until(ExpectedConditions.visibilityOf(usernameInput));
        return usernameInput.getAttribute("value");
    }

    public String getPasswordInput() {
        webDriverWait.until(ExpectedConditions.visibilityOf(passwordInput));
        return passwordInput.getAttribute("value");
    }

    public void clickNewCredentialButton(){
        webDriverWait.until(ExpectedConditions.visibilityOf(newCredentialButton));
        newCredentialButton.click();
    }
}
