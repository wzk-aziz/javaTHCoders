package org.pi.demo.controllers;


import jakarta.mail.MessagingException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.pi.demo.Exceptions.UserNotFoundException;
import org.pi.demo.entities.User;
import org.pi.demo.services.SessionService;
import org.pi.demo.services.UserService;
import org.pi.demo.services.mailing;
import org.pi.demo.services.ValidationService;
import org.pi.demo.utils.MyConnection;

import java.io.*;
import java.sql.Connection;

public class UserController extends Application {

    @FXML
    private Label EmptyFieldException;

    @FXML
    private Label InvalidEmailExceptionLabel;

    @FXML
    private Label InvalidPasswordExceptionLabel;

    @FXML
    private Label UserNotFoundExceptionLabel;

    @FXML
    private TextField emailField;

    @FXML
    private Label forgetPassword;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberMeCheckBox;

    @FXML
    private Label signUp;


    UserService userService = UserService.getInstance();
    SessionService sessionService = SessionService.getInstance();
    ValidationService validationService = new ValidationService();
    Connection connection = MyConnection.getInstance().getConnection();
    private static final String CREDENTIALS_FILE = "credentials.txt";
    private User currentUser;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UserController.class.getResource("/org/pi/demo/User.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("pi");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }

    public void initialize() {
        loadCredentials();
    }

    private void saveCredentials(String email, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CREDENTIALS_FILE))) {
            writer.write(email + "," + password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadCredentials() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_FILE))) {
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(",");
                emailField.setText(parts[0]);
                passwordField.setText(parts[1]);
                rememberMeCheckBox.setSelected(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void clearCredentials() {
        File file = new File(CREDENTIALS_FILE);
        if (file.exists()) {
            file.delete();
        }
    }
    @FXML
    public void rememberMe(ActionEvent actionEvent) {
        if (rememberMeCheckBox.isSelected()) {
            String email = emailField.getText();
            String password = passwordField.getText();
            saveCredentials(email, password);
        } else {
            clearCredentials();
        }
    }


    @FXML
    void onForgetPasswordClick(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/pi/demo/forget.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onLoginClick(ActionEvent actionEvent) throws UserNotFoundException {
        String email = emailField.getText();
        String password = passwordField.getText();
        // Retrieve user from the database
        User user = userService.getUserbyEmail(email);
        // Check if user exists
        System.out.println("user name");
        //System.out.println(user.getRoles());
        System.out.println("user.getPassword()");
        //System.out.println(user.getPassword());
        System.out.println("password");
        //System.out.println(password);

        // Verify password
        if (!userService.verifyPassword(password, user.getPassword())) {
            System.out.println("no");
            return;
        }
        try {
            // Pass the user's ID to the profile controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/pi/demo/Profile.fxml"));
            Parent root = loader.load();
            ProfileController profileController = loader.getController();
            profileController.initializeProfile(user.getId());

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            // Create an instance of the mailing class

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (rememberMeCheckBox.isSelected()) {
            saveCredentials(email, password);
        } else {
            // Clear saved credentials if "Remember Me" checkbox is not checked
            clearCredentials();
        }
    }


    @FXML
    void onSighUpClick(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/pi/demo/Register.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
