package controllers;

import Entites.Actualite;
import Service.ServiceActualite;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class AfficherActualitesController {
    @FXML
    private ListView<Actualite> listViewAct;
    public FXMLLoader loader;
    private File selectedFile;
    private Actualite selectedActualite;

    @FXML
    private DetailsActualiteController detailsActualiteController;
    private final ServiceActualite ser = new ServiceActualite();
@FXML
    public void initialize() throws SQLException {
        loader = new FXMLLoader(getClass().getResource("/AfficherActualites.fxml"));
       ObservableList<Actualite> listeActualites = FXCollections.observableArrayList();
        List<Actualite> actualitesExistantes = ser.readAll();
        listeActualites.addAll(actualitesExistantes);

        listViewAct.setItems(listeActualites);

    listViewAct.setCellFactory(param -> new ListCell<Actualite>() {
        @Override
        protected void updateItem(Actualite actualite, boolean empty) {
            super.updateItem(actualite, empty);
            if (empty || actualite == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(actualite.getTitre());

                Label dateLabel = new Label(actualite.getDateA().toString());

                // Créer un ImageView pour l'image
                ImageView imageView = new ImageView();
                imageView.setFitWidth(100); // Ajuster la largeur de l'image
                imageView.setPreserveRatio(true); // Préserver le ratio largeur/hauteur

                // Charger et afficher l'image
                String imagePath = actualite.getImagepath();
                if (imagePath != null && !imagePath.isEmpty()) {
                    Image image = new Image("file:" + imagePath);
                    imageView.setImage(image);
                }

                // Organiser les éléments dans un HBox
                HBox hbox = new HBox(imageView, dateLabel);
                hbox.setSpacing(10); // Espacement entre les éléments

                // Définir le HBox comme contenu graphique de la cellule
                setGraphic(hbox);
            }
        }
    });



    // Gère les événements de sélection d'élément dans la ListView
        listViewAct.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Affichez les détails de l'actualité sélectionnée dans le contrôleur des détails
                detailsActualiteController.afficherDetailsActualite(newValue);
                }}
        );
    }

    private void afficherDetailsActualite(Actualite actualite) {
        // Implémente ici la logique pour afficher toutes les informations de l'actualité
        // Par exemple, dans un autre volet ou une fenêtre modale
    }
    public void ajouterActualite(Actualite nouvelleActualite) {
        listViewAct.getItems().add(nouvelleActualite);
    }
    private String adjustImagePath(String imagePathFromDB) {
        // Vérifier si le chemin contient des barres obliques inverses ('/')
        if (imagePathFromDB.contains("/")) {
            // Remplacer les barres obliques inverses par des barres obliques inverses de votre système de fichiers
            imagePathFromDB = imagePathFromDB.replace("/", "\\");
        }
        return imagePathFromDB;
    }
    @FXML
    private void handleRowClick(Actualite selectedActualite) {
        if (selectedActualite != null) {
            setSelectedActualite(selectedActualite);
            openDetailInterface(selectedActualite);
        } else {
            System.out.println("Aucune actualité sélectionnée.");
        }
    }

    private void setSelectedActualite(Actualite selectedActualite) {
        this.selectedActualite = selectedActualite;
    }

    private void openDetailInterface(Actualite selectedActualite) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsActualite.fxml"));
            Parent root = loader.load();
            DetailsActualiteController controller = loader.getController();
            controller.initData(selectedActualite); // Pass the selected Actualite object to the controller
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception properly
        }
    }



}
