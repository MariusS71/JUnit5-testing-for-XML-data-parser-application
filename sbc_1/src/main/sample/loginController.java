package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;



public class loginController implements  Initializable{

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    protected boolean userFound = false;

    @FXML
    private Button backButton;

    @FXML
    private Button cancelButton;

    @FXML
    private AnchorPane createAccountPane;

    @FXML
    private Button createButton;

    @FXML
    PasswordField create_emailTextField;

    @FXML
    Label createMessageLabel;

    @FXML
    PasswordField create_passwordTextField;

    @FXML
    TextField create_usernameTextField;

    @FXML
    private Hyperlink forgotPasswordLink;

    @FXML
    private Button loginButton;

    @FXML
    Label loginMessageLabel;

    @FXML
    private AnchorPane loginPane;

    @FXML
    PasswordField login_enterPasswordField;

    @FXML
    TextField login_usernameTextField;

    @FXML
    PasswordField repeat_passwordTextField;

    @FXML
    private Hyperlink signUpLink;


    @FXML
    private ImageView imageOne;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // File brandingFile= new File("C:\\Users\\saioc\\Desktop\\Facultate\\Licenta\\StrongerEveryDay\\images\\1.png");
       // Image brandingImage=new Image(brandingFile.toURI().toString());
       // imageOne.setImage(brandingImage);
        loginPane.setVisible(true);
        createAccountPane.setVisible(false);

    }

    @FXML
    public void loginButtonOnAction(ActionEvent event) throws SQLException, NoSuchAlgorithmException, IOException {
        loginMessageLabel.setText("You try to login");
        if(!login_usernameTextField.getText().isBlank() && !login_enterPasswordField.getText().isBlank()){
            validateLogin();
        }
        else {
            loginMessageLabel.setText("Empty fields");
        }
    }



    @FXML
    protected void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }



    @FXML
    void forgotPasswordAction(ActionEvent event) throws IOException {

        loginMessageLabel.setText("Unlucky. Functionality in development");
    }



    @FXML
    void signUpAction(ActionEvent event) {
        loginPane.setVisible(false);
        createAccountPane.setVisible(true);
    }


    @FXML
    void createButtonOnAction(ActionEvent event) throws SQLException, NoSuchAlgorithmException {
       //extrage+verifica informatiile introduse

        String username = create_usernameTextField.getText();
        String password = create_passwordTextField.getText();
        String password2 = repeat_passwordTextField.getText();
        String email = create_emailTextField.getText();


        if(!password.equals(password2)){
            createMessageLabel.setText("The passwords are different");
            return;
        }
        if(password.length()<8){
            createMessageLabel.setText("Password - at least 8 characters");
            return;
        }
        if(username.length()<6){
            createMessageLabel.setText("Username - at least 6 characters");
            return;
        }

// Parse the XML file to check if the username already exists
        try {
            File inputFile = new File("users.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList userList = doc.getElementsByTagName("user");

            for (int i = 0; i < userList.getLength(); i++) {
                Node userNode = userList.item(i);
                if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element userElement = (Element) userNode;
                    String existingUsername = userElement.getElementsByTagName("username").item(0).getTextContent();
                    if (existingUsername.equals(username)) {
                        createMessageLabel.setText("Username already exists");
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            createMessageLabel.setText("Error accessing user data");
            return;
        }

        // If username doesn't exist, add the new user to the XML file
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse("users.xml");

            Node rootElement = doc.getDocumentElement();

            Element newUser = doc.createElement("user");

            Element usernameElement = doc.createElement("username");
            usernameElement.appendChild(doc.createTextNode(username));
            newUser.appendChild(usernameElement);

            Element passwordElement = doc.createElement("password");
            passwordElement.appendChild(doc.createTextNode(password));
            newUser.appendChild(passwordElement);

            Element emailElement = doc.createElement("email");
            emailElement.appendChild(doc.createTextNode(email));
            newUser.appendChild(emailElement);

            rootElement.appendChild(newUser);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("users.xml"));

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);

            createMessageLabel.setText("Account created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            createMessageLabel.setText("Error creating account");
        }


        loginPane.setVisible(true);
        createAccountPane.setVisible(false);
        loginMessageLabel.setText("Account created successfully.");
    }


    @FXML
    void backButtonOnAction(ActionEvent event) {
        loginPane.setVisible(true);
        createAccountPane.setVisible(false);
    }


    public void validateLogin(){

        String username = login_usernameTextField.getText();
        String password = login_enterPasswordField.getText();


        try {
            File inputFile = new File("users.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();


            //ia lista cu useri si treci prin ea
            NodeList userList = doc.getElementsByTagName("user");

            for (int i = 0; i < userList.getLength(); i++) {
                Node userNode = userList.item(i);

                if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element userElement = (Element) userNode;

                    //extrage si verifica fiecare username si parola pt fiecare user din fisier
                    String storedUsername = userElement.getElementsByTagName("username").item(0).getTextContent();
                    String storedPassword = userElement.getElementsByTagName("password").item(0).getTextContent();

                    if (Objects.equals(storedUsername, username) && Objects.equals(storedPassword, password)) {
                        userFound = true;
                        getFirstPage();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!userFound) {
            loginMessageLabel.setText("Incorrect username or password");
        }
        }

    public void getFirstPage() throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(sample.Main.class.getResource("page1.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1500, 800);
        Stage FirstPageStage = new Stage();
        FirstPageStage.setTitle("Baza de date accidente");
        FirstPageStage.setScene(scene);
        FirstPageStage.show();
    }

}

