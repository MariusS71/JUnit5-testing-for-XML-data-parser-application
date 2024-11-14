package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Nested
class LoginControllerTest extends ApplicationTest {

    private loginController loginController;
    private static final String USERS_FILE_PATH = "users.xml";
    private static final String USERS_BACKUP_FILE_PATH = "users_backup.xml";

    @BeforeEach
    public void setUp() throws Exception {
        // Backup the original users.xml file
        Files.copy(Paths.get(USERS_FILE_PATH), Paths.get(USERS_BACKUP_FILE_PATH));

        // Initialize the controller and the scene
        loginController = new loginController();
        interact(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/login.fxml"));
                Parent root = loader.load();

                // Get the controller
                loginController = loader.getController();

                Scene scene = new Scene(root, 600, 300);
                Stage FirstPageStage = new Stage();
                FirstPageStage.setTitle("Baza de date accidente");
                FirstPageStage.setScene(scene);
                FirstPageStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Prepare a test XML file for user data
        prepareTestUserData();
    }

    @AfterEach
    public void tearDown() throws Exception {
        // Restore the original users.xml file
        Files.copy(Paths.get(USERS_BACKUP_FILE_PATH), Paths.get(USERS_FILE_PATH), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        Files.delete(Paths.get(USERS_BACKUP_FILE_PATH));
    }

    private void prepareTestUserData() throws Exception {
        String userData = """
        <users>
            <user>
                <username>validUser</username>
                <password>validPass</password>
                <email>valid@example.com</email>
            </user>
        </users>
        """;
        Files.writeString(Paths.get(USERS_FILE_PATH), userData);
    }

    @Test
    void testValidLogin() throws SQLException, NoSuchAlgorithmException, IOException {
        interact(() -> {
            loginController.login_usernameTextField.setText("validUser");
            loginController.login_enterPasswordField.setText("validPass");
            try {
                loginController.loginButtonOnAction(null);
            } catch (SQLException | NoSuchAlgorithmException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        // Check if the userFound variable is true
        assertTrue(loginController.userFound, "User should be found with valid credentials");
    }

    @Test
    void testInvalidLogin() throws SQLException, NoSuchAlgorithmException, IOException {
        interact(() -> {
            loginController.login_usernameTextField.setText("test");
            loginController.login_enterPasswordField.setText("test");
            try {
                loginController.loginButtonOnAction(null);
            } catch (SQLException | NoSuchAlgorithmException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        // Check if the login message label indicates incorrect credentials
        assertEquals("Incorrect username or password", loginController.loginMessageLabel.getText());
    }

    @Test
    void testUsernameCorrectPasswordIncorrect() throws SQLException, NoSuchAlgorithmException, IOException {
        interact(() -> {
            loginController.login_usernameTextField.setText("validUser");
            loginController.login_enterPasswordField.setText("invalidPass");
            try {
                loginController.loginButtonOnAction(null);
            } catch (SQLException | NoSuchAlgorithmException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        // Check if the login message label indicates incorrect credentials
        assertEquals("Incorrect username or password", loginController.loginMessageLabel.getText());
    }

    @Test
    void testPasswordCorrectUsernameIncorrect() throws SQLException, NoSuchAlgorithmException, IOException {
        interact(() -> {
            loginController.login_usernameTextField.setText("invalidUser");
            loginController.login_enterPasswordField.setText("validPass");
            try {
                loginController.loginButtonOnAction(null);
            } catch (SQLException | NoSuchAlgorithmException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        // Check if the login message label indicates incorrect credentials
        assertEquals("Incorrect username or password", loginController.loginMessageLabel.getText());
    }


    @Test
    void testEmptyFields() throws SQLException, NoSuchAlgorithmException, IOException {
        interact(() -> {
            // Leave username and password fields empty
            loginController.login_usernameTextField.setText("");
            loginController.login_enterPasswordField.setText("");

            // Simulate clicking the login button
            try {
                loginController.loginButtonOnAction(null);
            } catch (SQLException | NoSuchAlgorithmException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        // Check if the login message label indicates empty fields
        assertEquals("Empty fields", loginController.loginMessageLabel.getText());
    }

    @Test
    void testCreateUserWithValidDetails() throws SQLException, NoSuchAlgorithmException {
        interact(() -> {
            loginController.create_usernameTextField.setText("newUser");
            loginController.create_passwordTextField.setText("strongPassword");
            loginController.repeat_passwordTextField.setText("strongPassword");
            loginController.create_emailTextField.setText("newuser@example.com");

            // Simulate clicking the create button
            try {
                loginController.createButtonOnAction(null);
            } catch (SQLException | NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        });
        // Check if the account creation was successful
        assertEquals("Account created successfully.", loginController.createMessageLabel.getText());

        // Verify the new user data in the XML file
        boolean userFound = false;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(USERS_FILE_PATH));
            doc.getDocumentElement().normalize();

            NodeList userList = doc.getElementsByTagName("user");

            for (int i = 0; i < userList.getLength(); i++) {
                Node userNode = userList.item(i);
                if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element userElement = (Element) userNode;
                    String username = userElement.getElementsByTagName("username").item(0).getTextContent();
                    String password = userElement.getElementsByTagName("password").item(0).getTextContent();
                    if ("newUser".equals(username) && "strongPassword".equals(password)) {
                        userFound = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(userFound, "New user should be found in the XML file");

        // Test login with the new credentials
        interact(() -> {
            loginController.login_usernameTextField.setText("newUser");
            loginController.login_enterPasswordField.setText("strongPassword");
            try {
                loginController.loginButtonOnAction(null);
            } catch (SQLException | NoSuchAlgorithmException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        // Check if the new user login is successful
        assertTrue(loginController.userFound, "New user should be able to log in with valid credentials");
    }

    @Test
    void testPasswordMismatchDuringUserCreation() throws SQLException, NoSuchAlgorithmException {
        interact(() -> {
            loginController.create_usernameTextField.setText("newUser");
            loginController.create_passwordTextField.setText("strongPassword");
            loginController.repeat_passwordTextField.setText("wrongPassword");
            loginController.create_emailTextField.setText("newuser@example.com");

            // Simulate clicking the create button
            try {
                loginController.createButtonOnAction(null);
            } catch (SQLException | NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        });
        // Check if the password mismatch error message is displayed
        assertEquals("The passwords are different", loginController.createMessageLabel.getText());
    }

    @Test
    void testExistingUsernameDuringUserCreation() throws SQLException, NoSuchAlgorithmException {
        interact(() -> {
            loginController.create_usernameTextField.setText("validUser"); // Already existing username
            loginController.create_passwordTextField.setText("newPassword");
            loginController.repeat_passwordTextField.setText("newPassword");
            loginController.create_emailTextField.setText("newuser@example.com");

            // Simulate clicking the create button
            try {
                loginController.createButtonOnAction(null);
            } catch (SQLException | NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        });
        // Check if the error message for existing username is displayed
        assertEquals("Username already exists", loginController.createMessageLabel.getText());
    }

    @Test
    void testWeakPasswordDuringUserCreation() throws SQLException, NoSuchAlgorithmException {
        interact(() -> {
            loginController.create_usernameTextField.setText("newUser");
            loginController.create_passwordTextField.setText("weak");
            loginController.repeat_passwordTextField.setText("weak");
            loginController.create_emailTextField.setText("newuser@example.com");

            // Simulate clicking the create button
            try {
                loginController.createButtonOnAction(null);
            } catch (SQLException | NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        });
        // Check if the weak password error message is displayed
        assertEquals("Password - at least 8 characters", loginController.createMessageLabel.getText());
    }
}
