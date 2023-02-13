package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;

public class HomePage {

    @FindBy(css="#logoutDiv button")
    private WebElement logoutButton;

    public HomePage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public void logout(){
        logoutButton.click();
    }
}
