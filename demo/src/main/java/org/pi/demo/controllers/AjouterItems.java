package org.pi.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.pi.demo.entities.Items;
import org.pi.demo.services.ItemsService;
import org.pi.demo.services.InventoryService;
import org.pi.demo.entities.Inventory;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.List;

public class AjouterItems {

    @FXML
    private ChoiceBox<String> conditionItem;

    @FXML
    private TextField descItem;

    @FXML
    private TextField error;

    @FXML
    private ImageView imageItem;

    @FXML
    private TextField nomItem;

    @FXML
    private ChoiceBox<Integer> quantityItem;

    @FXML
    private TextField refItem;


    @FXML
    private ChoiceBox<String> inventoryItem;


    @FXML
    public void initialize() {
        // Populate the quantity ChoiceBox with numbers from 1 to 20
        for (int i = 1; i <= 20; i++) {
            quantityItem.getItems().add(i);
        }

        // Populate the condition ChoiceBox with the values "Used", "New", "Refurbished"
        conditionItem.getItems().addAll("Used", "New", "Refurbished");

        // Populate the inventory ChoiceBox with the names of the inventories
        try {
            InventoryService inventoryService = InventoryService.getInstance();
            List<Inventory> inventories = inventoryService.AfficherInventory();
            for (Inventory inventory : inventories) {
                inventoryItem.getItems().add(inventory.getTitle());
            }
        } catch (SQLException e) {
            error.setText("Error while loading inventories: " + e.getMessage());
            e.printStackTrace();
        }
    }



    @FXML
    void ListedesItems(ActionEvent event) {

    }

    @FXML
    void ajouterItem(ActionEvent event) {
        String name = nomItem.getText();
        String description = descItem.getText();
        String ref = refItem.getText();
        String condition = conditionItem.getValue();
        int quantity = quantityItem.getValue();
        String inventoryName = inventoryItem.getValue();
        String photos = "";
        // Field validation
        if (name.isEmpty()) {
            error.setText("Name cannot be empty");
            error.setStyle("-fx-text-fill: red;");
            return;
        }
        if (description.isEmpty()) {
            error.setText("Description cannot be empty");
            error.setStyle("-fx-text-fill: red;");
            return;
        }
        if (ref.isEmpty()) {
            error.setText("Reference cannot be empty");
            error.setStyle("-fx-text-fill: red;");
            return;
        }
        if (condition.isEmpty()) {
            error.setText("Condition cannot be empty");
            error.setStyle("-fx-text-fill: red;");
            return;
        }
        if (quantity <= 0) {
            error.setText("Quantity must be greater than 0");
            error.setStyle("-fx-text-fill: red;");
            return;
        }
        if (inventoryName.isEmpty()) {
            error.setText("Inventory name cannot be empty");
            error.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            // Find the selected inventory from the database
            InventoryService inventoryService = InventoryService.getInstance();
            List<Inventory> inventories = inventoryService.searchByTitle(inventoryName);
            if (inventories.isEmpty()) {
                error.setText("Inventory not found: " + inventoryName);
                error.setStyle("-fx-text-fill: red;");
                return;
            }
            Inventory inventory = inventories.get(0);

            // Create a new Items object and add it to the database
            Items item = new Items(name, description, ref, condition, quantity, inventory.getId(), ""); // Assuming photos is an empty string
            ItemsService.getInstance().addItems(item);
            error.setText("Item added successfully!");
            error.setStyle("-fx-text-fill: green;"); // Set the text color to green
        } catch (SQLException e) {
            error.setText("Error while adding item: " + e.getMessage());
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
        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(selectedFile);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageItem.setImage(image);

                // Define the path to the directory where you want to save the image
                String directoryPath = "src/main/resources/uploadedphotos";

                // Create the directory if it doesn't exist
                File directory = new File(directoryPath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Define the path to the new image file
                String imagePath = directoryPath + "/" + selectedFile.getName();

                // Write the image to the new file
                ImageIO.write(bufferedImage, "png", new File(imagePath));

                // Now you can store imagePath in your database
            } catch (IOException e) {
                error.setText("Error while loading image: " + e.getMessage());
                error.setStyle("-fx-text-fill: red;");
                e.printStackTrace();
            }
        }
    }

    @FXML
    void menu(ActionEvent event) {

    }

}
