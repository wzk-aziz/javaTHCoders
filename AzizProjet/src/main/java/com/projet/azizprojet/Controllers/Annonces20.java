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
        disbtn.setVisible(true);
        likebtn.setVisible(true);
        myrating.setVisible(true);
    }

    // You can also set other details like liked, rating, etc. if needed

    @FXML
    void comment(ActionEvent event) {

    }

    @FXML
    void dislike(ActionEvent event) {

    }

    @FXML
    private Label titreLabel;

    @FXML
    public String getTitre() {
        return titreLabel.getText();
    }


    @FXML
    void like(ActionEvent event) {

    }


    public void commenta(ActionEvent actionEvent) {
    }


    public Annonce getAnnonceDetails() {
        return selectedAnnonce;
    }

}