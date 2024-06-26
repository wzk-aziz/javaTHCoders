package org.pi.demo.controllers;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import org.pi.demo.Interfaces.MyListener;
import org.pi.demo.entities.Items;
import org.pi.demo.services.ItemsService;
import org.pi.demo.services.SMSService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.IOException;

import java.sql.SQLException;

import java.util.List;
import java.util.stream.Collectors;

public class itemsforshow implements MyListener {
    @FXML
    private ImageView qrCodeImage;

    @FXML
    private VBox chosenItemsCard;

    @FXML
    private Button exchangebtn;

    @FXML
    private GridPane grid;

    @FXML
    private TextField item_condition;

    @FXML
    private Label item_condition_label;

    @FXML
    private ImageView item_image;

    @FXML
    private TextField item_name;

    @FXML
    private Label item_name_label;

    @FXML
    private TextField item_quantity;

    @FXML
    private Label item_quantity_label;

    @FXML
    private TextField item_ref;

    @FXML
    private Label item_ref_label;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField search_bar;

    @FXML
    private Button searchbtn;

    @FXML
    private CheckBox newItemsCheckBox;

    @FXML
    private CheckBox refurbishedItemsCheckBox;

    @FXML
    private CheckBox usedItemsCheckBox;

    private MyListener myListener;

    @FXML
    public void initialize() throws SQLException {
        // Initialize UI components and listeners
        myListener = this;
        // Load and display items
        ItemsService itemsService = ItemsService.getInstance();
        List<Items> itemsList = itemsService.AfficherItems();
        // Add listener to each checkbox
        newItemsCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!newValue) {
                    showAllItems();
                } else {
                    filterItemsByCondition("new");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        refurbishedItemsCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!newValue) {
                    showAllItems();
                } else {
                    filterItemsByCondition("refurbished");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        usedItemsCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!newValue) {
                    showAllItems();
                } else {
                    filterItemsByCondition("used");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Load and display items in the grid
        loadAndDisplayItems();
    }



