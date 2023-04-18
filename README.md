# Cloud Storage Project

This is a 3-Tiers web application project which implements a simple version of "Google Drive" using the following technology stacks:
1. Spring Boot MVC and MyBatis ORM frame work as backend
2. Thymeleaf rending template as frontend
3. Selenium + JUnit for test automation

This Cloud Storage has the following features:
1. **Simple File Storage:** Upload/download/remove files
2. **Note Management:** Add/update/remove text notes
3. **Password Management:** Save, edit, and delete website credentials.  

### The Project Starter Code
The project is built with the following starter code:
* A database schema for the project and has added it to the `src/main/resources` directory. 
* Some HTML templates are provided for the website mockups, and placed in the `src/main/resources/templates` folder. These are static pages only. Thymeleaf is used to add functionality and real data rendered from the server you develop.
* EncryptionService in the backend for encrypting and decrypting passwords.

Different features are implemented in different tiers of this web application:
### The Back-End
1. Managing user access with Spring Security
- restrict unauthorized users from accessing pages other than the login and signup pages. 

2. Handling front-end calls with controllers
 - controllers are bound with application data and able to receive and transmit data through the defined data model to the front-end. 
 - interpret the requests submitted by user and display error messages when needed.

3. Making calls to the database with MyBatis mappers
 - Implementing MyBatis mapper to connect model classes with database data to support the basic CRUD (Create, Read, Update, Delete) operations.

### The Front-End
Insert Thymeleaf attributes to render back-end data to HTML web page templates and send data from HTML web pages to the Backend API of the application. 

Following pages are included in this project with the corresponding functions:

1. Login page
 - Everyone is allowed access to this page, and users can use this page to login to the application. 
 - Show login errors, like invalid username/password, on this page. 

2. Sign Up page
 - Everyone are allow to access this page, users can use this page to sign up for a new account. 
 - Validate that the username supplied does not already exist in the application, and show signup errors on the page when they arise.
 - User's password are stored in database with encryption.

3. Home page
The home page includes three functionalities as in tabs that can be clicked through by the user:

 i. Files
  - User can **upload files** and see any files they previously uploaded. 
  - User can **view/download or delete previously-uploaded files**.
  - Errors related to file actions will be displayed. e.g. user cannot upload two files with duplicate name.

 ii. Notes
  - User can create notes and see a list of the notes they have previously created.
  - User can edit or delete previously-created notes.

 iii. Credentials
 - User can store credentials for specific websites and see a list of the credentials they've previously stored. Password are show in encrypted string.
 - User can view/edit or delete individual credentials. When the user views the credential, they can see the unencrypted password.

The home page has a logout button.

### Testing
The test cases are written using Selenium + JUnit for automating the testing of user journey of the web application.
Page Object Model + Page factory design pattern is used to improve the coding readability and re-usability.

Follow test cases are included:
1. Tests for user signup, login, and unauthorized access restrictions.
 
2. Tests for note creation, viewing, editing, and deletion.

3. Tests for credential creation, viewing, editing, and deletion.
