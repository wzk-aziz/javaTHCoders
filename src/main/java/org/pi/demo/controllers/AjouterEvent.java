package org.pi.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.pi.demo.entities.Events;
import org.pi.demo.entities.Events;
import org.pi.demo.services.EventService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class AjouterEvent {
    @FXML
    private TextField capacity;
    @FXML
    private DatePicker debutEvent;

    @FXML
    private TextField descEvent;

    @FXML
    private TextField error;

    @FXML
    private DatePicker finEvent;

    @FXML
    private ImageView imageTF;

    @FXML
    private TextField nomEvent;

    @FXML
    private TextField place;

    private String imagePath = null; // Variable to store the image path

    @FXML
    public void initialize() {

    }
    @FXML
    void ListeEvent(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/pi/demo/Displayeventsback.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   @FXML
void ajouterEvent(ActionEvent event) {
    try {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Get the selected start and end dates
        LocalDate selectedStartDate = debutEvent.getValue();
        LocalDate selectedEndDate = finEvent.getValue();


        // Check if the selected start date is before the current date
        if (selectedStartDate.isBefore(currentDate)) {
            showAlert("Start date cannot be before the current date");
            return;
        }

        // Check if the selected end date is before the selected start date
        if (selectedEndDate.isBefore(selectedStartDate) || selectedEndDate.isEqual(selectedStartDate)) {
            showAlert("End date cannot be before or equal to the start date");
            return;
        }

        // Validate the input fields
        if (nomEvent.getText().isEmpty()) {
            showAlert("Event Name is required");
            return;
        }

        if (place.getText().isEmpty()) {
            showAlert("Event Place is required");
            return;
        }

        if (descEvent.getText().isEmpty()) {
            showAlert("Event Description is required");
            return;
        }

        if (capacity.getText().isEmpty()) {
            showAlert("Event Capacity is required");
            return;
        }

        if (debutEvent.getValue() == null) {
            showAlert("Event Start Date is required");
            return;
        }

        if (finEvent.getValue() == null) {
            showAlert("Event End Date is required");
            return;
        }

        if (imagePath == null) {
            showAlert("Event Image is required");
            return;
        }

        String eventName = nomEvent.getText();
        String eventPlace = place.getText();
        String eventDescription = descEvent.getText();
        int eventCapacity = Integer.parseInt(capacity.getText());
        Date eventStartDate = java.sql.Date.valueOf(debutEvent.getValue());
        Date eventEndDate = java.sql.Date.valueOf(finEvent.getValue());
        String eventImage = imagePath; // Use the stored image path

        Events newEvent = new Events(eventName, eventPlace, eventDescription, eventImage, eventCapacity, eventStartDate, eventEndDate);
        EventService.getInstance().addEvent(newEvent);

        // Clear the fields after adding the event
        nomEvent.clear();
        place.clear();
        descEvent.clear();
        capacity.clear();
        debutEvent.getEditor().clear();
        finEvent.getEditor().clear();
        imageTF.setImage(null); // Clear the image field
        imagePath = null; // Clear the image path
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private void showAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Validation Error");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
    @FXML
    void displayev(MouseEvent event) {

    }

    @FXML
    void image(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imageTF.setImage(image);
            imagePath = selectedFile.getAbsolutePath(); // Store the image path
        } else {
            System.out.println("No file selected");
        }
    }
@FXML
     void listeEvent(MouseEvent mouseEvent) {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/pi/demo/Displayeventsback.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
}

