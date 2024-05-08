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
import org.pi.demo.services.PasswordResetService;
import java.io.IOException;
import java.util.Objects;

public class RandomCodeController {

    @FXML
    private Label InvalidCode;

    @FXML
    private Label ResendCode;

    @FXML
    private Button Submit;

    @FXML
    private TextField code;

    @FXML
    private Label forgetPassword;
    private PasswordResetService passwordResetService = PasswordResetService.getInstance(); // Initialize this in the constructor or with dependency injection

    @FXML
    void ResendCode(MouseEvent event) {

    }

    @FXML
    void returnClicked(MouseEvent event) {

    }
    @FXML
    void submitBtn(ActionEvent event) throws IOException {
        String verificationCode = code.getText();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String email = (String) stage.getUserData();
        System.out.println(verificationCode);

        // Verify the verification code
        if (passwordResetService.verifySMSCode(email, verificationCode)) {
            // Navigate to ResetPasswordController
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/pi/demo/RandomCode.fxml")));

            // Pass any necessary data to ResetPasswordController if required
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            InvalidCode.setText("Invalid verification code. Please enter the correct code.");
        }
    }
}

