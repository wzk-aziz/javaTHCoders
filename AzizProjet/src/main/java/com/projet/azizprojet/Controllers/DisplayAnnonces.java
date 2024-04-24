package com.projet.azizprojet.Controllers;

import com.projet.azizprojet.ImServices.AnnoncesServicesImp;
import com.projet.azizprojet.entities.Annonce;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class DisplayAnnonces {

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    @FXML
    private VBox root;



    private AnnoncesServicesImp annoncesService = new AnnoncesServicesImp();

    public void initialize() {
        loadAnnonces();
    }

    private void loadAnnonces() {
        List<Annonce> annoncesList = annoncesService.afficher();
        int column = 0;
        int row = 0;
        try {
            for (Annonce annonce : annoncesList) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/projet/azizprojet/annonces20.fxml"));
                fxmlLoader.load();
                Annonces20 annonceController = fxmlLoader.getController();
                annonceController.setAnnonceDetails(annonce);

                grid.add(fxmlLoader.getRoot(), column++, row);
                if (column == 2) {
                    column = 0;
                    row++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleCardViewClick(Annonce annonce) {
        // Implement the action you want to perform when a card view is clicked
        System.out.println("Clicked on card view with title: " + annonce.getTitre());
    }

}
