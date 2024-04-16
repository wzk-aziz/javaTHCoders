package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuBackCrontroller {
    public void gestionFacture(ActionEvent actionEvent) {
    }

    public void gestionParking(ActionEvent actionEvent) {
    }

    public void gestionUtilisateur(ActionEvent actionEvent) {
    }

    public void gestionActualite(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Actualite.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gestionReclamation(ActionEvent actionEvent) {
    }

    public void gestionTransport(ActionEvent actionEvent) {
    }
}
