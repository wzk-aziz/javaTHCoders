package org.pi.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ResetPasswordController {

    @FXML
    private Label InvalidPassword;

    @FXML
    private Label PasswordDontMatch;

    @FXML
    private PasswordField confirmNewPass;

    @FXML
    private TextField newPass;

    @FXML
    private Button resetPassword;

    @FXML
    private Label returnBtn;

    @FXML
    void resetPasswordBtn(ActionEvent event) {

    }

    @FXML
    void returnBtn(MouseEvent event) {

    }

}