    @FXML
    void searchbtn(ActionEvent event) throws SQLException {
        String searchText = search_bar.getText().toLowerCase();
        System.out.println("Search term: " + searchText); // Print the search term

        ItemsService itemsService = ItemsService.getInstance();
        List<Items> allItems = itemsService.AfficherItems();
        List<Items> filteredItems = allItems.stream()
                .filter(item -> item.getName().toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        System.out.println("Number of filtered items: " + filteredItems.size()); // Print the size of the filtered items list

        grid.getChildren().clear();

        if (filteredItems.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Items Found");
            alert.setHeaderText(null);
            alert.setContentText("No items match the search term: " + searchText);

            // Add an event handler to the OK button in the alert box
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Clear the search field
                    search_bar.clear();

                    // Display all items again
                    int column = 0;
                    int row = 1;
                    for (Items item : allItems) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/org/pi/demo/allitems.fxml"));

                        try {
                            AnchorPane pane;
                            pane = fxmlLoader.load();

                            Allitems itemController = fxmlLoader.getController();
                            itemController.setData(item, myListener);

                            if (column == 2) {
                                column = 0;
                                row++;
                            }

                            grid.add(pane, column++, row);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            int column = 0;
            int row = 1;
            for (Items item : filteredItems) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/org/pi/demo/allitems.fxml"));

                try {
                    AnchorPane pane = fxmlLoader.load();

                    Allitems itemController = fxmlLoader.getController();
                    itemController.setData(item, myListener);

                    if (column == 2) {
                        column = 0;
                        row++;
                    }

                    grid.add(pane, column++, row);
                    //set grid width
                    grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                    grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    grid.setMaxWidth(Region.USE_PREF_SIZE);
                    //set grid height
                    grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    grid.setMaxHeight(Region.USE_PREF_SIZE);
                    //set grid alignment

                grid.setMargin(pane, new javafx.geometry.Insets(10,10,10,10));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    @FXML
//    void echange_btn(ActionEvent event) throws  IOException {
////        Parent AjouterInventaireParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/pi/demo/Menu.fxml")));
////        Scene AjouterInventaireScene = new Scene(AjouterInventaireParent);
////
////        // This line gets the Stage information
////        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
////
////        window.setScene(AjouterInventaireScene);
////        window.show();
//    }

 @FXML
void echange_btn(ActionEvent event) throws IOException, SQLException {
    // Retrieve the current quantity of the item
    int currentQuantity = Integer.parseInt(item_quantity.getText());

    // Retrieve the quantity set in the quantity field
    // Assuming you have a TextField named quantityField
    int quantityToSubtract = Integer.parseInt(item_quantity.getText());

    // Subtract the quantity set in the quantity field from the current quantity of the item
    int newQuantity = currentQuantity - quantityToSubtract;

    // Update the item's quantity with the new quantity
    item_quantity.setText(String.valueOf(newQuantity));

    // Update the item's quantity in the database
    ItemsService itemsService = ItemsService.getInstance();
    itemsService.updateItemQuantity(item_ref.getText(), newQuantity);

    // Refresh the grid
    loadAndDisplayItems();
}

    @FXML
    void shownewitemsonly(ActionEvent event) throws SQLException {
        filterItemsByCondition("new");
    }

    @FXML
    void showrefurbisheditemsonly(ActionEvent event) throws SQLException {
        filterItemsByCondition("refurbished");
    }

    @FXML
    void showuseditemsonly(ActionEvent event) throws SQLException {
        filterItemsByCondition("used");
    }

    private void loadAndDisplayItems() throws SQLException {
        ItemsService itemsService = ItemsService.getInstance();
        List<Items> allItems = itemsService.AfficherItems();
        displayItems(allItems);
    }

    private void displayItems(List<Items> itemsList) {
        grid.getChildren().clear();

        int column = 0;
        int row = 1;
        for (Items item : itemsList) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/org/pi/demo/allitems.fxml"));

            try {
                AnchorPane pane = fxmlLoader.load();

                Allitems itemController = fxmlLoader.getController();
                itemController.setData(item, myListener);

                if (column == 2) {
                    column = 0;
                    row++;
                }

                grid.add(pane, column++, row);
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);
                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                //set grid alignment

                grid.setMargin(pane, new javafx.geometry.Insets(10,10,10,10));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void filterItemsByCondition(String condition) throws SQLException {
        ItemsService itemsService = ItemsService.getInstance();
        List<Items> filteredItems = itemsService.searchByPartCondition(condition);
        displayItems(filteredItems);
    }

    private void showAllItems() throws SQLException {
        loadAndDisplayItems();
    }

    @Override
    public void OnClickListner(Items item) {
        System.out.println("OnClickListner method called. Item: " + item);
        System.out.println("setData method called. Item: " + item); // Debug print statement
        item_name.setText(item.getName());
        item_condition.setText(item.getPart_condition());
        item_ref.setText(item.getRef());
        item_quantity.setText(String.valueOf(item.getQuantity()));

        System.out.println("Image path: " + item.getPhotos()); // Print the image path

        try {
            Image image = new Image("file:" + item.getPhotos());
            item_image.setImage(image);
            item_image.setFitHeight(100); // Set the maximum height
            item_image.setFitWidth(100); // Set the maximum width
            item_image.setPreserveRatio(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

      String qrCodeText = String.format(
    "Item Details:\n\nName: %s\nCondition: %s\nReference: %s\nQuantity: %d",
    item.getName(),
    item.getPart_condition(),
    item.getRef(),
    item.getQuantity()
);

try {
    generateQRCodeImage(qrCodeText, 200, 200/*,"images/logo2.jpg"*/);
} catch (WriterException | IOException e) {
    System.out.println("Error generating QR code: " + e.getMessage());
}

        if (item.getQuantity() == 0) {
            SMSService smsService = new SMSService();
            String to = "+21628504447";  // replace with the actual recipient's number
            String from = "+17479008244";  // replace with your Twilio number
            String message = "The item " + item.getName() + " is out of stock." +"on the inventory" + item.getInventory().getTitle() + "Please restock it.";
            smsService.sendSMS(to, from, message);
        }
    }

    public void generateQRCodeImage(String text, int width, int height)
        throws WriterException, IOException {
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

    // Make the BufferedImage that are to hold the QRCode
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    image.createGraphics();

    Graphics2D graphics = (Graphics2D) image.getGraphics();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, width, height);
    graphics.setColor(Color.BLACK);

    // Paint and save the image using the ByteMatrix
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            if (bitMatrix.get(i, j)) {
                graphics.fillRect(i, j, 1, 1);
            }
        }
    }

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ImageIO.write(image, "png", outputStream);
    byte[] byteArray = outputStream.toByteArray();

    Image qrImage = new Image(new ByteArrayInputStream(byteArray));
    qrCodeImage.setImage(qrImage);
}

//   public void generateQRCodeImage(String text, int width, int height, String logoPath)
//        throws WriterException, IOException {
//    QRCodeWriter qrCodeWriter = new QRCodeWriter();
//    Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
//    hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//    BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hintMap);
//
//    BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
//    Graphics2D graphics = qrImage.createGraphics();
//
//    URL logoUrl = getClass().getResource("/images/logo2.jpg");
//    BufferedImage logoImage = ImageIO.read(logoUrl);
//    int deltaHeight = height - logoImage.getHeight();
//    int deltaWidth = width - logoImage.getWidth();
//
//    graphics.drawImage(logoImage, Math.round(deltaWidth/2), Math.round(deltaHeight/2), null);
//    graphics.dispose();
//
//    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//    ImageIO.write(qrImage, "png", outputStream);
//    byte[] byteArray = outputStream.toByteArray();
//
//    javafx.scene.image.Image finalImage = SwingFXUtils.toFXImage(qrImage, null);
//    qrCodeImage.setImage(finalImage);
//}
}
