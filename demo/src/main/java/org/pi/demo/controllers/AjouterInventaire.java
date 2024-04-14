package org.pi.demo.controllers;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.pi.demo.entities.Inventory;
import org.pi.demo.entities.Items;
import org.pi.demo.services.InventoryService;
import org.pi.demo.services.ItemsService;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class AjouterInventaire {

    @FXML
    private ListView<String> inventoryList;
    @FXML
    private TextField error;

    @FXML
    private TextField descInventaire;

    @FXML
    private TextField titreinventaire;

    public void initialize() {
        try {
            InventoryService inventoryService = InventoryService.getInstance();
            List<Inventory> inventories = inventoryService.AfficherInventory();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            for (Inventory inventory : inventories) {
                String formattedDate = dateFormat.format(inventory.getAdd_date());
                inventoryList.getItems().add("Title: " + inventory.getTitle() + "\nDescription: " + inventory.getDescription() + "\nAdded on: " + formattedDate);
            }

            // Add a listener to the ListView's selection model
            inventoryList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    // Split the selected inventory string to get the title and description
                    String title = newValue.split("\n")[0].split(": ")[1];
                    String description = newValue.split("\n")[1].split(": ")[1];

                    // Fill the text fields with the title and description
                    titreinventaire.setText(title);
                    descInventaire.setText(description);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void refreshListView() {
        try {
            // Clear the ListView
            inventoryList.getItems().clear();

            // Repopulate the ListView
            InventoryService inventoryService = InventoryService.getInstance();
            List<Inventory> inventories = inventoryService.AfficherInventory();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            for (Inventory inventory : inventories) {
                String formattedDate = dateFormat.format(inventory.getAdd_date());
                inventoryList.getItems().add("Title: " + inventory.getTitle() + "\nDescription: " + inventory.getDescription() + "\nAdded on: " + formattedDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ajouterInventaire(ActionEvent event) {
        String title = titreinventaire.getText();
        String description = descInventaire.getText();

        if (title.isEmpty() || description.isEmpty()) {
            Platform.runLater(() -> {
                error.setText("Title and description cannot be empty!");
                error.setStyle("-fx-text-fill: red;");
            });
            return;
        }

        try {
            // Check if an inventory with the same title already exists
            InventoryService inventoryService = InventoryService.getInstance();
            List<Inventory> existingInventories = inventoryService.searchByTitle(title);
            if (!existingInventories.isEmpty()) {
                error.setText("An inventory with this title already exists!");
                error.setStyle("-fx-text-fill: red;");
                return;
            }

            // Add the new inventory
            Inventory inventory = new Inventory(title, description, null);
            inventoryService.addInventory(inventory);
            Platform.runLater(() -> {
                error.setText("Inventory added successfully!");
                error.setStyle("-fx-text-fill: green;");
                // Clear the text fields
                titreinventaire.clear();
                descInventaire.clear();
                // Refresh the ListView after adding an inventory
                refreshListView();
            });
        } catch (SQLException e) {
            Platform.runLater(() -> {
                error.setText("Error while adding inventory: " + e.getMessage());
                error.setStyle("-fx-text-fill: red;");
            });
            e.printStackTrace();
        }
    }
    @FXML
    void menu(ActionEvent event) {
        try {
            // Load the menu scene
            Parent root = FXMLLoader.load(getClass().getResource("/org/pi/demo/Menu.fxml"));
            Scene scene = new Scene(root);

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Change the scene to the menu
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Modifierinventaire(ActionEvent event) {
        // Get the selected inventory from the ListView
        String selectedInventory = inventoryList.getSelectionModel().getSelectedItem();
        if (selectedInventory == null) {
            error.setText("Please select an inventory to modify.");
            error.setStyle("-fx-text-fill: red;");
            return;
        }

        // Split the selected inventory string to get the title
        String title = selectedInventory.split("\n")[0].split(": ")[1];

        // Get the new title and description from the text fields
        String newTitle = titreinventaire.getText();
        String newDescription = descInventaire.getText();

        if (newTitle.isEmpty() || newDescription.isEmpty()) {
            error.setText("Title and description cannot be empty!");
            error.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            // Get the inventory from the database
            InventoryService inventoryService = InventoryService.getInstance();
            List<Inventory> inventories = inventoryService.searchByTitle(title);
            if (inventories.isEmpty()) {
                error.setText("Inventory not found!");
                error.setStyle("-fx-text-fill: red;");
                return;
            }

            // Update the inventory
            Inventory inventory = inventories.get(0);
            inventory.setTitle(newTitle);
            inventory.setDescription(newDescription);
            inventory.setAdd_date(new java.sql.Timestamp(new java.util.Date().getTime())); // Set the add_date to the current timestamp
            inventoryService.updateInventory(inventory);

            error.setText("Inventory updated successfully!");
            error.setStyle("-fx-text-fill: green;");
        } catch (SQLException e) {
            error.setText("Error while updating inventory: " + e.getMessage());
            error.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
        // Refresh the ListView after modifying an inventory
        refreshListView();
    }


    @FXML
    void SupprimerInventaire(ActionEvent event) {
        // Get the selected inventory from the ListView
        String selectedInventory = inventoryList.getSelectionModel().getSelectedItem();
        if (selectedInventory == null) {
            error.setText("Please select an inventory to delete.");
            error.setStyle("-fx-text-fill: red;");
            return;
        }

        // Split the selected inventory string to get the title
        String title = selectedInventory.split("\n")[0].split(": ")[1];

        try {
            // Get the inventory from the database
            InventoryService inventoryService = InventoryService.getInstance();
            List<Inventory> inventories = inventoryService.searchByTitle(title);
            if (inventories.isEmpty()) {
                error.setText("Inventory not found!");
                error.setStyle("-fx-text-fill: red;");
                return;
            }

            // Check if the inventory contains any items
            Inventory inventory = inventories.get(0);
            ItemsService itemsService = ItemsService.getInstance(); // Assuming you have a similar getInstance method in ItemsService
            List<Items> items = itemsService.getItemsForInventory(inventory.getId()); // Assuming you have a getItemsForInventory method in ItemsService
            if (!items.isEmpty()) {
                error.setText("The inventory contains items and cannot be deleted.");
                error.setStyle("-fx-text-fill: red;");
                return;
            }

            // Delete the inventory

            inventoryService.SupprimerInventory(inventory.getId());

            error.setText("Inventory deleted successfully!");
            error.setStyle("-fx-text-fill: green;");

            // Refresh the ListView immediately after deleting an inventory
            refreshListView();
        } catch (SQLException e) {
            error.setText("Error while deleting inventory: " + e.getMessage());
            error.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }
}
