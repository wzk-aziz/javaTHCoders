package org.pi.demo.controllers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.pi.demo.MainFX;
import org.pi.demo.entities.Events;
import org.pi.demo.services.EventService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class AjouterEvent {
    @FXML
    private TextField capacity;
    @FXML
    private DatePicker debutEvent;
    @FXML
    private WebView webView;
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

        // Restrict the phone number field to accept only numbers
        capacity.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        }));

        place.textProperty().addListener((observable, oldValue, newValue) -> {
            WebEngine webEngine = webView.getEngine();
            String mapHtml = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <title>Embedded Google Map</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<iframe\n" +
                    "        width=\"600\"\n" +
                    "        height=\"450\"\n" +
                    "        frameborder=\"0\" style=\"border:0\"\n" +
                    "        src=\"http://maps.google.com/maps?q=" + newValue + "&output=embed\"\n" +
                    "        allowfullscreen>\n" +
                    "</iframe>\n" +
                    "</body>\n" +
                    "</html>";
            webEngine.loadContent(mapHtml);
        });
    }
//private String mapboxApiKey = "sk.eyJ1IjoiYWNocmFmbmFnIiwiYSI6ImNsdnd1em15MzF6YXMya3JyYWIzd3YwaW0ifQ.0xw-UhjREKfNsrTFy_4ogg";
//private double latitude = 0;
//    private double longitude = 0;
//
//  @FXML
//public void initialize() {
//    WebEngine webEngine = webView.getEngine();
//    String mapHtml = "<!DOCTYPE html>\n" +
//            "<html>\n" +
//            "<head>\n" +
//            "    <title>Mapbox Map</title>\n" +
//            "    <script src='https://api.mapbox.com/mapbox-gl-js/v2.7.0/mapbox-gl.js'></script>\n" +
//            "    <link href='https://api.mapbox.com/mapbox-gl-js/v2.7.0/mapbox-gl.css' rel='stylesheet' />\n" +
//            "    <style>\n" +
//            "        body { margin: 0; padding: 0; }\n" +
//            "        #map { position: absolute; top: 0; bottom: 0; width: 100%; }\n" +
//            "    </style>\n" +
//            "</head>\n" +
//            "<body>\n" +
//            "<div id='map'></div>\n" +
//            "<script>\n" +
//            "    mapboxgl.accessToken = '" + mapboxApiKey + "';\n" +
//            "    var map = new mapboxgl.Map({\n" +
//            "        container: 'map',\n" +
//            "        style: 'mapbox://styles/mapbox/streets-v11',\n" +
//            "        center: [0, 0],\n" + // Default center
//            "        zoom: 1\n" + // Default zoom
//            "    });\n" +
//            "    var mapLoaded = false;\n" +
//            "    map.on('load', function () {\n" +
//            "        mapLoaded = true;\n" +
//            "    });\n" +
//            "</script>\n" +
//            "</body>\n" +
//            "</html>";
//    webEngine.loadContent(mapHtml);
//
//    // Update map location when text field changes
//    place.textProperty().addListener((observable, oldValue, newValue) -> {
//        try {
//            // Geocode the place name to get the coordinates
//            String geocodingUrl = "https://api.mapbox.com/geocoding/v5/mapbox.places/" + newValue + ".json?access_token=" + mapboxApiKey;
//            URL url = new URL(geocodingUrl);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            StringBuilder response = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                response.append(line);
//            }
//            reader.close();
//
//            // Parse the JSON response to get the coordinates
//            JSONObject json = new JSONObject(response.toString());
//            JSONArray features = json.getJSONArray("features");
//            if (features.length() > 0) {
//                JSONObject feature = features.getJSONObject(0);
//                JSONArray center = feature.getJSONArray("center");
//                longitude = center.getDouble(0);
//                latitude = center.getDouble(1);
//
//                // Update the map with the new coordinates
//                webEngine.executeScript(
//                    "if (mapLoaded) {" +
//                    "    map.flyTo({center: [" + longitude + ", " + latitude + "], zoom: 15});" +
//                    "}"
//                );
//            } else {
//                System.out.println("No features found in the JSON response");
//            }
//        } catch (IOException | JSONException e) {
//            e.printStackTrace();
//        }
//    });
//}
    @FXML
    void ListeEvent(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/pi/demo/Displayeventsback.fxml"));
            Parent root = fxmlLoader.load();

            // Get the current stage and set the new scene on it
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
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

            // Check if the selected start date is null
            if (selectedStartDate == null) {
                showAlert("All fields must be filled");
                return;
            }

            // Check if the selected start date is before the current date
            if (selectedStartDate.isBefore(currentDate)) {
                showAlert("Start date cannot be before the current date");
                return;
            }

            // Check if the selected end date is null
            if (selectedEndDate == null) {
                showAlert("All fields must be filled");
                return;
            }

            // Check if the selected end date is before the selected start date
            if (selectedEndDate.isBefore(selectedStartDate) || selectedEndDate.isEqual(selectedStartDate)) {
                showAlert("End date cannot be before or equal to the start date");
                return;
            }

            // Validate input fields
            if (nomEvent.getText().isEmpty() || capacity.getText().isEmpty() || descEvent.getText().isEmpty() || place.getText().isEmpty() || imagePath == null) {
                System.out.println("Fields cannot be empty!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Validation");
                alert.setHeaderText("Fields cannot be empty!");
                alert.setContentText("All fields must be filled!");
                alert.showAndWait();
                return;
            }

            String eventName = nomEvent.getText();
            String eventPlace = place.getText();
            String eventDescription = descEvent.getText();
            int eventCapacity = Integer.parseInt(capacity.getText());
            Date eventStartDate = java.sql.Date.valueOf(selectedStartDate);
            Date eventEndDate = java.sql.Date.valueOf(selectedEndDate);
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

            // Get the current stage and set the new scene on it
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
