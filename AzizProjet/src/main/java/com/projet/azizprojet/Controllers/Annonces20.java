package com.projet.azizprojet.Controllers;

import com.projet.azizprojet.HelloApplication;
import com.projet.azizprojet.entities.Annonce;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.io.IOException;

public class Annonces20 {

    @FXML
    private Label date_annonces;

    @FXML
    private Label description_annonces;

    @FXML
    private Button disbtn;
    private int likeStatus = 0;

    private int clickCount = 0;
    @FXML
    private Button likebtn;

    @FXML
    private Rating myrating;

    @FXML
    private Label titre_annonces;
    private Annonce selectedAnnonce;

    public void initialize() {
        // Initialize UI components
        // For example, you might want to hide the buttons until an annonce is selected
        disbtn.setVisible(false);
        likebtn.setVisible(false);
        myrating.setVisible(false);
    }


    public void setAnnonceDetails(Annonce annonce) {
        // Set the details of the selected annonce
        selectedAnnonce = annonce;
        titre_annonces.setText(annonce.getTitre());
        date_annonces.setText(annonce.getDatedepub().toString());
        description_annonces.setText(annonce.getDescription());
        if (annonce.getLiked() == 1) {
            likebtn.setText("Liked");
        } else {
            likebtn.setText("Like");
        }
        // Set rating
        myrating.setRating(annonce.getRating());

        // Show buttons and rating

        likebtn.setVisible(true);
        myrating.setVisible(true);
    }

    // You can also set other details like liked, rating, etc. if needed

    @FXML
    void comment(ActionEvent event) {

    }

   
    @FXML
    private Label titreLabel;

    @FXML
    public String getTitre() {
        return titreLabel.getText();
    }


    @FXML
    void like(ActionEvent event) {
        if (selectedAnnonce != null) {
            clickCount++;

            switch (clickCount) {
                case 1:
                    // First click, change color to green and text to "Liked"
                    likebtn.setStyle("-fx-background-color: #00FF00;"); // Green color
                    likebtn.setText("Liked");
                    selectedAnnonce.setLiked(1);
                    break;
                case 2:
                    // Second click, change color to red and text to "Dislike"
                    likebtn.setStyle("-fx-background-color: #FF0000;"); // Red color
                    likebtn.setText("Dislike");
                    selectedAnnonce.setLiked(0);
                    break;
                case 3:
                    // Third click, reset to initial state (blue color, text "Like")
                    likebtn.setStyle("-fx-background-color: #6495ED;"); // Blue color
                    likebtn.setText("Like");
                    selectedAnnonce.setLiked(0);
                    clickCount = 0; // Reset click count
                    break;
            }

            // Update UI or send update to database
        }
    }


        public void commenta(ActionEvent actionEvent) {
    }


    public Annonce getAnnonceDetails() {
        return selectedAnnonce;
    }

    public void dislike(ActionEvent actionEvent) {
    }
}
