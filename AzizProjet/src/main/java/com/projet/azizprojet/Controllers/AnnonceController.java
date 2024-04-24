package com.projet.azizprojet.Controllers;

import com.projet.azizprojet.HelloApplication;
import com.projet.azizprojet.ImServices.AnnoncesServicesImp;
import com.projet.azizprojet.entities.Annonce;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AnnonceController implements Initializable {
	@FXML
	private Rating myrating;

	@FXML
	private DatePicker date;
	public static int idAn;
	int etat = 0;

	@FXML
	private TextArea desc;

	@FXML
	private ListView<Annonce> listv;

	@FXML
	private TextField titire;

	@FXML
	private Button disbtn;

	@FXML
	private Button likebtn;

	@FXML
	void add(ActionEvent event) {
		String titreText = titire.getText().trim();
		String descText = desc.getText().trim();
		double note = myrating.getRating(); // Retrieve the rating value
		Annonce rec = new Annonce(titreText, java.sql.Date.valueOf(date.getValue()), descText, etat, note);

		if (titreText.isEmpty() || descText.isEmpty() || date.getValue() == null) {
			showAlert("Error", "Please fill in all fields before adding.");
			return;
		}

		AnnoncesServicesImp an = new AnnoncesServicesImp();
		an.ajouter(rec);
		listv.getItems().clear();
		loadData();
	}

	@FXML
	void Menu(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Menu.fxml"));
		Scene scene2 = new Scene(fxmlLoader.load());
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		app_stage.setScene(scene2);
		app_stage.show();
	}

	@FXML
	void comment(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Comment.fxml"));
		Scene scene2 = new Scene(fxmlLoader.load());
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		app_stage.setScene(scene2);
		app_stage.show();
	}

	@FXML
	void delete(ActionEvent event) {
		AnnoncesServicesImp an = new AnnoncesServicesImp();
		an.supprimer(new Annonce(idAn));
		loadData();
		clearField();
	}

	@FXML
	void dislike(ActionEvent event) {
		AnnoncesServicesImp an = new AnnoncesServicesImp();
		double note = myrating.getRating(); // Retrieve the rating value
		an.modifier(new Annonce(idAn, titire.getText(), java.sql.Date.valueOf(date.getValue()), desc.getText(), 0, note));
		loadData();
	}

	@FXML
	void edit(ActionEvent event) {
		AnnoncesServicesImp an = new AnnoncesServicesImp();
		double note = myrating.getRating(); // Retrieve the rating value
		an.modifier(new Annonce(idAn, titire.getText(), java.sql.Date.valueOf(date.getValue()), desc.getText(), etat, note));
		loadData();
	}

	@FXML
	void like(ActionEvent event) {
		AnnoncesServicesImp an = new AnnoncesServicesImp();
		double note = myrating.getRating(); // Retrieve the rating value
		an.modifier(new Annonce(idAn, titire.getText(), java.sql.Date.valueOf(date.getValue()), desc.getText(), 1, note));
		loadData();
		clearField();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		loadData();
	}

	void clearField() {
		titire.clear();
		desc.clear();
		date.getEditor().clear();
		likebtn.setDisable(false);
		disbtn.setDisable(false);
		idAn = 0;
		etat = 0;
	}

	void loadData() {
		AnnoncesServicesImp an = new AnnoncesServicesImp();
		listv.getItems().clear();
		listv.getItems().addAll(an.afficher());
		listv.getSelectionModel().selectedItemProperty().addListener((observableValue, annonce, t1) -> {
			if (t1 != null) {
				titire.setText(t1.getTitre());
				desc.setText(t1.getDescription());
				date.setValue(t1.getDatedepub().toLocalDate());
				etat = t1.getLiked();
				myrating.setRating(t1.getRating()); // Set the rating value
				if (t1.getLiked() == 1) {
					likebtn.setDisable(true);
					disbtn.setDisable(false);
				} else if (t1.getLiked() == 0) {
					disbtn.setDisable(true);
					likebtn.setDisable(false);
				}
				idAn = t1.getId();
			}
		});
	}

	private void showAlert(String title, String content) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}
	/*private void filterAnnonces(String keyword) {
		AnnoncesServicesImp an = new AnnoncesServicesImp();
		listv.getItems().clear();
		listv.getItems().addAll(an.rechercherParTitre(keyword));
	}
	*/

}
