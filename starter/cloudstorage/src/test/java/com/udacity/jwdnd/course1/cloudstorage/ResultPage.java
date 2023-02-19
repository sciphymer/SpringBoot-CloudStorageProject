package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {

    @FindBy(css="a")
    private WebElement continueLink;

    public ResultPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver,this);
    }

    public void clickContinue(){
        continueLink.click();
    }
}
