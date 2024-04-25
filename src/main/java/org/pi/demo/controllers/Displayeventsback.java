package org.pi.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.pi.demo.Interfaces.MyListener;
import org.pi.demo.entities.Events;
import org.pi.demo.entities.Events;
import org.pi.demo.services.EventService;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Displayeventsback {

    @FXML
    private Button btnmodif;

    @FXML
    private Button btnsupprimer;

    @FXML
    private TextField capacity;

    @FXML
    private VBox chosenEventCard;

    @FXML
    private DatePicker date_debut;

    @FXML
    private Label date_debut_label;

    @FXML
    private DatePicker date_fin;

    @FXML
    private TextField description;

    @FXML
    private Label event_capacity_label;

    @FXML
    private ImageView event_image;

    @FXML
    private TextField event_name;

    @FXML
    private Label event_name_label;

    @FXML
    private TextField event_place;

    @FXML
    private Label event_place_label;

    @FXML
    private GridPane grid;
    @FXML
    private TextField searchbar;
    @FXML
    private ScrollPane scroll;
    private int selectedEventId;
    private LocalDate start_date;
    private LocalDate end_date;
    private String imagePath = null; // Variable to store the image path


    private MyListener myListener;

    public void setStart_dateString(String start_dateString) {
        // Assuming date format in the database is "yyyy-MM-dd"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.start_date = LocalDate.parse(start_dateString, formatter);
    }

    public void setEnd_dateString(String end_dateString) {
        // Assuming date format in the database is "yyyy-MM-dd"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.end_date = LocalDate.parse(end_dateString, formatter);
    }

    @FXML
    public void initialize() throws SQLException {
        EventService eventService = EventService.getInstance();
        List<Events> eventsList = eventService.AfficherEvent();
        loadEvents();
        myListener = new MyListener() {

            @Override
            public void OnClickListner(Events events) {
                // Update UI elements with event details
                event_name.setText(events.getEvent_name());
                event_place.setText(events.getPlace());
                capacity.setText(String.valueOf(events.getCapacity()));
                selectedEventId = events.getId();
                // Convert java.sql.Date to java.util.Date
                Date startDate = new Date(events.getStart_date().getTime());
                Date endDate = new Date(events.getEnd_date().getTime());

                // Set date values
                date_debut.setValue(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                date_fin.setValue(endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                description.setText(events.getDescription());

                // Load and display event image
                try {
                    Image image = new Image("file:" + events.getImage());
                    event_image.setImage(image);
                    event_image.setFitHeight(100); // Set the maximum height
                    event_image.setFitWidth(100); // Set the maximum width
                    event_image.setPreserveRatio(true);
                } catch (Exception e) {
                    System.out.println("Error loading image: " + e.getMessage());
                }
            }
        };


        int column = 0;
        int row = 0;
        try {
            for (int i = 0; i < eventsList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/org/pi/demo/Events.fxml"));

                AnchorPane pane = fxmlLoader.load();

                EventsController eventController = fxmlLoader.getController();
                eventController.setData(eventsList.get(i), myListener);

                if (column == 2) {
                    column = 0;
                    row++;
                }

                grid.add(pane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void image(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            event_image.setImage(image);
            imagePath = selectedFile.getAbsolutePath(); // Update the image path
        } else {
            System.out.println("No file selected");
        }
    }

    @FXML
    void modif_event(ActionEvent event) {
        // Validate the input fields
        if (event_name.getText().isEmpty() || event_place.getText().isEmpty() || capacity.getText().isEmpty() ||
                date_debut.getValue() == null || date_fin.getValue() == null || description.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }

        // Update the event
        try {
            EventService eventService = EventService.getInstance();
            Events eventToUpdate = new Events();
            eventToUpdate.setId(selectedEventId);
            eventToUpdate.setEvent_name(event_name.getText());
            eventToUpdate.setPlace(event_place.getText());
            eventToUpdate.setCapacity(Integer.parseInt(capacity.getText()));
            eventToUpdate.setStart_date(java.sql.Date.valueOf(date_debut.getValue()));
            eventToUpdate.setEnd_date(java.sql.Date.valueOf(date_fin.getValue()));
            eventToUpdate.setDescription(description.getText());
            eventToUpdate.setImage(imagePath); // Assuming imagePath is the path of the image

            boolean isUpdated = eventService.updateEvent(eventToUpdate);
            if (isUpdated) {
                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Update Successful");
                alert.setHeaderText(null);
                alert.setContentText("The event has been updated successfully");
                alert.showAndWait();

                // Clear the input fields
                event_name.clear();
                event_place.clear();
                capacity.clear();
                date_debut.setValue(null);
                date_fin.setValue(null);
                description.clear();
                event_image.setImage(null);
                loadEvents();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Update Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to update the event");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void supp_event(ActionEvent event) {
        // Check if an event is selected
        if (event_name.getText().isEmpty() || event_place.getText().isEmpty() || capacity.getText().isEmpty() ||
                date_debut.getValue() == null || date_fin.getValue() == null || description.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Deletion Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select an event to delete");
            alert.showAndWait();
            return;
        }

        // Confirm the deletion
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this event?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() != ButtonType.OK) {
            return;
        }

        // Delete the event
        try {
            EventService eventService = EventService.getInstance();
            boolean isDeleted = eventService.SupprimerEvent(selectedEventId);
            if (isDeleted) {
                // Show success message
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Deletion Successful");
                successAlert.setHeaderText(null);
                successAlert.setContentText("The event has been deleted successfully");
                successAlert.showAndWait();

                // Clear the input fields
                event_name.clear();
                event_place.clear();
                capacity.clear();
                date_debut.setValue(null);
                date_fin.setValue(null);
                description.clear();
                event_image.setImage(null);

                // Refresh the events
                loadEvents();
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Deletion Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Failed to delete the event");
                errorAlert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadEvents() throws SQLException {
        EventService eventService = EventService.getInstance();
        List<Events> eventsList = eventService.AfficherEvent();
        int column = 0;
        int row = 0;
        try {
            // Clear the GridPane
            grid.getChildren().clear();

            for (int i = 0; i < eventsList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/org/pi/demo/Events.fxml"));

                AnchorPane pane = fxmlLoader.load();

                EventsController eventController = fxmlLoader.getController();
                eventController.setData(eventsList.get(i), myListener);

                if (column == 2) {
                    column = 0;
                    row++;
                }

                grid.add(pane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void search_btn(ActionEvent event) throws SQLException {
        String searchText = searchbar.getText().toLowerCase();
        System.out.println("Search term: " + searchText); // Print the search term

        EventService eventService = EventService.getInstance();
        List<Events> allEvents = eventService.AfficherEvent();

        List<Events> filteredEvents = allEvents.stream()
                .filter(events -> events.getEvent_name().toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        System.out.println("Number of filtered events: " + filteredEvents.size()); // Print the size of the filtered items list

        grid.getChildren().clear();

        if (filteredEvents.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Events Found");
            alert.setHeaderText(null);
            alert.setContentText("No Events match the search term: " + searchText);
            alert.showAndWait();
            loadEvents();
        } else {
            int column = 0;
            int row = 1;
            for (Events events : filteredEvents) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/org/pi/demo/Events.fxml"));

                try {
                    AnchorPane pane = fxmlLoader.load();

                    EventsController eventController = fxmlLoader.getController();
                    eventController.setData(events, myListener);

                    if (column == 2) {
                        column = 0;
                        row++;
                    }

                    grid.add(pane, column++, row);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

