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
import org.pi.demo.entities.Reservation;
import org.pi.demo.services.EventService;
import org.pi.demo.services.ReservationService;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class Reserver {
    @FXML
    private Button btnreserver;

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
    private TextField res_address;

    @FXML
    private TextField res_nom;

    @FXML
    private TextField res_tel;

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

                // After loading the events, check the capacity of the selected event
                Events selectedEvent = null;
                try {
                    selectedEvent = eventService.getEventById(selectedEventId);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (selectedEvent.getCapacity() == 0) {
                    // Disable the "Reserver" button if the capacity is 0
                    btnreserver.setDisable(true);
                } else {
                    // Enable the "Reserver" button if the capacity is greater than 0
                    btnreserver.setDisable(false);
                }

                // Load and display event image
                try {
                    Image image = new Image("file:" + events.getImage());
                    event_image.setImage(image);
                    event_image.setFitHeight(100); // Set the maximum height
                    event_image.setFitWidth(100); // Set the maximum width
                    event_image.setPreserveRatio(true);

                }

                catch (Exception e) {
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

        // Restrict the phone number field to accept only numbers
        res_tel.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        }));

    }


    @FXML
    void reserver_event(ActionEvent event) throws SQLException {
        try {

            // Validate input fields
            if (res_nom.getText().isEmpty() || res_address.getText().isEmpty() || res_tel.getText().isEmpty()) {
                System.out.println("Fields cannot be empty!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Validation");
                alert.setHeaderText("Fields cannot be empty!");
                alert.setContentText("All fields must be filled!");
                alert.showAndWait();
                return;
            }



//            // Validate the input fields
//            if (res_nom.getText().isEmpty()) {
//                showAlert("Name is required");
//                return;
//            }
//
//            if (res_address.getText().isEmpty()) {
//                showAlert("Address is required");
//                return;
//            }
//
//            if (res_tel.getText().isEmpty()) {
//                showAlert("Phone is required");
//                return;
//            }

            // Parse the phone number to an integer
            int phone;
            try {
                phone = Integer.parseInt(res_tel.getText());
            } catch (NumberFormatException e) {
                showAlert("Phone must be a number");
                return;
            }

            // Create a new Reservation object
            Reservation newReservation = new Reservation(res_nom.getText(), res_address.getText(), phone, selectedEventId);

            // Add the reservation to the database
            ReservationService reservationService = ReservationService.getInstance();
            reservationService.addReservation(newReservation);

            // Decrement the event's capacity
            EventService eventService = EventService.getInstance();
            eventService.decrementEventCapacity(selectedEventId);

            // Show success message
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Reservation Successful");
            successAlert.setHeaderText(null);
            successAlert.setContentText("The reservation has been added successfully");
            successAlert.showAndWait();

            // Clear the input fields
            res_nom.clear();
            res_address.clear();
            res_tel.clear();
        } catch (SQLException e) {
            // Show error message
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Reservation Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Failed to add the reservation");
            errorAlert.showAndWait();
        }
        // After successful reservation
        resetFields();
        loadEvents();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
    private void resetFields() {
        event_name.clear();
        event_place.clear();
        capacity.clear();
        date_debut.setValue(null);
        date_fin.setValue(null);
        description.clear();
        event_image.setImage(null);
    }
}

