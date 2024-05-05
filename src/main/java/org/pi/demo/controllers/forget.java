package org.pi.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.pi.demo.Exceptions.UserNotFoundException;
import org.pi.demo.services.PasswordResetService;

import java.io.IOException;

public class forget {

    @FXML
    private Label InvalidPhoneNumber;

    @FXML
    private Label ResendCode;

    @FXML
    private Button SendCode;

    @FXML
    private TextField email;

    @FXML
    private Label forgetPassword;


    @FXML
    void ResendCode(MouseEvent event) throws UserNotFoundException {
        String phoneNumber = ""; // Get the phone number from previous steps
        String mail = email.getText();
        // Call sendVerificationCode method from PasswordResetService
        PasswordResetService resetService = new PasswordResetService();
        resetService.sendVerificationCode(mail);
    }

    @FXML
    void sendCodeBtn(ActionEvent event) throws UserNotFoundException, IOException {
        String mail = email.getText();
            // Call sendVerificationCode method from PasswordResetService
        PasswordResetService resetService = new PasswordResetService();
        resetService.sendVerificationCode(mail);

            // Navigate to RandomCodeController
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/pi/demo/RandomCode.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setUserData(mail);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    void returnClicked(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/pi/demo/User.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
