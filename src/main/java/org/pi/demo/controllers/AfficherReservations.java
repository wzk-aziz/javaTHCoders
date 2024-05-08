package org.pi.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.pi.demo.entities.Events;
import org.pi.demo.entities.Reservation;
import org.pi.demo.services.EventService;
import org.pi.demo.services.ReservationService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AfficherReservations implements Initializable {

    @FXML
    private Button btnsupprimer;

    @FXML
    private ListView<String> list2;

    private ObservableList<String> reservationsWithEvents;
@FXML
void supp_reservation(ActionEvent event) {
    int selectedIndex = list2.getSelectionModel().getSelectedIndex();
    if (selectedIndex != -1) { // Check if an item is selected
        String selectedItem = list2.getSelectionModel().getSelectedItem();
        String[] parts = selectedItem.split(", ");
        if (parts.length == 4) {
            String name = parts[0].replace("Nom: ", "").trim();
            String address = parts[1].replace("Address: ", "").trim();
            String phone = parts[2].replace("Phone: ", "").trim();
            String eventName = parts[3].replace("Event Name: ", "").trim();
            try {
                ReservationService reservationService = ReservationService.getInstance();
                // Get all reservations
                List<Reservation> reservations = reservationService.AfficherReservation();
                for (Reservation reservation : reservations) {
                    // Check if the reservation matches the selected item
                    if (reservation.getNom().equals(name) && reservation.getAddress().equals(address)
                            && String.valueOf(reservation.getPhone()).equals(phone) && reservation.getEvent_id() == getEventIdByName(eventName)) {
                        // Delete the matching reservation
                        reservationService.SupprimerReservation(reservation.getId());
                        reservationsWithEvents.remove(selectedItem);
                        break;
                    }
                }
            } catch (SQLException e) {
                showAlert("Failed to delete reservation: " + e.getMessage());
            }
        } else {
            showAlert("Failed to delete reservation: Invalid selection");
        }
    } else {
        showAlert("Failed to delete reservation: No item selected");
    }
}

private int getEventIdByName(String eventName) throws SQLException {
    EventService eventService = EventService.getInstance();
    List<Events> events = eventService.AfficherEvent();
    for (Events event : events) {
        if (event.getEvent_name().equals(eventName)) {
            return event.getId();
        }
    }
    return -1; // Return -1 if no matching event is found
}

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadReservations();
    }

    private void loadReservations() {
        try {
            ReservationService reservationService = ReservationService.getInstance();
            EventService eventService = EventService.getInstance();

            reservationsWithEvents = FXCollections.observableArrayList();

            for (Reservation reservation : reservationService.AfficherReservation()) {
                Events event = eventService.getEventById(reservation.getEvent_id());
                String reservationInfo =
                        " Nom: " + reservation.getNom() +
                        ", Address: " + reservation.getAddress() +
                        ", Phone: " + reservation.getPhone() +
                        ", Event Name: " + event.getEvent_name();
                reservationsWithEvents.add(reservationInfo);
            }

            list2.setItems(reservationsWithEvents);
        } catch (SQLException e) {
            showAlert("Failed to load reservations: " + e.getMessage());
        }
    }
    @FXML
void back_btn(ActionEvent event) {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/pi/demo/MenuBack.fxml"));
        Parent root = fxmlLoader.load();

        // Get the current stage and set the new scene on it
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
