package org.pi.demo.controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.pi.demo.entities.Echange;
import org.pi.demo.services.EchangeService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class ModifierEchangeController {

    @FXML
    private TextField error;

    @FXML
    private TextField EchangeEtat;

    @FXML
    private TextField EchangeOffre;

    @FXML
    private Button modifyButton;

    @FXML
    private Button listeEchangesButton;

    @FXML
    private Button uploadImageButton;

    @FXML
    private ImageView imageItem;

    private Echange echange;

    private final EchangeService echangeService;

    private String imagePath; // Declare imagePath variable here

    public ModifierEchangeController() {
        try {
            this.echangeService = EchangeService.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Error initializing EchangeService", e);
        }
    }

    public void initData(Echange echange) {
        this.echange = echange;
        EchangeEtat.setText(echange.getEtat());
        EchangeOffre.setText(echange.getOffre());
    }

    @FXML
    void ajouterEchange() {
        String newEtat = EchangeEtat.getText();
        String newOffre = EchangeOffre.getText();
        // Check if any of the input fields are empty
        if (newEtat.isEmpty() || newOffre.isEmpty() || imagePath == null || imagePath.isEmpty()) {
            error.setText("Please fill in all fields and upload an image");
            return; // Exit the method if validation fails
        }

        echange.setEtat(newEtat);
        echange.setOffre(newOffre);
        echange.setImage(imagePath); // Set imagePath to the Echange object

        try {
            boolean updateSuccessful = echangeService.updateEchange(echange);
            if (updateSuccessful) {
                error.setText("Echange modified successfully!");
            } else {
                error.setText("Failed to update Echange in the database!");
            }
        } catch (Exception e) {
            error.setText("Error updating Echange: " + e.getMessage());
        }
    }

    @FXML
    void displayev() {
        // Implement the functionality to display the list of exchanges
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
