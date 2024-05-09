package org.pi.demo.controllers;
import java.io.IOException;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.pi.demo.entities.Reclamation;
import org.pi.demo.services.ReclamationService;


import java.sql.SQLException;
import java.util.Objects;


public class AjouterReclamation {

    @FXML
    private TextField RecDetails;

    @FXML
    private TextField RecNom;

    @FXML
    private TextField RecSujet;

    @FXML
    private TextField error;

    @FXML
    void ModifierReclamation(ActionEvent event) {
        // Implement update logic here
        // For example:
        try {
            // Get the data from the text fields
            String name = RecNom.getText();
            String sujetText = RecSujet.getText();
            String detailsText = RecDetails.getText();
            // Field validation
            if (name == null || name.isEmpty()) {
                error.setText("write the name to modify the raclamation");
                error.setStyle("-fx-text-fill: red;");
                return;
            }
            if (sujetText == null || sujetText.isEmpty()) {
                error.setText("Subject cannot be empty");
                error.setStyle("-fx-text-fill: red;");
                return;
            }
            if (detailsText == null || detailsText.isEmpty()) {
                error.setText("details cannot be empty");
                error.setStyle("-fx-text-fill: red;");
                return;
            }

            // Update the reclamation in the database
            ReclamationService reclamationService = ReclamationService.getInstance();
            reclamationService.updateReclamation(name, sujetText, detailsText);

            error.setText("Reclamation updated successfully!");
            error.setStyle("-fx-text-fill: green;"); // Set the text color to green
        } catch (SQLException e) {
            error.setText("Error while updating Reclamation: " + e.getMessage());
            error.setStyle("-fx-text-fill: red;"); // Set the text color to red
            e.printStackTrace();
        }

    }

    @FXML
    void SupprimerReclamation(ActionEvent event) {
        // Implement delete logic here
        // For example:
        try {
            // Get the data from the text fields
            String name = RecNom.getText();
            // Field validation
            if (name == null || name.isEmpty()) {
                error.setText("write the name to delete the raclamation");
                error.setStyle("-fx-text-fill: red;");
                return;
            }

            // Delete the reclamation from the database
            ReclamationService reclamationService = ReclamationService.getInstance();
            reclamationService.SupprimerReclamation(name);

            error.setText("Reclamation deleted successfully!");
            error.setStyle("-fx-text-fill: green;"); // Set the text color to green
        } catch (SQLException e) {
            error.setText("Error while deleting Reclamation: " + e.getMessage());
            error.setStyle("-fx-text-fill: red;"); // Set the text color to red
            e.printStackTrace();
        }

    }

    @FXML
    void ajouterReclamation(ActionEvent event) {
        String name = RecNom.getText();
        String sujetText = RecSujet.getText();
        String detailsText = RecDetails.getText();
        // Field validation
        if (name == null || name.isEmpty()) {
            error.setText("Name cannot be empty");
            error.setStyle("-fx-text-fill: red;");
            return;
        }
        if (sujetText == null || sujetText.isEmpty()) {
            error.setText("Subject cannot be empty");
            error.setStyle("-fx-text-fill: red;");
            return;
        }
        if (detailsText == null || detailsText.isEmpty()) {
            error.setText("details cannot be empty");
            error.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            // Get the current date
            LocalDate currentDate = LocalDate.now();
            // Find the selected exchange from the database
            ReclamationService reclamationService = ReclamationService.getInstance();

            // Create a new Items object and add it to the database
            Reclamation reclamations = new Reclamation(name,sujetText,detailsText, currentDate.atStartOfDay()); // Assuming photos is an empty string
            ReclamationService.getInstance().addReclamation(reclamations);
            error.setText("Reclamation added successfully!");
            error.setStyle("-fx-text-fill: green;"); // Set the text color to green
        } catch (SQLException e) {
            error.setText("Error while adding Reclamation: " + e.getMessage());
            error.setStyle("-fx-text-fill: red;"); // Set the text color to red
            e.printStackTrace();
        }


    }




    
    @FXML
    void menu(ActionEvent event) throws IOException {
        Parent AjouterReclamationParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MenuWess.fxml")));
        if (AjouterReclamationParent == null) {
            System.err.println("Error: MenuWess.fxml not found!");
            return;
        }
        Scene AjouterReclamationScene = new Scene(AjouterReclamationParent);

        // This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(AjouterReclamationScene);
        window.show();

    }

}
