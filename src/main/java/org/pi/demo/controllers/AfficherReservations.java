package org.pi.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import org.pi.demo.entities.Events;
import org.pi.demo.entities.Reservation;
import org.pi.demo.services.EventService;
import org.pi.demo.services.ReservationService;

import java.net.URL;
import java.sql.SQLException;
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
            String[] parts = selectedItem.split(", Nom: ");
            if (parts.length == 2) {
                String idString = parts[0].replace("Reservation ID: ", "");
                int id = Integer.parseInt(idString.trim());
                try {
                    ReservationService reservationService = ReservationService.getInstance();
                    reservationService.SupprimerReservation(id);
                    reservationsWithEvents.remove(selectedItem);
                } catch (SQLException e) {
                    showAlert("Failed to delete reservation: ");
                }
            } else {
                showAlert("Failed to delete reservation: Invalid selection");
            }
        } else {
            showAlert("Failed to delete reservation: No item selected");
        }
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
                String reservationInfo = "Reservation ID: " + reservation.getId() +
                        ", Nom: " + reservation.getNom() +
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

}
