package org.pi.demo.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.pi.demo.entities.Event;
import org.pi.demo.services.EventService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class Gerereventback {

    @FXML
    private TableView<Event> TableView_Event;

    @FXML
    private TableColumn<Event, Integer> TableView_col_Event_capacity;

    @FXML
    private TableColumn<Event, LocalDate> TableView_col_Event_date_debut;

    @FXML
    private TableColumn<Event, LocalDate> TableView_col_Event_date_fin;

    @FXML
    private TableColumn<Event, String> TableView_col_Event_description;

    @FXML
    private TableColumn<Event, Integer> TableView_col_Event_id;

    @FXML
    private TableColumn<Event, String> TableView_col_Event_localisation;

    @FXML
    private TableColumn<Event, String> TableView_col_Event_nom;

    @FXML
    private DatePicker debutEvent;

    @FXML
    private TextField descEvent;

    @FXML
    private ChoiceBox<Integer> eventcapacity;

    @FXML
    private DatePicker finEvent;

    @FXML
    private ImageView imageEvent;

    @FXML
    private TextField nomEvent;

    @FXML
    private TextField place;
    private String imagePath = null;
    @FXML
    void initialize() {
        try {
            EventService eventService = EventService.getInstance();
            List<Event> eventList = eventService.AfficherEvent();
            ObservableList<Event> events = FXCollections.observableArrayList(eventList);

            TableView_Event.setItems(events);
            TableView_col_Event_capacity.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCapacity()).asObject());
            TableView_col_Event_date_debut.setCellValueFactory(cellData -> {
                java.util.Date startDate = new java.util.Date(cellData.getValue().getStart_date().getTime());
                LocalDate localStartDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                return new SimpleObjectProperty<>(localStartDate);
            });
            TableView_col_Event_date_fin.setCellValueFactory(cellData -> {
                java.util.Date endDate = new java.util.Date(cellData.getValue().getEnd_date().getTime());
                LocalDate localEndDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                return new SimpleObjectProperty<>(localEndDate);
            });

            TableView_col_Event_description.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
            //TableView_col_Event_id.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
            TableView_col_Event_localisation.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlace()));
            TableView_col_Event_nom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEvent_name()));

            TableView_Event.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    // Update fields with the selected event's data
                    nomEvent.setText(newSelection.getEvent_name());
                    descEvent.setText(newSelection.getDescription());
                    place.setText(newSelection.getPlace());
                    //debutEvent.setValue(newSelection.getStart_date().toLocalDate());
                    //finEvent.setValue(newSelection.getEnd_date().toLocalDate());
                    eventcapacity.setValue(newSelection.getCapacity());

                    // Update imageEvent with the selected event's image
                    imageEvent.setImage(new Image("file:" + newSelection.getImage()));
                }
            });

            // Populate eventcapacity ChoiceBox
            eventcapacity.getItems().addAll(1, 2, 3); // Add appropriate capacity values

            // Add more initialization code as needed
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    @FXML
    void ModifierEvent(ActionEvent event) throws SQLException {
        // Get the selected event
        Event selectedEvent = TableView_Event.getSelectionModel().getSelectedItem();

        // Check if an event is selected
        if (selectedEvent != null) {
            // Validate the fields
            if (nomEvent.getText().isEmpty() || descEvent.getText().isEmpty() || place.getText().isEmpty() ||
                    debutEvent.getValue() == null || finEvent.getValue() == null || eventcapacity.getValue() == null || imagePath == null) {
                // Display an error message
                System.out.println("All fields must be filled out");
                return;
            }

            // Get the data from the fields and update the selected event with this data
            selectedEvent.setEvent_name(nomEvent.getText());
            selectedEvent.setDescription(descEvent.getText());
            selectedEvent.setPlace(place.getText());
            selectedEvent.setStart_date(java.sql.Date.valueOf(debutEvent.getValue()));
            selectedEvent.setEnd_date(java.sql.Date.valueOf(finEvent.getValue()));
            selectedEvent.setCapacity(eventcapacity.getValue());
            selectedEvent.setImage(imagePath);

            // Update the event in the database
            EventService.getInstance().updateEvent(selectedEvent);

            // Refresh the table
            TableView_Event.refresh();

            // Reselect the event
            TableView_Event.getSelectionModel().select(selectedEvent);

            // Clear the fields
            nomEvent.clear();
            descEvent.clear();
            place.clear();
            debutEvent.setValue(null);
            finEvent.setValue(null);
            eventcapacity.setValue(null);
            imageEvent.setImage(null);
            imagePath = null;
        }
    }


    @FXML
    void SupprimerEvent(ActionEvent event) {
        // Get the selected item
        Event selectedEvent = TableView_Event.getSelectionModel().getSelectedItem();

        // Check if an item is selected
        if (selectedEvent != null) {
            // Remove the selected item from the TableView
            TableView_Event.getItems().remove(selectedEvent);

            try {
                // Remove the selected item from the database
                EventService.getInstance().SupprimerEvent(selectedEvent.getId());

                // Show an alert indicating the item has been deleted
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Event Deleted");
                alert.setHeaderText(null);
                alert.setContentText("The event has been successfully deleted.");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Show an alert indicating no item was selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select an event to delete.");
            alert.showAndWait();
        }
    }
    @FXML
    void handleUploadButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(selectedFile);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageEvent.setImage(image);
                imagePath = selectedFile.getAbsolutePath(); // Store the image path
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No file selected");
        }
    }

    @FXML
    void returnhomepage(ActionEvent event) {

    }

}
