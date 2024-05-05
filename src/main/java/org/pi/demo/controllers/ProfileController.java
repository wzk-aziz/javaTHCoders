package org.pi.demo.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.pi.demo.Exceptions.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import org.pi.demo.entities.User;
import org.pi.demo.services.SessionService;
import org.pi.demo.services.UserService;
import org.pi.demo.utils.Type;
import java.util.prefs.Preferences;
import java.io.IOException;

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
                Preferences userPreferences = Preferences.userRoot();
                String retrievedValue = userPreferences.get("1", "defaultValue");

                // Print the retrieved value
                System.out.println("Retrieved value for key '" + "1" + "': " + retrievedValue);
                // Set the role label based on the user's role
                if (currentUser.getRoles() == Type.ROLE_ADMIN) {
                    role.setText("Admin");
                } else {
                    role.setText("Client");
                }

                // Set the username label (optional)
                name.setText(capitalizeFirstLetter(currentUser.getFirstname())); // or any other field you want to display as username

                setFieldsEditable(isEditMode);
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


    private void setFieldsEditable(boolean isEditMode) {
        firstname.setEditable(this.isEditMode);
        lastname.setEditable(this.isEditMode);
        email.setEditable(this.isEditMode);
        phone.setEditable(this.isEditMode);
        profession.setEditable(this.isEditMode);
    }

    @FXML
    void deleteAccount(MouseEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Are you sure you want to delete this account permanently?");
        confirmationAlert.setContentText("This action cannot be undone.");

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    if (currentUser != null) {
                        // Delete the user account
                        userService.deleteUser(currentUser);

                        // Display a success message
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Success");
                        successAlert.setHeaderText("Account Deleted");
                        successAlert.setContentText("Your account has been deleted successfully.");

                        // Navigate to register.fxml
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/pi/demo/User.fxml"));
                        Parent root = fxmlLoader.load();
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();

                        // Show the success message
                        successAlert.showAndWait();
                    } else {
                        System.out.println("No user account to delete.");
                    }
                } catch ( IOException e) {
                    // Handle exception appropriately
                    e.printStackTrace();
                }
            } else {
                System.out.println("Deletion canceled.");
            }
        });
    }
    @FXML
    void profileButton(MouseEvent event) throws UserNotFoundException {
        // Retrieve current user's ID from session and initialize profile
        int userId = sessionService.getCurrentUser().getId();
        initializeProfile(userId);
    }

    @FXML
    void editProfile(ActionEvent event) throws UserNotFoundException, IncorrectPasswordException, InvalidPhoneNumberException, InvalidEmailException, EmptyFieldException {
        if (isEditMode) {
            // Save changes and switch to read-only mode
            saveChanges();
            editProfileBtn.setText("Edit");
            isEditMode = false;
        } else {
            // Enable editing
            editProfileBtn.setText("Save");
            isEditMode = true;
        }
        setFieldsEditable(isEditMode);
    }
    private void saveChanges() throws UserNotFoundException, IncorrectPasswordException, InvalidPhoneNumberException, InvalidEmailException, EmptyFieldException {
        // Update user object with new data
        currentUser.setFirstname(firstname.getText());
        currentUser.setName(lastname.getText());
        currentUser.setEmail(email.getText());
        currentUser.setPhone(phone.getText());
        currentUser.setProfession(profession.getText());
        // Save changes to the database
        userService.updateUser(currentUser);
    }


}