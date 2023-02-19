package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class NotePage {

    protected WebDriver webDriver;

    @FindBy(css="#nav-notes-tab")
    private WebElement navNoteTab;

    @FindBy(xpath="//button[text()='+ Add a New Note']")
    private WebElement addNoteBtn;

    @FindBy(css="#note-title")
    private WebElement noteTitleInput;

    @FindBy(css="#note-description")
    private WebElement noteDescriptionInput;

    @FindBy(xpath="#noteSubmit")
    private WebElement saveChangesBtn;

    public NotePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver,this);
    }

    public void saveNewNote(String title, String description){
        noteTitleInput.sendKeys(title);
        noteDescriptionInput.sendKeys(description);
        saveChangesBtn.click();
    }

    public WebElement getNoteEditBtnByNoteDescription(String noteTitle, String noteDescription){
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 2);
        return webDriverWait.until(webDriver -> webDriver.findElement(By.xpath("//*[@id='userTable'']/tbody/tr/th[text()="
                + noteTitle+"]/following-siblings::td[text()="+noteDescription+"])/preceding-siblings::td/button")));
    }

    public WebElement getNoteDeleteBtnByNoteDescription(String noteTitle, String noteDescription){
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 2);
        return webDriverWait.until(webDriver -> webDriver.findElement(By.xpath("//*[@id='userTable'']/tbody/tr/th[text()="
                + noteTitle+"]/following-siblings::td[text()="+noteDescription+"])/preceding-siblings::td/a")));
    })

    public String getNoteTitleInput() {
        return noteTitleInput.getText();
    }

    public String getNoteDescriptionInput() {
        return noteDescriptionInput.getText();
    }
}