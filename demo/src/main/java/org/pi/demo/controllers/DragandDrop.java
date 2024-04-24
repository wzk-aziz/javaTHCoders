package org.pi.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.pi.demo.entities.Inventory;
import org.pi.demo.entities.Items;
import org.pi.demo.services.InventoryService;
import org.pi.demo.services.ItemsService;

import java.sql.SQLException;
import java.util.List;

public class DragandDrop {
    @FXML
    private ListView<Items> itemsListView;

    @FXML
    private GridPane inventoryGridPane;

    private ItemsService itemsService;
    private InventoryService inventoryService;

    public DragandDrop() {
        try {
            itemsService = ItemsService.getInstance();
            inventoryService = InventoryService.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        // Populate list views with items and inventories
        populateItemsListView();
        populateInventoryGridPane();

        // Enable drag-and-drop functionality
        enableDragAndDrop();
    }

    private void populateItemsListView() {
        List<Items> allItems = itemsService.AfficherItems();
        itemsListView.getItems().addAll(allItems);

        // Set a custom cell factory
        itemsListView.setCellFactory(param -> new ListCell<Items>() {
            @Override
            protected void updateItem(Items item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox(10); // 10 is the spacing between elements in the HBox
                    Label idlabel = new Label("id: " + item.getId());
                    Label nameLabel = new Label("Name: " + item.getName());
                    Label quantityLabel = new Label("Quantity: " + item.getQuantity());
                    Label inventoryIdLabel = new Label("Inventory ID: " + (item.getInventory_id() != -1 ? item.getInventory_id() : "No Inventory"));                    // Add more labels for other attributes

                    hBox.getChildren().addAll(nameLabel, quantityLabel, inventoryIdLabel, idlabel);
                    setGraphic(hBox);
                }
            }
        });
    }
    private void populateInventoryGridPane() {
        List<Inventory> allInventories = inventoryService.AfficherInventory();
        for (int i = 0; i < allInventories.size(); i++) {
            ListView<Inventory> inventoryListView = new ListView<>();
            inventoryListView.getItems().add(allInventories.get(i));
            inventoryGridPane.add(inventoryListView, i, 1);
        }
    }

    private void enableDragAndDrop() {
        itemsListView.setOnDragDetected(event -> {
            Items selectedItem = itemsListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Dragboard dragboard = itemsListView.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(String.valueOf(selectedItem.getId())); // Store the item's ID
                dragboard.setContent(content);
                event.consume();
            }
        });

        for (int i = 0; i < inventoryGridPane.getChildren().size(); i++) {
            ListView<Inventory> inventoryListView = (ListView<Inventory>) inventoryGridPane.getChildren().get(i);
            inventoryListView.setOnDragOver(event -> {
                if (event.getGestureSource() != inventoryListView && event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            });

            inventoryListView.setOnDragDropped(event -> {
                Dragboard dragboard = event.getDragboard();
                boolean success = false;
                if (dragboard.hasString()) {
                    int itemId = Integer.parseInt(dragboard.getString()); // Retrieve the item's ID
                    Inventory targetInventory = inventoryListView.getSelectionModel().getSelectedItem();
                    if (targetInventory != null) {
                        // Update the item's inventory_id in the database
                        Items itemToUpdate = itemsService.getItemById(itemId); // You need to implement this method
                        itemToUpdate.setInventory_id(targetInventory.getId());
                        itemsService.updateItems(itemToUpdate); // You already have this method

                        // Remove the item from itemsListView and add it to the inventoryListView
                        itemsListView.getItems().remove(itemToUpdate);
                        inventoryListView.getItems().add(itemToUpdate.getInventory());

                        success = true;
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            });
        }
    }
}