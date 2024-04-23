package com.projet.azizprojet.Controllers;

import com.projet.azizprojet.entities.Annonce;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.controlsfx.control.Rating;

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
        // You can also set other details like liked, rating, etc. if needed
    }
    @FXML
    void comment(ActionEvent event) {

    }

    @FXML
    void dislike(ActionEvent event) {

    }

    @FXML
    void like(ActionEvent event) {

    }

}
