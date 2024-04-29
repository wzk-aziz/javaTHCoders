package org.pi.demo.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.pi.demo.entities.Inventory;
import org.pi.demo.entities.Items;
import org.pi.demo.services.InventoryService;
import org.pi.demo.services.ItemsService;

import java.sql.SQLException;
import java.util.List;

public class Dashboard {
    @FXML
    private Label All_Inventories_Count;

    @FXML
    private Label All_Items_Count;

    @FXML
    private Label Percentage_of_exchanged_items;
    @FXML
    private ListView<Items> itemsListview;

    @FXML
    void invanditemback(MouseEvent event) {

    }
 public void initialize() {
    try {
        ItemsService itemsService = ItemsService.getInstance();
        List<Items> items = itemsService.AfficherItems();

        itemsListview.setCellFactory(param -> new ListCell<Items>() {
            @Override
            protected void updateItem(Items item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    VBox vBox = new VBox(); // Use VBox for vertical layout
                    Text name = new Text("Name: " + item.getName());
                    Text description = new Text("Description: " + item.getDescription());
                    Text ref = new Text("Ref: " + item.getRef());
                    Text partCondition = new Text("Part Condition: " + item.getPart_condition());
                    Text quantity = new Text("Quantity: " + item.getQuantity());


                    vBox.getChildren().addAll(name, description, ref, partCondition, quantity);
                    setGraphic(vBox);
                }
            }
        });

        itemsListview.getItems().setAll(items);
    } catch (SQLException e) {
        e.printStackTrace();
    }
    try {
        // Get services
        ItemsService itemsService = ItemsService.getInstance();
        InventoryService inventoryService = InventoryService.getInstance();

        // Get all items and inventories
        List<Items> items = itemsService.AfficherItems();
        List<Inventory> inventories = inventoryService.AfficherInventory();

        // Calculate and set percentage of exchanged items
        // Assuming an item is considered "exchanged" if its quantity is 0
        long exchangedItemsCount = items.stream().filter(item -> item.getQuantity() == 0).count();
        double exchangedItemsPercentage = (double) exchangedItemsCount / items.size() * 100;

        // Create IntegerProperties for gradual number loading
        IntegerProperty inventoryCountProperty = new SimpleIntegerProperty(0);
        IntegerProperty itemCountProperty = new SimpleIntegerProperty(0);
        DoubleProperty exchangedItemsPercentageProperty = new SimpleDoubleProperty(0);

        // Bind the text properties of the labels to the IntegerProperties
        All_Inventories_Count.textProperty().bind(inventoryCountProperty.asString());
        All_Items_Count.textProperty().bind(itemCountProperty.asString());
        Percentage_of_exchanged_items.textProperty().bind(exchangedItemsPercentageProperty.asString("%.2f%%"));

        // Create timelines for gradual number loading
        Timeline inventoryCountTimeline = new Timeline(
            new KeyFrame(Duration.seconds(2),
                new KeyValue(inventoryCountProperty, inventories.size())
            )
        );

        Timeline itemCountTimeline = new Timeline(
            new KeyFrame(Duration.seconds(2),
                new KeyValue(itemCountProperty, items.size())
            )
        );

        Timeline exchangedItemsPercentageTimeline = new Timeline(
            new KeyFrame(Duration.seconds(2),
                new KeyValue(exchangedItemsPercentageProperty, exchangedItemsPercentage)
            )
        );

        // Start the timelines
        inventoryCountTimeline.play();
        itemCountTimeline.play();
        exchangedItemsPercentageTimeline.play();

    } catch (SQLException e) {
        e.printStackTrace();
    }

}
@FXML
public void handleMouseClick(MouseEvent event) {
    Items selectedItem = itemsListview.getSelectionModel().getSelectedItem();
    if (selectedItem != null) {
        // Get the inventory that the selected item belongs to
        Inventory itemInventory = selectedItem.getInventory();

        if (itemInventory != null) {
            // Create an alert window
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Item Inventory Information");
            alert.setHeaderText("Inventory Information for " + selectedItem.getName());
            alert.setContentText("Inventory Title: " + (itemInventory.getTitle() != null ? itemInventory.getTitle() : "N/A") + "\n" +
                                 "Inventory Description: " + (itemInventory.getDescription() != null ? itemInventory.getDescription() : "N/A"));

            // Show the alert window
            alert.showAndWait();
        } else {
            // Handle the case where the item does not have an associated inventory
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Item Information");
            alert.setHeaderText("Information for " + selectedItem.getName());
            alert.setContentText("This item does not have an associated inventory.");

            // Show the alert window
            alert.showAndWait();
        }
    }
}

}
