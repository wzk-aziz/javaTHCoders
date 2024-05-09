package org.pi.demo.controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.pi.demo.services.EchangeService;
import org.pi.demo.entities.Echange;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class AjouterEchange {

    @FXML
    private TextField EchangeEtat;

    @FXML
    private TextField EchangeOffre;

    @FXML
    private TextField error;

    @FXML
    private ImageView imageItem;
    private String imagePath = null; //
    private Echange echange;



    @FXML
    void ajouterEchange(ActionEvent event) {
        String name = EchangeEtat.getText();
        String description = EchangeOffre.getText();
        String photos = imagePath;
        // Field validation
        if (name == null || name.isEmpty()) {
            error.setText("Name cannot be empty");
            error.setStyle("-fx-text-fill: red;");
            return;
        }
        if (description == null || description.isEmpty()) {
            error.setText("Description cannot be empty");
            error.setStyle("-fx-text-fill: red;");
            return;
        }
        if (photos == null || photos.isEmpty()) {
            error.setText("You must select an image");
            error.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            // Find the selected exchange from the database
            EchangeService echangeService = EchangeService.getInstance();

            // Create a new Items object and add it to the database
            Echange echanges = new Echange(name, description, photos); // Assuming photos is an empty string
            EchangeService.getInstance().addEchange(echanges);
            error.setText("exchange added successfully!");
            error.setStyle("-fx-text-fill: green;"); // Set the text color to green
        } catch (SQLException e) {
            error.setText("Error while adding exchange: " + e.getMessage());
            error.setStyle("-fx-text-fill: red;"); // Set the text color to red
            e.printStackTrace();
        }

    }

    @FXML
    void displayev(MouseEvent event) {

    }

    @FXML
    void handleUploadButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(selectedFile);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageItem.setImage(image);
                imagePath = selectedFile.getAbsolutePath(); // Store the image path
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No file selected");
        }
    }
    public void initData(Echange echange) {
        this.echange = echange;}

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
