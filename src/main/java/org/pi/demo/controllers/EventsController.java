package org.pi.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.pi.demo.Interfaces.MyListener;
import org.pi.demo.entities.Events;

import java.awt.*;

public class EventsController {

    @FXML
    private ImageView event_image;

    private MyListener myListener;
    private Events events;

    @FXML
    private Text event_capacity;

    @FXML
    private Text event_date;

    @FXML
    private Text event_desc;
    @FXML
    private Text event_name;
    @FXML
    void clickmouse(MouseEvent event) {
        if (myListener != null && events != null) {
            myListener.OnClickListner(events);
        }
    }

    public void setMyListener(MyListener myListener) {
        this.myListener = myListener;
    }

    public void setData(Events event, MyListener myListener) {
        this.myListener = myListener;
        this.events = event;

        System.out.println("setData method called. Event: " + event); // Debug print statement

        event_name.setText(event.getEvent_name());
        event_capacity.setText(String.valueOf(event.getCapacity()));
        event_date.setText(event.getStart_date().toString());
        event_desc.setText(event.getDescription());


        System.out.println("Image path: " + event.getImage()); // Print the image path

        try {
            Image image = new Image("file:" + event.getImage());
            event_image.setImage(image);
            event_image.setFitHeight(100); // Set the maximum height
            event_image.setFitWidth(100); // Set the maximum width
            event_image.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage()); // Print any exceptions
        }
    }
}
