# Super*Duper*Drive Cloud Storage Project

This is a 3-Tiers web application project which implements a simple version of "Google Drive" using the following technology stacks:
1. Spring Boot MVC and MyBatis ORM frame work as backend
2. Thymeleaf rending template as frontend
3. Selenium for test automation

This Cloud Storage has the following features:
1. **Simple File Storage:** Upload/download/remove files
2. **Note Management:** Add/update/remove text notes
3. **Password Management:** Save, edit, and delete website credentials.  

## The Project Starter Code
The project is built with the following starter code:
* A database schema for the project and has added it to the `src/main/resources` directory. 
* Some HTML templates are provided for the website mockups, and placed in the `src/main/resources/templates` folder. These are static pages only. Thymeleaf is used to add functionality and real data rendered from the server you develop.
* EncryptionService in the backend for encrypting and decrypting passwords.

Different features are implemented in different tiers of this web application:
### The Back-End
1. Managing user access with Spring Security
 - restrict unauthorized users from accessing pages other than the login and signup pages. 

2. Handling front-end calls with controllers
 - controllers are bind with application data and able to receive and transmit data through the defined data model to the front-end. 
 - interpret the requests submitted by user and display error messages when needed.

3. Making calls to the database with MyBatis mappers
 - Implementing MyBatis mapper to connect model classes with database data to support the basic CRUD (Create, Read, Update, Delete) operations.


### The Front-End
Your tech lead has done a thorough job developing HTML templates for the required application pages. They have included fields, modal forms, success and error message elements, as well as styling and functional components using Bootstrap as a framework. You must edit these templates and insert Thymeleaf attributes to supply the back-end data and functionality described by the following individual page requirements:

1. Login page
 - Everyone should be allowed access to this page, and users can use this page to login to the application. 
 - Show login errors, like invalid username/password, on this page. 


2. Sign Up page
 - Everyone should be allowed access to this page, and potential users can use this page to sign up for a new account. 
 - Validate that the username supplied does not already exist in the application, and show such signup errors on the page when they arise.
 - Remember to store the user's password securely!


3. Home page
The home page is the center of the application and hosts the three required pieces of functionality. The existing template presents them as three tabs that can be clicked through by the user:


 i. Files
  - The user should be able to upload files and see any files they previously uploaded. 

  - The user should be able to view/download or delete previously-uploaded files.
  - Any errors related to file actions should be displayed. For example, a user should not be able to upload two files with the same name, but they'll never know unless you tell them!


 ii. Notes
  - The user should be able to create notes and see a list of the notes they have previously created.
  - The user should be able to edit or delete previously-created notes.

 iii. Credentials
 - The user should be able to store credentials for specific websites and see a list of the credentials they've previously stored. If you display passwords in this list, make sure they're encrypted!
 - The user should be able to view/edit or delete individual credentials. When the user views the credential, they should be able to see the unencrypted password.

The home page should have a logout button that allows the user to logout of the application and keep their data private.

### Testing
Your tech lead trusts you to do a good job, but testing is important whether you're an excel number-cruncher or a full-stack coding superstar! The QA team at Super*Duper*Drive carries out extensive user testing. Still, your tech lead wants you to write some simple Selenium tests to verify user-facing functionality and prove that your code is feature-complete before the testers get their hands on it.

1. Write tests for user signup, login, and unauthorized access restrictions.
 - Write a test that verifies that an unauthorized user can only access the login and signup pages.
 - Write a test that signs up a new user, logs in, verifies that the home page is accessible, logs out, and verifies that the home page is no longer accessible. 


2. Write tests for note creation, viewing, editing, and deletion.
 - Write a test that creates a note, and verifies it is displayed.
 - Write a test that edits an existing note and verifies that the changes are displayed.
 - Write a test that deletes a note and verifies that the note is no longer displayed.


3. Write tests for credential creation, viewing, editing, and deletion.
 - Write a test that creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted.
 - Write a test that views an existing set of credentials, verifies that the viewable password is unencrypted, edits the credentials, and verifies that the changes are displayed.
 - Write a test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.

## Final Tips and Tricks
### Password Security
Make sure not to save the plain text credentials of the application's users in the database. That's a recipe for data breach disaster! Use a hashing function to store a scrambled version instead. Your tech lead gave you a class called `HashService` that can hash passwords for you. When the user signs up, you only store a hashed version of their password in the database, and on login, you hash the password attempt before comparing it with the hashed password in the database. Your tech lead knows that can be a little confusing, so they provided this code sample to help illustrate the idea:








