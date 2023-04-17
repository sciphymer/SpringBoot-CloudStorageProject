package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private WebDriver webDriver;
    private WebDriverWait webDriverWait;
    @FindBy(css="#logoutDiv button")
    private WebElement logoutButton;

    @FindBy(css="#nav-credentials-tab")
    private WebElement credentialTab;

    @FindBy(css="#nav-files-tab")
    private WebElement filesTab;

    @FindBy(css="#nav-notes-tab")
    private WebElement notesTab;

    public HomePage(WebDriver driver){
        this.webDriver = driver;
        this.webDriverWait = new WebDriverWait(webDriver, 2);
        PageFactory.initElements(driver,this);
    }

    public void logout(){
        logoutButton.click();
    }

    public void clickCredentialsTab(){
        webDriverWait.until(ExpectedConditions.visibilityOf(credentialTab));
        credentialTab.click();
    }

    public void clickNotesTab(){
        webDriverWait.until(ExpectedConditions.visibilityOf(notesTab));
        notesTab.click();
    }

    public void clickFilesTab(){
        webDriverWait.until(ExpectedConditions.visibilityOf(filesTab));
        filesTab.click();
    }
}
