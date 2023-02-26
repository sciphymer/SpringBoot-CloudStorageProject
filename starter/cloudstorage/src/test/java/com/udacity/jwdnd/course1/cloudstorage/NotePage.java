package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class NotePage {

    protected WebDriver webDriver;

    static protected WebDriverWait webDriverWait;

    @FindBy(css="#nav-notes-tab")
    private WebElement navNoteTab;

    @FindBy(xpath="//button[text()='+ Add a New Note']")
    private WebElement addNoteBtn;

    @FindBy(css="#note-title")
    private WebElement noteTitleInput;

    @FindBy(css="#note-description")
    private WebElement noteDescriptionInput;

    @FindBy(xpath="//*[@id='noteModal']/div/div/div/button[text()='Save changes']")
    private WebElement saveChangesBtn;

    public NotePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.webDriverWait = new WebDriverWait(webDriver, 2);
        PageFactory.initElements(webDriver,this);
    }

    public void saveNewNote(String title, String description){
        webDriverWait.until(ExpectedConditions.visibilityOf(noteTitleInput));
        noteTitleInput.clear();
        noteTitleInput.sendKeys(title);
        noteDescriptionInput.clear();
        noteDescriptionInput.sendKeys(description);
        saveChangesBtn.click();
    }

    public WebElement getNoteEditBtnByNoteDescription(String noteTitle, String noteDescription){
        String editBtnXpath = "//*[@id='userTable']/tbody/tr/th[text()='"+noteTitle+"']/following-sibling::td[text()='"+noteDescription+"']/preceding-sibling::td/button";

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(editBtnXpath)));
        return webDriver.findElement(By.xpath(editBtnXpath));
    }

    public WebElement getNoteDeleteBtnByNoteDescription(String noteTitle, String noteDescription){
        String delBtnXpath="//*[@id='userTable']/tbody/tr/th[text()='"+noteTitle+"']/following-sibling::td[text()='"+noteDescription+"']/preceding-sibling::td/a";
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(delBtnXpath)));
        return webDriver.findElement(By.xpath(delBtnXpath));
    }

    public String getNoteTitleInput() {
        webDriverWait.until(ExpectedConditions.visibilityOf(noteTitleInput));
        return noteTitleInput.getAttribute("value");
    }

    public String getNoteDescriptionInput() {
        webDriverWait.until(ExpectedConditions.visibilityOf(noteDescriptionInput));
        return noteDescriptionInput.getAttribute("value");
    }
}