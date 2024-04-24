package org.pi.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import java.util.Date;

public class AjouterEvent {

    @FXML
    private DatePicker debutEvent;

    @FXML
    private TextField descEvent;

    @FXML
    private TextField error;

    @FXML
    private ChoiceBox<Integer> eventcapacity;

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
        for (int i = 1; i <= 500; i++) {
            eventcapacity.getItems().add(i);
        }
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
            String eventName = nomEvent.getText();
            String eventPlace = place.getText();
            String eventDescription = descEvent.getText();
            int eventCapacity = eventcapacity.getValue();
            Date eventStartDate = java.sql.Date.valueOf(debutEvent.getValue());
            Date eventEndDate = java.sql.Date.valueOf(finEvent.getValue());
            String eventImage = imagePath; // Use the stored image path

            Events newEvent = new Events(eventName, eventPlace, eventDescription, eventImage, eventCapacity, eventStartDate, eventEndDate);
            EventService.getInstance().addEvent(newEvent);

            // Clear the fields after adding the event
            nomEvent.clear();
            place.clear();
            descEvent.clear();
            eventcapacity.getSelectionModel().clearSelection();
            debutEvent.getEditor().clear();
            finEvent.getEditor().clear();
            imageTF.setImage(null); // Clear the image field
            imagePath = null; // Clear the image path
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

