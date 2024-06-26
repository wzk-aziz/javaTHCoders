package org.pi.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.pi.demo.Exceptions.EmptyFieldException;
import org.pi.demo.Exceptions.IncorrectPasswordException;
import org.pi.demo.Exceptions.InvalidEmailException;
import org.pi.demo.Exceptions.InvalidPhoneNumberException;
import org.pi.demo.entities.User;
import org.pi.demo.services.UserService;
import org.pi.demo.services.ValidationService;
import org.pi.demo.utils.MyConnection;
import org.pi.demo.utils.Type;

import java.sql.Connection;

public class UserApp {

    @FXML
    private TextField ageField;

    @FXML
    private TextField confirmPasswordField;

    @FXML
    private Label confirmPasswordLabel;

    @FXML
    private TextField emailField;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField firstnameField;

    @FXML
    private Label firstnameLabel;

    @FXML
    private TextField lastnameField;

    @FXML
    private Label lastnameLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField phoneField;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private TextField professionField;

    @FXML
    private Button registerButton;

    @FXML
    void registerOnAction(ActionEvent event) {
        try {
            Connection connection = MyConnection.getInstance().getConnection();
            UserService userService = UserService.getInstance();
            ValidationService validationService = new ValidationService();
            User user = new User();
            if (!firstnameField.getText().isEmpty() || !lastnameField.getText().isEmpty() ||
                    !emailField.getText().isEmpty() || !passwordField.getText().isEmpty()) {
                user.setFirstname(firstnameField.getText());
                user.setName(lastnameField.getText());
                user.setEmail(emailField.getText());
                user.setPassword(passwordField.getText());
                user.setPhone(phoneField.getText());
                user.setProfession(professionField.getText());
                user.setAge(Integer.parseInt(ageField.getText()));
                user.setRoles(Type.ROLE_USER);
                userService.addUser(user);
            } else {
                firstnameLabel.setText("Please enter your firstname.");
                lastnameLabel.setText("Please enter your lastname.");
                passwordLabel.setText("Please enter your password.");
                confirmPasswordLabel.setText("This field if necessary.");
                phoneNumberLabel.setText("Please enter your phone number.");
            }
            if (!validationService.isValidEmail(emailField.getText())) {
                emailLabel.setText("Invalid email, please check your email address.");
            }
            // Valider le format du numéro de téléphone (s'il est fourni)
            if (!phoneNumberLabel.getText().isEmpty() && !validationService.isValidPhoneNumber(phoneField.getText())) {
                phoneNumberLabel.setText("Invalid phone number format.");
            }
            // Valider le format du mot de passe
            if (!validationService.isValidPassword(passwordField.getText())) {
                passwordLabel.setText("Password must contain at least one uppercase letter, one lowercase letter, one digit, and be at least 6 characters long.");
            }
        } catch (InvalidEmailException | IncorrectPasswordException | InvalidPhoneNumberException |
                 EmptyFieldException e) {
            System.err.println(e);
        }
    }

}
