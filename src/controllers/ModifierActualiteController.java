package controllers;
import Entites.Actualite;
import Service.ServiceActualite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;


public class ModifierActualiteController {
    @FXML
    private ComboBox<String> txtCategModif;
    @FXML
    private TextField txtCheminActModif;

    @FXML
    private TextField txtTitreActModif;

    @FXML
    private TextField txtauteurActModif;

    @FXML
    private TextArea txtcontenuActModif;

    @FXML
    private DatePicker txtdateActModif;

    @FXML
    private TextField txtidActModif;
    private final ServiceActualite ser = new ServiceActualite();
    private ObservableList<Actualite> observableList;
    @FXML
    void ModifierAct(ActionEvent event) throws SQLException {
        Actualite Actualitemodifie = creerActualiteModifie();
        if (Actualitemodifie != null) {
            ser.update(Actualitemodifie);
            observableList.setAll(ser.readAll());
            Stage stage = (Stage) txtTitreActModif.getScene().getWindow();
            stage.close();
        }
    }

    private Actualite creerActualiteModifie() {
        int id_actualite = Integer.parseInt(txtidActModif.getText());
        String titre = txtTitreActModif.getText();
        String contenu = txtcontenuActModif.getText();
        // Récupérer la date sélectionnée du DatePicker
        LocalDate localDate = txtdateActModif.getValue();
        // Convertir LocalDate en Date
        Date dateA = java.sql.Date.valueOf(localDate);
        String categorie = txtCategModif.getValue();
        String auteur = txtauteurActModif.getText();
        String imagePath = txtCheminActModif.getText();
        if (titre.isEmpty() || contenu.isEmpty() || dateA == null || categorie.isEmpty() || auteur.isEmpty()) {
            System.out.println("Veuillez remplir tous les champs obligatoires.");
            return null;
        }
        Actualite Actualitemodifie = new Actualite(id_actualite, titre, contenu, dateA, categorie, auteur,imagePath);
        return Actualitemodifie;
    }

    public void initDonneesActualite(Actualite actualite) {
        txtidActModif.setText(String.valueOf(actualite.getId()));
        txtTitreActModif.setText(actualite.getTitre());
        txtcontenuActModif.setText(actualite.getContenu());
        txtCategModif.setValue(actualite.getCategorie());
        // Convertir la Date en LocalDate
        Date date = actualite.getDateA();
        LocalDate localDate = new java.util.Date(date.getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        // Set the value of the DatePicker
        txtdateActModif.setValue(localDate);
        txtauteurActModif.setText(String.valueOf(actualite.getAuteur()));
        txtCheminActModif.setText(actualite.getImagepath());
    }

    public void setObservableList(ObservableList<Actualite> observableList) {
        this.observableList = observableList;
    }
}