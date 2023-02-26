package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private WebDriverWait webDriverWait;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		webDriverWait = new WebDriverWait(this.driver, 2);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@DisplayName("unauthorized User can only access login And signup pages")
	public void unauthorizedUserAccess(){
		getLoginPage();

		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		Assertions.assertEquals("Sign Up", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@DisplayName("A test that signs up a new user, logs in, verifies that the home page " +
			"is accessible, logs out, and verifies that the home page is no longer accessible")
	public void basicLoginLogout(){

		String firstName = "Harry";
		String lastName = "Potter";
		String username = "harrypotter";
		String password = "magicWorld";

		SignupPage signupPage = new SignupPage(driver);
		LoginPage loginPage = new LoginPage(driver);
		HomePage homePage = new HomePage(driver);


		doMockSignUp(firstName,lastName,username,password);

		doLogIn(username,password);

		webDriverWait.until(ExpectedConditions.urlContains("/home"));
		Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());

		homePage.logout();
		webDriverWait.until(ExpectedConditions.urlContains("/login"));
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");
		try {
			webDriverWait.until(ExpectedConditions.urlContains("/home"));
		} catch (TimeoutException e) {
		} finally {
			Assertions.assertNotEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());
		}

	}

	@Test
	@DisplayName("Test for Note Creation, Viewing, Editing, and Deletion.")
	public void createAndDisplayNote(){
		NotePage notepage = new NotePage(driver);
		SignupPage signupPage = new SignupPage(driver);
		LoginPage loginPage = new LoginPage(driver);
		ResultPage resultPage = new ResultPage(driver);
		HomePage homePage = new HomePage(driver);

		driver.get("http://localhost:" + this.port + "/signup");
		String firstName = "Ron";
		String lastName = "Weasley";
		String username = "ron";
		String password = "wizardFamily";

		doMockSignUp(firstName,lastName,username,password);

		doLogIn(username,password);

		String noteTitle = "Magic Spell";
		String noteDescription = "Wingardium Leviosa.";

		homePage.clickNotesTab();

	 	webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='+ Add a New Note']")));
		WebElement newNoteButton = driver.findElement(By.xpath("//button[text()='+ Add a New Note']"));
		newNoteButton.click();
		notepage.saveNewNote(noteTitle, noteDescription);

		resultPage.clickContinue();

		homePage.clickNotesTab();

		WebElement editBtn = notepage.getNoteEditBtnByNoteDescription(noteTitle,noteDescription);
		editBtn.click();

		String existingTitle = notepage.getNoteTitleInput();
		String editedDescription = "Expecto patronum!";

		notepage.saveNewNote(existingTitle,editedDescription);

		resultPage.clickContinue();

		homePage.clickNotesTab();

		Assertions.assertTrue(checkIfNoteExisted(driver, existingTitle,editedDescription));

		try {
			WebElement deleteBtn = notepage.getNoteDeleteBtnByNoteDescription(noteTitle, editedDescription);
			deleteBtn.click();
			resultPage.clickContinue();
			Assertions.assertFalse(checkIfNoteExisted(driver, existingTitle, editedDescription));
		} catch (TimeoutException e){
			System.out.println(e.getMessage());
			Assertions.fail();
		}
	}

	@Test
	@DisplayName("Tests for Credential Creation, Viewing, Editing, and Deletion.")
	public void testCredential() {
		String firstName = "Ron";
		String lastName = "Weasley";
		String username = "ron";
		String password = "wizardFamily";

		String url = "moodle.com";
		String url_username = "harrypotter";
		String url_password = "sirusblack";

		CredentialPage credentialPage = new CredentialPage(driver);
		SignupPage signupPage = new SignupPage(driver);
		LoginPage loginPage = new LoginPage(driver);
		HomePage homePage = new HomePage(driver);
		ResultPage resultPage = new ResultPage(driver);
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		doMockSignUp(firstName,lastName, username, password);

		doLogIn(username, password);

		webDriverWait.until(ExpectedConditions.urlContains("/home"));
		WebElement navNoteTab = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#nav-credentials-tab")));
		navNoteTab.click();

		credentialPage.clickNewCredentialButton();
		credentialPage.saveNewCredential(url,url_username,url_password);

		resultPage.clickContinue();

		homePage.clickCredentialsTab();

		//verify the created credential record//

		WebElement editBtn = credentialPage.getEditBtnByURL(url);
		editBtn.click();

		String existingUrl = credentialPage.getUrlInputByURL();
		String existingUsername = credentialPage.getUsernameInput();
		String existingPassword = credentialPage.getPasswordInput();

		String editedURLUsername = "jamespotter";
		String editedURLPassword = "lilypotter";

		credentialPage.saveNewCredential(existingUrl,editedURLUsername,editedURLPassword);

		resultPage.clickContinue();

		Assertions.assertTrue(checkIfCredentialExisted(driver, existingUrl, editedURLUsername, editedURLPassword ));

		try {
			driver.get("http://localhost:" + this.port + "/home");
			homePage.clickCredentialsTab();
			WebElement deleteBtn = credentialPage.getDeleteBtnByURL(existingUrl);
			deleteBtn.click();
			resultPage.clickContinue();
			Assertions.assertFalse(checkIfCredentialExisted(driver, existingUrl, editedURLUsername, editedURLPassword ));
		} catch (TimeoutException e){
			System.out.println(e.getMessage());
			Assertions.fail();
		}
	}

	private static boolean checkIfNoteExisted(WebDriver driver, String title, String description){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		String editBtnXpath = String.format("//*[@id='userTable']/tbody/tr/th[text()='%s']/following-sibling::td[text()='%s']/preceding-sibling::td/button",title,description);
		try{
			webDriverWait.until(ExpectedConditions
					.visibilityOf(driver.findElement(By.xpath(editBtnXpath))));
			return true;
		} catch(TimeoutException | NoSuchElementException e){
			return false;
		}
	}

	private static boolean checkIfCredentialExisted(WebDriver driver, String url, String urlUsername, String urlPassword){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		CredentialPage credentialPage = new CredentialPage(driver);
		String credentialRecordEditBtnXpath = String.format("//*[@id='credentialTable']/tbody/tr/th[text()='%s']" +
				"/following-sibling::td[text()='%s']/preceding-sibling::td/button", url, urlUsername);
		try {
			WebElement editBtn = webDriverWait.until(ExpectedConditions
					.visibilityOf(driver.findElement(By.xpath(credentialRecordEditBtnXpath))));
			editBtn.click();

			String credentialPagePassword = credentialPage.getPasswordInput();
			return urlPassword.equals(credentialPagePassword);

		} catch(TimeoutException | NoSuchElementException e){
			System.out.println(e.getMessage());
			return false;
		}
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depending on the rest of your code.
		*/
		webDriverWait = new WebDriverWait(driver, 1);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-msg")));
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		String loginPageURL = "http://localhost:" + this.port + "/login";
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		webDriverWait.until(ExpectedConditions.urlToBe(loginPageURL));
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals(loginPageURL, driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		String randomPageURL = "http://localhost:" + this.port + "/some-random-page";
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");

		// Try to access a random made-up URL.
		driver.get(randomPageURL);
		webDriverWait.until(ExpectedConditions.urlToBe(randomPageURL));
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}



}
