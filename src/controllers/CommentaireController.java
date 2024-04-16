package controllers;
import Entites.Actualite;
import Entites.Commentaire;
import Service.ServiceActualite;
import Service.ServiceCommentaire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javafx.scene.input.MouseEvent;

public class CommentaireController {
    private Commentaire selectedCommentaire;
    public void setSelectedCommentaire(Commentaire selectedCommentaire) {
        this.selectedCommentaire = selectedCommentaire;
        initialize();
    }
    @FXML
    private TableColumn<Commentaire, String> colCommentaire;

    @FXML
    private TableColumn<Commentaire, LocalDate> colDateCommentaire;

    @FXML
    private TableView<Commentaire> tableViewCom;

    @FXML
    private TextArea textAreaCommentaire;
    private final ServiceCommentaire ser = new ServiceCommentaire();
    private final ServiceActualite serA = new ServiceActualite();
    private int actualiteId;

    public void setActualiteId(int actualiteId) {
        this.actualiteId = actualiteId;
        initialize();
    }

    @FXML
    void initialize() {
        try {

            List<Commentaire> list = ser.readAll();
            //List<Commentaire> list = ser.readByActualiteId(actualiteId);
            // Créer des propriétés de type PropertyValueFactory pour les colonnes du TableView
            PropertyValueFactory<Commentaire, String> contenuC = new PropertyValueFactory<>("contenuC");
            PropertyValueFactory<Commentaire, LocalDate> dateC = new PropertyValueFactory<>("dateC");

            colCommentaire.setCellValueFactory(contenuC);
            colDateCommentaire.setCellValueFactory(dateC);

            // Charger les données dans le TableView
            ObservableList<Commentaire> obers = FXCollections.observableList(list);
            tableViewCom.setItems(obers);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    @FXML
    void AjouteerCommentaire(ActionEvent event) {
       /* LocalDate currentDate = LocalDate.now();

        java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);

        Commentaire rec = new Commentaire(actualite.getId(), 7, textAreaCommentaire.getText(),sqlDate);

        try {
            ser.ajouter(rec);

            initialize();
        } catch (SQLException e) {
            // Gérer les exceptions
            e.printStackTrace();
        }*/
        try {

            String contenuC = textAreaCommentaire.getText();
            LocalDate currentDate = LocalDate.now();

            java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);
            Commentaire c1 = new Commentaire( contenuC, sqlDate);
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation");
            alert1.setContentText("Commentaire ajouté avec succès");
            alert1.showAndWait();

            ser.ajouter(c1);
        } catch (SQLException | NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void ModifieerCommentaire(ActionEvent event) {
        Commentaire commentaireSelectionne = tableViewCom.getSelectionModel().getSelectedItem();

        if (commentaireSelectionne != null) {

            TextInputDialog dialog = new TextInputDialog(commentaireSelectionne.getContenuC());
            dialog.setTitle("Modifier Commentaire");
            dialog.setHeaderText("Entrez le nouveau Commentaire pour l'Actualité :");
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
    void SupprimeerCommentaire(ActionEvent event) throws SQLException {
        Commentaire actualiteselect = tableViewCom.getSelectionModel().getSelectedItem();
        if (actualiteselect != null) {
            ser.delete(actualiteselect);
            initialize();
        } else {
            System.out.println("Aucun Commentaire sélectionné.");
        }
    }
    @FXML
    void handleTableClick(MouseEvent mouseEvent ){
        if (mouseEvent.getClickCount() == 2) {
            Commentaire selectedCommentaire= tableViewCom.getSelectionModel().getSelectedItem();
            if (selectedCommentaire != null) {
                textAreaCommentaire.setText(selectedCommentaire.getContenuC());
            }
        }
    }
}
