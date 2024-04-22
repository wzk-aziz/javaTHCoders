package org.pi.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.pi.demo.services.EventService;
import org.pi.demo.entities.Event;
import java.sql.SQLException;
import java.util.List;

public class ConsulterEventFront {

    @FXML
    private TextField adresse;

    @FXML
    private GridPane gridPane;

    @FXML
    private GridPane gridePaneTop;

    @FXML
    private ImageView imageevent;

    @FXML
    private TextField nom;

    @FXML
    private TextField num_tel;

    private EventService eventService;

    public void initialize() {
        try {
            EventService eventservice = EventService.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Event> events = EventService.AfficherEvent();

        int row = 0;
        int col = 0;
        for (Event event : events) {
            try {
                String imagePath = "file:" + event.getImage();
                System.out.println("Loading image from: " + imagePath); // Print the image path for debugging
                Image image = new Image(imagePath);
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(100); // Set the maximum height
                imageView.setFitWidth(100); // Set the maximum width
                imageView.setPreserveRatio(true);

                // Add the image to the GridPane
                gridPane.add(imageView, col, row);

                // Increment row and col to move to the next cell in the GridPane
                col++;
                if (col == 3) { // Assuming you want 3 events per row
                    col = 0;
                    row++;
                }
            } catch (Exception e) {
                System.out.println("Error loading image: " + e.getMessage()); // Print any exceptions
            }
        }

    }


    @FXML
    void reserver(ActionEvent event) {

    }

}

