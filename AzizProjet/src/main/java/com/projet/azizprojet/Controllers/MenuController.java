package com.projet.azizprojet.Controllers;

import com.projet.azizprojet.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MenuController {
    @FXML
    private TextField aff;

    @FXML
    void consulterInventaire(ActionEvent event) throws IOException {
        Parent AjouterInventaireParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/pi/demo/AjouterInventaire.fxml")));
        Scene AjouterInventaireScene = new Scene(AjouterInventaireParent);

        // This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(AjouterInventaireScene);
        window.show();
    }

    @FXML
    void consulteritems(ActionEvent event) throws IOException {
        Parent AjouterInventaireParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/pi/demo/AjouterItems.fxml")));
        Scene AjouterInventaireScene = new Scene(AjouterInventaireParent);

        // This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(AjouterInventaireScene);
        window.show();
    }

    @FXML
    void allitems(ActionEvent event) throws IOException {
        Parent AjouterInventaireParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/pi/demo/itemsforshow.fxml")));
        Scene AjouterInventaireScene = new Scene(AjouterInventaireParent);

        // This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(AjouterInventaireScene);
        window.show();
    }


    @FXML
    void Annonces(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Annonce.fxml"));

        Scene scene2 = new Scene(fxmlLoader.load());
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(scene2);
        app_stage.show();
    }

    @FXML
    void consulterannonces(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("DisplayAnnonces.fxml"));

        Scene scene2 = new Scene(fxmlLoader.load());
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(scene2);
        app_stage.show();
    }


}




