# JUnit 5 Testing for JavaFX Application: Accident Data Management 

This project demonstrates the use of **JUnit 5** for unit testing a **JavaFX-based application** designed to manage accident data in factories. The application allows users to authenticate, view, and filter accident records from an XML file. The goal of the project is to ensure the reliability and correctness of these key features through rigorous testing.

## Project Overview
- **Objective**: Implement a comprehensive testing strategy using JUnit 5 to validate functionality and ensure the quality of a JavaFX application that handles accident data in factories.
- **Technologies Used**:
  - **Java**: Core programming language.
  - **JavaFX**: Framework for building the GUI.
  - **JUnit 5**: Testing framework used for writing and executing unit tests.
  - **TestFX**: For testing JavaFX applications.
  
- **Application Features**:
  - **User Authentication**: Login with username and password.
  - **Accident Data Management**: View, filter, and search accident data stored in an XML file.
  - **Error Handling**: Provides error messages for invalid inputs like incorrect usernames or missing fields.

## Methodology
1. **Test Types**:
   - **Unit Testing**: Validating the functionality of individual components (such as login logic and XML parsing).
   - **Functional Testing**: Ensuring that the application meets its expected behaviors across different scenarios.

2. **Test Strategy**:
   - A detailed **Test Plan** was created, including test cases for user authentication, data retrieval, and data filtering.
   - Tests were categorized based on the functional areas: authentication, data retrieval, and accident data filtering.
   - Special test data was created to avoid interfering with real data and ensure isolated test environments.

3. **JUnit 5 Test Structure**:
   - **LoginControllerTest**: Tests related to the user login functionality (e.g., valid/invalid credentials).
   - **Page1ControllerTest**: Tests related to the main application window, including accident data display and filtering.
   - Used **@Nested** classes to organize related tests for better clarity.
   - Integrated **TestFX** for testing JavaFX application components.

4. **Key Functionalities Tested**:
   - **Authentication**:
     - Valid/invalid login attempts.
     - Password mismatch handling.
     - Account creation and error messages for invalid inputs.
   - **Accident Data Display**:
     - Correct display of accident data for valid/invalid factory names.
     - Filtering accidents based on severity (light, medium, severe).
   - **XML Data Parsing**:
     - Ensuring the application correctly reads and displays accident and employee data from XML files.

## Key Features
- **Robust Test Coverage**: Ensures all critical functionalities, such as authentication, data display, and error handling, are thoroughly tested.
- **Data Integrity**: Temporary test datasets are used to prevent interference with production data, and the database is reset after each test.
- **Automated Testing**: Full test automation with JUnit 5 and TestFX, ensuring that all tests are executed in sequence and providing immediate feedback if any test fails.

## Results
- **Test Cases**: The following test cases were implemented and executed:
  - **Authentication Tests**: 7 test cases, including valid/invalid logins, empty fields, and password mismatch.
  - **Data Display Tests**: 6 test cases covering valid/invalid factory names, empty searches, and accident filtering.
  - **Error Handling**: Ensured proper error messages are displayed for invalid inputs or missing data.
  
- **Test Execution**: All tests pass successfully, ensuring that the application is stable and the core features function as expected.

## Conclusion
This project showcases how unit testing with JUnit 5 can be applied to validate the functionality of a JavaFX application. By implementing a structured testing approach, we ensured that the application's core features—authentication, XML data parsing, and accident data filtering—are reliable and robust. The project highlights the importance of automated testing in maintaining software quality and preventing defects during the development process.

### Future Improvements
- **Integration Testing**: Future work could include integration tests to ensure that different components of the application interact as expected.
- **Performance Testing**: Adding tests to evaluate the application’s performance under different load conditions, especially for large datasets.

For detailed documentation, test code, and the complete test plan, please refer to the project files.

