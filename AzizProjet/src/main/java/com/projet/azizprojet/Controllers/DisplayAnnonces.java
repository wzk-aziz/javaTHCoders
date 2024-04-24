package com.projet.azizprojet.Controllers;

import com.projet.azizprojet.HelloApplication;
import com.projet.azizprojet.ImServices.AnnoncesServicesImp;
import com.projet.azizprojet.entities.Annonce;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class DisplayAnnonces {

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    @FXML
    private VBox root;

    @FXML
    private TextField searchField;

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


    @FXML
    void comment(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Comment.fxml"));
        Scene scene2 = new Scene(fxmlLoader.load());
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(scene2);
        app_stage.show();
    }

    public void Annonces(ActionEvent actionEvent) {
    }
}
