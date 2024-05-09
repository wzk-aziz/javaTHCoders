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
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.pi.demo.entities.Events;
import org.pi.demo.entities.Reservation;
import org.pi.demo.services.EventService;
import org.pi.demo.services.ReservationService;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;



import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;



public class AfficherReservations implements Initializable {
    @FXML
    private Button pdf;


    @FXML
    private ComboBox<String> combopdf;

    @FXML
    private Button btnsupprimer;

    @FXML
    private ListView<String> list2;

    private ObservableList<String> reservationsWithEvents;



    @FXML
    void pdf(ActionEvent event) {
        String selectedEventName = combopdf.getValue();
        if (selectedEventName != null && !selectedEventName.isEmpty()) {
            try {
                EventService eventService = EventService.getInstance();
                Events selectedEvent = eventService.getEventByName(selectedEventName);
                if (selectedEvent != null) {
                    // Fetch reservations related to the selected event
                    ReservationService reservationService = ReservationService.getInstance();
                    List<Reservation> reservations = reservationService.getReservationsByEventId(selectedEvent.getId());

                    // Generate PDF with reservations
                    generatePDF(selectedEvent, reservations);
                } else {
                    showAlert("Selected event not found.");
                }
            } catch (SQLException | IOException e) {
                showAlert("Failed to generate PDF: " + e.getMessage());
            }
        } else {
            showAlert("Please select an event.");
        }
    }

    private void generatePDF(Events event, List<Reservation> reservations) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

                // Add image to the top-left corner
                try (InputStream in = getClass().getResourceAsStream("/org/pi/demo/LOGOKHOUDHWHET.jpg")) {
                    PDImageXObject image = PDImageXObject.createFromByteArray(document, IOUtils.toByteArray(in), "image");
                    contentStream.drawImage(image, 50, page.getMediaBox().getHeight() - 50, image.getWidth() / 4, image.getHeight() / 4);
                }

                // Add title with event name
                String title = "Liste des réservations pour '" + event.getEvent_name() + "'";
                float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(title) / 1000 * 12; // Font size is 12, convert from font units to points
                float titleHeight = PDType1Font.HELVETICA_BOLD.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * 12; // Font size is 12, convert from font units to points
                float titleXPosition = (page.getMediaBox().getWidth() - titleWidth) / 2; // Center horizontally
                float titleYPosition = page.getMediaBox().getHeight() - 50 - titleHeight; // Place 50 points from the top
                contentStream.beginText();
                contentStream.newLineAtOffset(titleXPosition, titleYPosition);
                contentStream.showText(title);
                contentStream.endText();

                // Draw title line in red
                contentStream.setStrokingColor(Color.RED);
                contentStream.moveTo(titleXPosition, titleYPosition - 5); // Move to the starting point of the line
                contentStream.lineTo(titleXPosition + titleWidth, titleYPosition - 5); // Draw the line
                contentStream.stroke();

                // Draw table headers for events
                float yPosition = page.getMediaBox().getHeight() - 150;
                float xStart = 50;
                float tableWidth = page.getMediaBox().getWidth() - 2 * xStart;
                float rowHeight = 20;
                float cellMargin = 5;

                // Draw table headers for events
                drawTableRow(contentStream, xStart, yPosition, rowHeight, cellMargin, tableWidth,
                        "Nom de l'événement", "Place", "Date de début", "Date de fin");

                // Draw each event in a row
                yPosition -= rowHeight;
                drawTableRow(contentStream, xStart, yPosition, rowHeight, cellMargin, tableWidth,
                        event.getEvent_name(), event.getPlace(), event.getStart_date().toString(), event.getEnd_date().toString());

                // Draw table headers for reservations
                yPosition -= rowHeight * 2;
                drawTableRow(contentStream, xStart, yPosition, rowHeight, cellMargin, tableWidth,
                        "Nom", "Address", "Phone");

                // Draw each reservation in a table row
                for (Reservation reservation : reservations) {
                    yPosition -= rowHeight;
                    drawTableRow(contentStream, xStart, yPosition, rowHeight, cellMargin, tableWidth,
                            reservation.getNom(), reservation.getAddress(), String.valueOf(reservation.getPhone()));
                }
            }

            // Save the PDF document
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                document.save(file);
            }
        }
    }


    private void drawTableRow(PDPageContentStream contentStream, float xStart, float yPosition, float rowHeight,
                              float cellMargin, float tableWidth, String... cellValues) throws IOException {
        float xPosition = xStart;

        // Draw vertical lines
        contentStream.moveTo(xStart, yPosition);
        contentStream.lineTo(xStart + tableWidth, yPosition);
        contentStream.stroke();

        // Draw cells and horizontal lines
        for (String value : cellValues) {
            contentStream.moveTo(xPosition, yPosition);
            contentStream.lineTo(xPosition, yPosition - rowHeight);
            contentStream.stroke();

            contentStream.beginText();
            contentStream.newLineAtOffset(xPosition + cellMargin, yPosition - rowHeight + cellMargin);
            contentStream.showText(value);
            contentStream.endText();

            xPosition += tableWidth / cellValues.length;
        }

        // Draw the last vertical line
        contentStream.moveTo(xPosition, yPosition);
        contentStream.lineTo(xPosition, yPosition - rowHeight);
        contentStream.stroke();
    }




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
        loadEvents();
    }
    private void loadEvents() {
        try {
            EventService eventService = EventService.getInstance();
            List<Events> events = eventService.AfficherEvent();
            ObservableList<String> eventNames = FXCollections.observableArrayList();
            for (Events event : events) {
                eventNames.add(event.getEvent_name());
            }
            combopdf.setItems(eventNames);
        } catch (SQLException e) {
            showAlert("Failed to load events: " + e.getMessage());
        }
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
