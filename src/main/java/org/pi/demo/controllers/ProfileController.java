package org.pi.demo.controllers;

import org.pi.demo.Exceptions.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.pi.demo.entities.User;
import org.pi.demo.services.SessionService;
import org.pi.demo.services.UserService;
import org.pi.demo.utils.Type;

public class ProfileController {

    private UserService userService = UserService.getInstance();
    private SessionService sessionService = SessionService.getInstance();

    @FXML
    private Button editProfileBtn;

    @FXML
    private TextField email;

    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private Label name;

    @FXML
    private TextField phone;

    @FXML
    private TextField profession;

    @FXML
    private Label role;
    private User currentUser;
    private boolean isEditMode = false;
    @FXML
    void initialize() {
        // Add listener to firstname text field
        firstname.textProperty().addListener((observable, oldValue, newValue) -> {
            // Update name label with capitalized first name
            //name.setText(capitalizeFirstLetter(newValue));
        });
    }
    public void initializeProfile(int userId) throws UserNotFoundException {
        try {
            currentUser = userService.getUserbyID(userId);
            if (currentUser != null) {
                // Populate the text fields with user data
                firstname.setText(currentUser.getFirstname());
                lastname.setText(currentUser.getName());
                email.setText(currentUser.getEmail());
                phone.setText(currentUser.getPhone());
                profession.setText(currentUser.getProfession());

                // Set the role label based on the user's role
                if (currentUser.getRoles() == Type.ROLE_ADMIN) {
                    role.setText("Admin");
                } else {
                    role.setText("Client");
                }

                // Set the username label (optional)
                name.setText(capitalizeFirstLetter(currentUser.getFirstname())); // or any other field you want to display as username

                setFieldsEditable();
            } else {
                System.out.println("User not found.");
            }
        } catch (UserNotFoundException e) {
            // Handle exception appropriately
            e.printStackTrace();
        }
    }
    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    private void setFieldsEditable() {
        firstname.setEditable(true);
        lastname.setEditable(true);
        email.setEditable(true);
        phone.setEditable(true);
        profession.setEditable(true);
    }

    @FXML
    void deleteAccount(MouseEvent event) {

    }

    @FXML
    void editProfile(ActionEvent event) {

    }

}