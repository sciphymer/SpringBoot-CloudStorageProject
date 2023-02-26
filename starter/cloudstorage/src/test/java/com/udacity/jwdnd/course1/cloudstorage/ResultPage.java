package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultPage {

    private WebDriver webDriver;
    static private WebDriverWait webDriverWait;
    @FindBy(css="a")
    private WebElement continueLink;

    public ResultPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.webDriverWait = new WebDriverWait(webDriver,2);
        PageFactory.initElements(webDriver,this);
    }

    public void clickContinue(){
        webDriverWait.until(ExpectedConditions.visibilityOf(continueLink));
        continueLink.click();
    }
}
