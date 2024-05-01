package org.pi.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.pi.demo.Exceptions.PermissionException;
import org.pi.demo.Exceptions.UserNotFoundException;
import org.pi.demo.entities.User;
import org.pi.demo.services.SessionService;
import org.pi.demo.services.UserService;

import java.io.IOException;
import java.util.List;

public class BackOfficeController {
    private final UserService userService = UserService.getInstance();
    private final SessionService sessionService = SessionService.getInstance();

    @FXML
    private ListView<User> userListView;
    private User currentUser;

    @FXML
    private void initialize() {
        getUsers();
    }

    private void getUsers() {
        List<User> users = userService.getUsers();
        ObservableList<User> observableUsers = FXCollections.observableArrayList(users);
        userListView.setItems(observableUsers);
        userListView.setCellFactory(listView -> UserListCell.create());
        userListView.setFixedCellSize(50); // Set a fixed cell size for virtualization
    }

    @FXML
    private Label usernameLabel;


    @FXML
    void banAccount(MouseEvent event) throws PermissionException {
        User selectedUser = userListView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(usernameLabel.getScene().getWindow());
            alert.setTitle("No User Selected");
            alert.setHeaderText("Please select a user to ban.");
            alert.setContentText("No user was selected in the list view.");
            alert.showAndWait();
            return;
        }

    }

    @FXML
    void profileButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/greenta/Profile.fxml"));
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
    void usersButton(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/greenta/BackOffice.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void signOut(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/greenta/User.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void unlockAccount(ActionEvent actionEvent)  {
        User selectedUser = userListView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            String userEmail = selectedUser.getEmail();
            sessionService.unlockAccount(userEmail);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Unlock Successful");
            alert.setHeaderText(null);
            alert.setContentText("Account unlocked successfully!");
            alert.showAndWait();
            getUsers();
            userListView.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No User Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to unlock.");
            alert.showAndWait();
        }
    }
}