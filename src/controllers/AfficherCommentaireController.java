package controllers;

import Entites.Actualite;
import Entites.Commentaire;
import Service.ServiceActualite;
import Service.ServiceCommentaire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AfficherCommentaireController {

    @FXML
    private TableColumn<Commentaire, String> colcontenuC;
    @FXML
    private TableColumn<Commentaire, LocalDate> coldateC;
    @FXML
    private TableColumn<Commentaire, Integer> colidCom1;
    @FXML
    private Label lbcontenu;
    @FXML
    private TableView<Commentaire> tableviewc;
    @FXML
    private TableView<Actualite> tableviewAct;
    @FXML
    private TextArea txtcontenuC1;
    @FXML
    private DatePicker txtdateC1;
    @FXML
    private TextField txtidCom1;

    public Commentaire actualiteselect;
    public Actualite selectedActualite;

    private Commentaire selectedCommentaire;
    public Commentaire getActualiteselect() {
        return actualiteselect;
    }

    private final ServiceCommentaire ser = new ServiceCommentaire();
    private final ServiceActualite serA = new ServiceActualite();
    private Actualite actualiteAssociee;
    public void setSelectedActualite(Actualite selectedActualite) {
        this.selectedActualite = selectedActualite;
        initialize();
    }
    public void setSelectedCommentaire(Commentaire selectedCommentaire) {
        this.selectedCommentaire = selectedCommentaire;
        initialize();
    }

    public Label getLbcontenu() {
        return lbcontenu;
    }

    public void setLbcontenu(String lbcontenu) {
        this.lbcontenu.setText(lbcontenu);
    }

    @FXML
    void initialize() {
        /*try {
            // Récupérer les commentaires depuis la base de données
            List<Commentaire> list = ser.readAll();

            // Créer des propriétés de type PropertyValueFactory pour les colonnes du TableView
            PropertyValueFactory<Commentaire, String> contenuC = new PropertyValueFactory<>("contenuC");
            PropertyValueFactory<Commentaire, LocalDate> dateC = new PropertyValueFactory<>("dateC");

            // Associer les PropertyValueFactory aux colonnes du TableView

            colcontenuC.setCellValueFactory(contenuC);
            coldateC.setCellValueFactory(dateC); // Utiliser un PropertyValueFactory pour un LocalDate


            // Charger les données dans le TableView
            ObservableList<Commentaire> obers = FXCollections.observableList(list);
            tableviewc.setItems(obers);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/

        try {
            if (selectedActualite != null) {

                List<Commentaire> list = ser.readCommentairesForSelectedActualite(selectedActualite);

                ObservableList<Commentaire> obse = FXCollections.observableList(list);
                tableviewc.setItems(obse);
                colcontenuC.setCellValueFactory(new PropertyValueFactory<>("contenuC"));
                coldateC.setCellValueFactory(new PropertyValueFactory<>("dateC"));
                colidCom1.setCellValueFactory(new PropertyValueFactory<>("id_com"));
            } else {
                System.out.println("Aucune Actualitée sélectionnée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void AjouterCommentairee(ActionEvent event) {

        Actualite selectedActualite = tableviewAct.getSelectionModel().getSelectedItem();
        if (selectedActualite != null) {
            // Obtenez la date actuelle en tant que LocalDate
            LocalDate currentDate = LocalDate.now();

            java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);

            Commentaire rec = new Commentaire(selectedActualite.getId(), 7, txtcontenuC1.getText(),sqlDate);

            try {
                ser.ajouter(rec);
                initialize();
            } catch (SQLException e) {
                // Gérer les exceptions
                e.printStackTrace();
            }
        } else {

            System.out.println("Aucune Actualitée sélectionnée.");
        }
    }



    @FXML
    void ModifierCommentairee(ActionEvent event) {
        Commentaire commentaireSelectionne = tableviewc.getSelectionModel().getSelectedItem();
        if (commentaireSelectionne != null) {

            TextInputDialog dialog = new TextInputDialog(commentaireSelectionne.getContenuC());
            dialog.setTitle("Modifier Commentaire");
            dialog.setHeaderText("Entrez le nouveau commentaire pour l'actualité :");
            dialog.setContentText("Nouveau Commentaire:");

            Optional<String> result = dialog.showAndWait();

            result.ifPresent(modifiedMessage -> {
                commentaireSelectionne.setContenuC(modifiedMessage);

                try {
                    ser.update(commentaireSelectionne);
                    initialize();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } else {
            System.out.println("Aucun Commentaire sélectionnée.");
        }
    }

    @FXML
    void SupprimerCommentairee(ActionEvent event) throws SQLException {
        Commentaire actualiteselect = tableviewc.getSelectionModel().getSelectedItem();
        if (actualiteselect != null) {

            ser.delete(actualiteselect);
            initialize();
        } else {
            System.out.println("Aucun Commentaire sélectionné.");
        }
    }
    @FXML
    public void handleTableClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            Commentaire selectedCommentaire= tableviewc.getSelectionModel().getSelectedItem();
            if (selectedCommentaire != null) {

                txtcontenuC1.setText(selectedCommentaire.getContenuC());
            }
        }
    }
    public Commentaire getCommentaireSelectionne() {
        return tableviewc.getSelectionModel().getSelectedItem();
    }

    public void setTableViewAct(TableView<Actualite> tableviewAct) {
        this.tableviewAct = tableviewAct;
    }
}



