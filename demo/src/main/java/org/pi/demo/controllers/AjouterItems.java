package org.pi.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
import java.util.Objects;

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
    private Slider quantity_slider;
    @FXML
    private Label sliderValueLabel;
    private String imagePath = null; // Variable to store the image path

    @FXML
    public void initialize() {

        // Set the minimum, maximum and initial values for the quantity slider
        quantity_slider.setMin(1);
        quantity_slider.setMax(1000);
        quantity_slider.setValue(1);

        // Optional: Add a listener to the slider value property to display the current value
      quantity_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
    sliderValueLabel.setText("" + newValue.intValue());
});
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
    void ListedesItems(ActionEvent event) throws IOException {
        Parent AjouterInventaireParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/pi/demo/DisplayItemsfront.fxml")));
        Scene AjouterInventaireScene = new Scene(AjouterInventaireParent);

        // This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(AjouterInventaireScene);
        window.show();
    }

    @FXML
    void ajouterItem(ActionEvent event) {
        String name = nomItem.getText();
        String description = descItem.getText();
        String ref = refItem.getText();
        String condition = conditionItem.getValue();
        //int quantity = quantityItem.getValue();
        int quantity = (int) quantity_slider.getValue(); // Use the slider value
        String inventoryName = inventoryItem.getValue();
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
        if (ref == null || ref.isEmpty()) {
            error.setText("Reference cannot be empty");
            error.setStyle("-fx-text-fill: red;");
            return;
        }
        if (condition == null || condition.isEmpty()) {
            error.setText("Condition cannot be empty");
            error.setStyle("-fx-text-fill: red;");
            return;
        }
       double value = quantity_slider.getValue();
if (value < quantity_slider.getMin() || value > quantity_slider.getMax()) {
    error.setText("Quantity must be selected");
    error.setStyle("-fx-text-fill: red;");
    return;
}

        if (inventoryName == null || inventoryName.isEmpty()) {
            error.setText("Inventory name cannot be empty");
            error.setStyle("-fx-text-fill: red;");
            return;
        }
        if (photos == null || photos.isEmpty()) {
            error.setText("You must select an image");
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
            Items item = new Items(name, description, ref, condition, quantity, inventory.getId(), photos); // Assuming photos is an empty string
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
        Parent AjouterInventaireParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/pi/demo/Menu.fxml")));
        Scene AjouterInventaireScene = new Scene(AjouterInventaireParent);

        // This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(AjouterInventaireScene);
        window.show();

    }
    @FXML
    void quantity_slider(MouseEvent event) {

    }

}
