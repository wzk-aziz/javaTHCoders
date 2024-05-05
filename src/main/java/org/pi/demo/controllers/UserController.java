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

import javax.swing.*;
import java.io.*;
import java.sql.Connection;
import java.util.Objects;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

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
    public void start(Stage stage) throws IOException, UserNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(UserController.class.getResource("/org/pi/demo/User.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("user login");
        stage.setScene(scene);
        stage.show();
        Preferences userPreferences = Preferences.userRoot();
        String retrievedValue = userPreferences.get("remember", "defaultValue");
        System.out.println("Retrieved value for key '" + "remember" + "': " + retrievedValue);
        boolean isNotEmpty = userPreferences.get("remember", null) != null;

        if(isNotEmpty) {
            stage.close();
            loginauto();
        }

    }
    public static void main(String[] args) {
        launch();
    }

    public void initialize() throws BackingStoreException, UserNotFoundException, IOException {
        Preferences userPreferences = Preferences.userRoot();
        //userPreferences.clear();
        String retrievedValue = userPreferences.get("remember", "defaultValue");
        System.out.println("Retrieved value for key '" + "remember" + "': " + retrievedValue);
        //userPreferences.clear();
        }



    private void loginauto() throws UserNotFoundException, IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/pi/demo/profile.fxml")));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();

    }


    private void saveCredentials(String email, String password) {
        Preferences userPreferences = Preferences.userRoot();
            userPreferences.put("remember",emailField.getText());
            String retrievedValue = userPreferences.get("remember", "defaultValue");
    }
    private void clearCredentials() throws BackingStoreException {
        Preferences userPreferences = Preferences.userRoot();
        userPreferences.clear();
    }
    @FXML
    public void rememberMe(ActionEvent actionEvent) throws BackingStoreException {
        if (rememberMeCheckBox.isSelected()) {
            String email = emailField.getText();
            String password = passwordField.getText();
        } else {
            Preferences userPreferences = Preferences.userRoot();
            userPreferences.clear();
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
    void onLoginClick(ActionEvent actionEvent) throws UserNotFoundException, BackingStoreException {
        String email = emailField.getText();
        String password = passwordField.getText();
        // Retrieve user from the database
        User user = userService.getUserbyEmail(email);



        // Verify password
        if (!userService.verifyPassword(password, user.getPassword())) {
            System.out.println("no");
            JOptionPane.showMessageDialog(null, "password is incorrect");

            return;
        }
        try {
            // Pass the user's ID to the profile controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/pi/demo/Profile.fxml"));
            Parent root = loader.load();
            ProfileController profileController = loader.getController();
            profileController.initializeProfile(user.getId());
            if (rememberMeCheckBox.isSelected()) {
                saveCredentials(email, password);
            } else {
                // Clear saved credentials if "Remember Me" checkbox is not checked
                clearCredentials();
            }
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            // Create an instance of the mailing class

        } catch (IOException e) {
            e.printStackTrace();
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
