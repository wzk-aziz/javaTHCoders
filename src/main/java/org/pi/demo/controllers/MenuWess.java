package org.pi.demo.controllers;

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

public class MenuWess {
    @FXML
    private TextField aff;
    @FXML
    void consulterReclamation(ActionEvent event) throws IOException {
        Parent AjouterReclamationParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AjouterReclamation.fxml")));
        Scene AjouterReclamationScene = new Scene(AjouterReclamationParent);

        // This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(AjouterReclamationScene);
        window.show();
    }

    @FXML
    void consulterEchange(ActionEvent event) throws IOException {
        Parent AjouterEchangeParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AjouterEchange.fxml")));
        Scene AjouterEchangeScene = new Scene(AjouterEchangeParent);

        // This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(AjouterEchangeScene);
        window.show();
    }

    @FXML
    void allEchanges(ActionEvent event) throws IOException {
        System.out.println("button works");
        Parent AjouterEchangeParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/DisplayEchangesfront.fxml")));
        Scene AjouterEchangeScene = new Scene(AjouterEchangeParent);

        // This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(AjouterEchangeScene);
        window.show();
    }



    @FXML
    void allReclamations(ActionEvent event) throws IOException {
        System.out.println("button works");
        Parent AjouterReclamationParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/DisplayReclamationsfront2.fxml")));
        Scene AjouterReclamationScene = new Scene(AjouterReclamationParent);

        // This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(AjouterReclamationScene);
        window.show();
    }

}
