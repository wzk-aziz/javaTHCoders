package org.pi.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.pi.demo.entities.Events;
import org.pi.demo.entities.Reservation;
import org.pi.demo.services.EventService;
import org.pi.demo.services.ReservationService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AfficherReservations implements Initializable {

    @FXML
    private ListView<String> list2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ReservationService reservationService = ReservationService.getInstance();
            EventService eventService = EventService.getInstance();

            ObservableList<String> reservationsWithEvents = FXCollections.observableArrayList();

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
            e.printStackTrace();
        }
    }
}
