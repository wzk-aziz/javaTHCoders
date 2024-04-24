package org.pi.demo.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.pi.demo.entities.Inventory;
import org.pi.demo.entities.Items;
import org.pi.demo.services.InventoryService;
import org.pi.demo.services.ItemsService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class DisplayItemsfront {

    @FXML
    private ComboBox<String> conditionItem;

    @FXML
    private TextField descItem;

    @FXML
    private ImageView imageItem;

    @FXML
    private ComboBox<String> invenotryitem;

    @FXML
    private Label quantity_number;

//    @FXML
//    private TableColumn<Items,String> items_col_condition;
//
//    @FXML
//    private TableColumn<Items,String> items_col_desc;
//    @FXML
//    private TableColumn<Items, String> items_col_invname;
//
//    @FXML
//    private TableColumn<Items,String> items_col_name;
//
//    @FXML
//    private TableColumn<Items,Integer> items_col_quantity;
//
//    @FXML
//    private TableColumn<Items,String> items_col_ref;

//    @FXML
//    private TableView<Items> items_tableview;

    @FXML
    private TextField nomItem;

//    @FXML
//    private ComboBox<Integer> quantityitem;

    @FXML
    private TextField refItem;
    @FXML
    private ListView<Items> list_view_items;

    private String imagePath = null; // Variable to store the image path
//table view_code

//    @FXML
//    void initialize() {
//        try {
//            ItemsService itemsService = ItemsService.getInstance();
//            List<Items> itemsList = itemsService.AfficherItems();
//            ObservableList<Items> items = FXCollections.observableArrayList(itemsList);
//
//            items_tableview.setItems(items);
//            items_col_name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
//            items_col_desc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
//            items_col_ref.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRef()));
//            items_col_condition.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPart_condition()));
//            items_col_quantity.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
//            items_col_invname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInventory().getTitle()));
//            items_tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//                if (newSelection != null) {
//                    imageItem.setImage(new Image("file:" + newSelection.getPhotos()));
//                }
//            });
//            // Populate conditionItem ComboBox
//            conditionItem.getItems().addAll("New", "Used", "Refurbished");
//
//            // Populate the quantity combobox with numbers from 1 to 20
//            for (int i = 1; i <= 20; i++) {
//                quantityitem.getItems().add(i);
//            }
//            // Fetch the list of inventories from the database
//            try {
//                InventoryService inventoryService = InventoryService.getInstance();
//                List<Inventory> inventories = inventoryService.AfficherInventory();
//                for (Inventory inventory : inventories) {
//                    invenotryitem.getItems().add(inventory.getTitle());
//                }
//
//                // Add a listener to the selectedItemProperty of the TableView
//                items_tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//                    if (newSelection != null) {
//                        // Populate the fields with the data of the new selected item
//                        nomItem.setText(newSelection.getName());
//                        descItem.setText(newSelection.getDescription());
//                        refItem.setText(newSelection.getRef());
//                        conditionItem.setValue(newSelection.getPart_condition());
//                        quantityitem.setValue(newSelection.getQuantity());
//                        invenotryitem.setValue(newSelection.getInventory().getTitle());
//                        imageItem.setImage(new Image("file:" + newSelection.getPhotos()));
//                    }
//                });
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//        @FXML
//    void SupprimerItem(ActionEvent event) {
//        // Get the selected item
//        Items selectedItem = items_tableview.getSelectionModel().getSelectedItem();
//
//        // Check if an item is selected
//        if (selectedItem != null) {
//            // Remove the selected item from the TableView
//            items_tableview.getItems().remove(selectedItem);
//
//            try {
//                // Remove the selected item from the database
//                ItemsService.getInstance().SupprimerItems(selectedItem.getId());
//
//                // Show an alert indicating the item has been deleted
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Item Deleted");
//                alert.setHeaderText(null);
//                alert.setContentText("The item has been successfully deleted.");
//                alert.showAndWait();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        } else {
//            // Show an alert indicating no item was selected
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("No Selection");
//            alert.setHeaderText(null);
//            alert.setContentText("Please select an item to delete.");
//            alert.showAndWait();
//        }
//    }

//    @FXML
//void ModifierItem(ActionEvent event) throws SQLException {
//    // Get the selected item
//    Items selectedItem = items_tableview.getSelectionModel().getSelectedItem();
//
//    // Check if an item is selected
//    if (selectedItem != null) {
//        // Validate the fields
//        if (nomItem.getText().isEmpty() || descItem.getText().isEmpty() || refItem.getText().isEmpty() ||
//                conditionItem.getSelectionModel().isEmpty() || quantityitem.getSelectionModel().isEmpty() ||
//                invenotryitem.getSelectionModel().isEmpty() || imagePath == null) {
//            // Display an error message
//            System.out.println("All fields must be filled out");
//            return;
//        }
//
//        // Get the data from the fields and update the selected item with this data
//        selectedItem.setName(nomItem.getText());
//        selectedItem.setDescription(descItem.getText());
//        selectedItem.setRef(refItem.getText());
//        selectedItem.setPart_condition(conditionItem.getValue());
//        selectedItem.setQuantity(quantityitem.getValue());
//        selectedItem.setPhotos(imagePath);
//        //  in InventoryService to get an Inventory by its title
//        selectedItem.setInventory(InventoryService.getInstance().searchByTitle(invenotryitem.getValue()).get(0));
//
//        // Update the item in the database
//        ItemsService.getInstance().updateItems(selectedItem);
//
//        // Refresh the table
//        items_tableview.refresh();
//
//        // Reselect the item
//        items_tableview.getSelectionModel().select(selectedItem);
//
//        // Clear the fields
//        nomItem.clear();
//        descItem.clear();
//        refItem.clear();
//        conditionItem.setValue(null);
//        quantityitem.setValue(null);
//        invenotryitem.setValue(null);
//        imageItem.setImage(null);
//        imagePath = null;
//    }
//}

    //list view code :
    @FXML
    void initialize() {
        try {
            ItemsService itemsService = ItemsService.getInstance();
            List<Items> itemsList = itemsService.AfficherItems();
            ObservableList<Items> items = FXCollections.observableArrayList(itemsList);

            // Set the items to the ListView
            list_view_items.setItems(items);

            // Create a custom cell factory for the ListView to display the items
            list_view_items.setCellFactory(param -> new ListCell<Items>() {
                @Override
                protected void updateItem(Items item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !empty) {
                        setText(item.getName() + " - " + item.getDescription() + " - " + item.getRef() + " - " + item.getPart_condition() + " - " + item.getQuantity() + " - " + item.getInventory().getTitle());
                    } else {
                        setText(null);
                    }
                }
            });

            // Add a listener to the selectedItemProperty of the ListView
            list_view_items.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    // Populate the fields with the data of the new selected item
                    nomItem.setText(newSelection.getName());
                    descItem.setText(newSelection.getDescription());
                    refItem.setText(newSelection.getRef());
                    conditionItem.setValue(newSelection.getPart_condition());
                    //label
                    quantity_number.setText(String.valueOf(newSelection.getQuantity()));
                    invenotryitem.setValue(newSelection.getInventory().getTitle());
                    imageItem.setImage(new Image("file:" + newSelection.getPhotos()));

                }
            });

            // Populate conditionItem ComboBox
            conditionItem.getItems().addAll("New", "Used", "Refurbished");


            // Fetch the list of inventories from the database
            InventoryService inventoryService = InventoryService.getInstance();
            List<Inventory> inventories = inventoryService.AfficherInventory();
            for (Inventory inventory : inventories) {
                invenotryitem.getItems().add(inventory.getTitle());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void SupprimerItem(ActionEvent event) {
        // Get the selected item
        Items selectedItem = list_view_items.getSelectionModel().getSelectedItem();

        // Check if an item is selected
        if (selectedItem != null) {
            // Remove the selected item from the ListView
            list_view_items.getItems().remove(selectedItem);

            try {
                // Remove the selected item from the database
                ItemsService.getInstance().SupprimerItems(selectedItem.getId());

                // Show an alert indicating the item has been deleted
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Item Deleted");
                alert.setHeaderText(null);
                alert.setContentText("The item has been successfully deleted.");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Show an alert indicating no item was selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select an item to delete.");
            alert.showAndWait();
        }
    }

  @FXML
void ModifierItem(ActionEvent event) throws SQLException {
    // Get the selected item
    Items selectedItem = list_view_items.getSelectionModel().getSelectedItem();

    // Check if an item is selected
    if (selectedItem != null) {
        // Validate the fields
        if (nomItem.getText().isEmpty()) {
            System.out.println("Name field must be filled out");
            return;
        }
        if (descItem.getText().isEmpty()) {
            System.out.println("Description field must be filled out");
            return;
        }
        if (refItem.getText().isEmpty()) {
            System.out.println("Reference field must be filled out");
            return;
        }
        if (conditionItem.getSelectionModel().isEmpty()) {
            System.out.println("Condition must be selected");
            return;
        }
        if (quantity_number.getText().isEmpty() || !quantity_number.getText().matches("\\d+")) {
            System.out.println("Quantity must be a positive integer");
            return;
        }
        if (invenotryitem.getSelectionModel().isEmpty()) {
            System.out.println("Inventory item must be selected");
            return;
        }

        // Get the data from the fields and update the selected item with this data
        selectedItem.setName(nomItem.getText());
        selectedItem.setDescription(descItem.getText());
        selectedItem.setRef(refItem.getText());
        selectedItem.setPart_condition(conditionItem.getValue());
        selectedItem.setQuantity(Integer.parseInt(quantity_number.getText()));
        // Only update the photo if a new image has been selected
        if (imagePath != null) {
            selectedItem.setPhotos(imagePath);
        }
        //  in InventoryService to get an Inventory by its title
        selectedItem.setInventory(InventoryService.getInstance().searchByTitle(invenotryitem.getValue()).get(0));

        // Update the item in the database
        ItemsService.getInstance().updateItems(selectedItem);

        // Refresh the ListView
        list_view_items.refresh();

        // Reselect the item
        list_view_items.getSelectionModel().select(selectedItem);

        // Clear the fields
        nomItem.clear();
        descItem.clear();
        refItem.clear();
        conditionItem.setValue(null);
        invenotryitem.setValue(null);
        // Only clear the image if a new image has been selected
        if (imagePath != null) {
            imageItem.setImage(null);
            imagePath = null;
        }
        //quantity number
        quantity_number.setText("0");
    }
}
//endds here

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
    void returnhomepage(ActionEvent event) throws IOException {
        Parent AjouterInventaireParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/pi/demo/Menu.fxml")));
        Scene AjouterInventaireScene = new Scene(AjouterInventaireParent);

        // This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(AjouterInventaireScene);
        window.show();
    }

//    @FXML
//    void displayStatistics(ActionEvent event) {
//        ObservableList<Items> items = items_tableview.getItems();
//
//        int totalItems = items.size();
//        double averageQuantity = items.stream().mapToInt(Items::getQuantity).average().orElse(0.0);
//        long newItems = items.stream().filter(item -> item.getPart_condition().equals("New")).count();
//        long usedItems = items.stream().filter(item -> item.getPart_condition().equals("Used")).count();
//        long refurbishedItems = items.stream().filter(item -> item.getPart_condition().equals("Refurbished")).count();
//
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Items Statistics");
//        alert.setHeaderText(null);
//        alert.setContentText("Total items: " + totalItems +
//                "\nAverage quantity: " + averageQuantity +
//                "\nNew items: " + newItems +
//                "\nUsed items: " + usedItems +
//                "\nRefurbished items: " + refurbishedItems);
//        alert.showAndWait();
//    }


    @FXML
void increase_quantity_btn(ActionEvent event) {
    // Get the current quantity
    int currentQuantity = Integer.parseInt(quantity_number.getText());

    // Increase the quantity by 1
    currentQuantity++;

    // Update the quantity_number label
    quantity_number.setText(String.valueOf(currentQuantity));
}

@FXML
void decrease_quantity_btn(ActionEvent event) {
    // Get the current quantity
    int currentQuantity = Integer.parseInt(quantity_number.getText());

    // Decrease the quantity by 1, but not less than 0
    if (currentQuantity > 1) {
        currentQuantity--;
    }

    // Update the quantity_number label
    quantity_number.setText(String.valueOf(currentQuantity));
}


}
