package com.projet.azizprojet.Controllers;

import com.projet.azizprojet.HelloApplication;
import com.projet.azizprojet.ImServices.CommentServiceImp;
import com.projet.azizprojet.entities.Comment;
import com.projet.azizprojet.entities.CommentDTO;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CommentController implements Initializable {
	@FXML
	private DatePicker date;

	int etat = 0;

	@FXML
	private TextArea desc;

	@FXML
	private Button disbtn;

	@FXML
	private Button likebtn;

	@FXML
	private ListView<CommentDTO> listv;

	private AnnonceController annonceController;

	public void setAnnonceController(AnnonceController annonceController) {
		this.annonceController = annonceController;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		loadData();
	}

	@FXML
	void add(ActionEvent event) {
		String descText = desc.getText().trim();

		if (descText.isEmpty() || date.getValue() == null) {
			showAlert("Error", "Please fill in all fields before adding.");
			return;
		}

		CommentServiceImp com = new CommentServiceImp();
		com.ajouter(new Comment(descText, java.sql.Date.valueOf(date.getValue()), annonceController.idAn, etat));
		listv.getItems().clear();
		loadData();
	}

	@FXML
	void delete(ActionEvent event) {
		CommentServiceImp com = new CommentServiceImp();
		com.supprimer(new Comment(idCom));
		listv.getItems().clear();
		loadData();
	}

	@FXML
	void dislike(ActionEvent event) {
		CommentServiceImp com = new CommentServiceImp();
		com.modifier(new Comment(idCom, desc.getText(), java.sql.Date.valueOf(date.getValue()), annonceController.idAn, 0));
		listv.getItems().clear();
		loadData();
	}

	@FXML
	void edit(ActionEvent event) {
		CommentServiceImp com = new CommentServiceImp();
		com.modifier(new Comment(idCom, desc.getText(), java.sql.Date.valueOf(date.getValue()), annonceController.idAn, etat));
		listv.getItems().clear();
		loadData();
	}

	@FXML
	void like(ActionEvent event) {
		CommentServiceImp com = new CommentServiceImp();
		com.modifier(new Comment(idCom, desc.getText(), java.sql.Date.valueOf(date.getValue()), annonceController.idAn, 1));
		listv.getItems().clear();
		loadData();
	}

	@FXML
	void post(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Annonce.fxml"));
		Scene scene2 = new Scene(fxmlLoader.load());
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		app_stage.setScene(scene2);
		app_stage.show();
	}

	int idCom;

	void loadData() {
		CommentServiceImp com = new CommentServiceImp();
		listv.getItems().clear();
		listv.getItems().addAll(com.AfficheR());
		listv.getSelectionModel().selectedItemProperty().addListener((observableValue, commentDTO, t1) -> {
			if (listv.getSelectionModel().getSelectedItem() != null) {
				CommentDTO commentDTO1 = listv.getSelectionModel().getSelectedItem();
				desc.setText(commentDTO1.getContenu());
				date.setValue(commentDTO1.getDatecommnt().toLocalDate());
				etat = commentDTO1.getLiked();
				if (t1.getLiked() == 1) {
					likebtn.setDisable(true);
					disbtn.setDisable(false);
				} else if (t1.getLiked() == 0) {
					disbtn.setDisable(true);
					likebtn.setDisable(false);
				}
				idCom = t1.getId();
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
}
