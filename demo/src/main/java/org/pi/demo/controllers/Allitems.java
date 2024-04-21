package org.pi.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.pi.demo.Interfaces.MyListener;
import org.pi.demo.entities.Items;

public class Allitems {


    @FXML
    private Label item_condition;

    @FXML
    private ImageView item_image;

    @FXML
    private Label item_name;

    @FXML
    private Label item_quantity;

    @FXML
    private Label item_ref;
    private Items item;
    private MyListener myListener;


    @FXML
    void click(MouseEvent event) {

            myListener.OnClickListner(item);
    }

    public void setData(Items item, MyListener myListener) {
        this.item = item;
        this.myListener = myListener;
        System.out.println("setData method called. Item: " + item); // Debug print statement
        item_name.setText(item.getName());
        item_condition.setText(item.getPart_condition());
        item_ref.setText(item.getRef());
        item_quantity.setText(String.valueOf(item.getQuantity()));

        System.out.println("Image path: " + item.getPhotos()); // Print the image path

        try {
            Image image = new Image("file:" + item.getPhotos());
            item_image.setImage(image);
            item_image.setFitHeight(100); // Set the maximum height
            item_image.setFitWidth(100); // Set the maximum width
            item_image.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage()); // Print any exceptions
        }
    }

    public void setMyListener(MyListener myListener) {
        this.myListener = myListener;
    }
}