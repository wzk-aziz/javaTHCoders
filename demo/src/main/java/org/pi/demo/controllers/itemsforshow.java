package org.pi.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.pi.demo.entities.Items;
import org.pi.demo.services.ItemsService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class itemsforshow {

    @FXML
    private VBox chosenItemsCard;

    @FXML
    private Button exchangebtn;

    @FXML
    private GridPane grid;

    @FXML
    private ImageView itemimg;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField search_bar;

    @FXML
    private Button searchbtn;


    @FXML
    public void initialize() throws SQLException {
        ItemsService itemsService = ItemsService.getInstance();
        List<Items> itemsList = itemsService.AfficherItems();

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < itemsList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/org/pi/demo/allitems.fxml"));

                AnchorPane pane = fxmlLoader.load();

                Allitems itemController = fxmlLoader.getController();
                itemController.setData(itemsList.get(i));

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(pane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void backtoinv(ActionEvent event) throws IOException {
        Parent AjouterInventaireParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/pi/demo/Menu.fxml")));
        Scene AjouterInventaireScene = new Scene(AjouterInventaireParent);

        // This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(AjouterInventaireScene);
        window.show();
    }

    @FXML
    void searchbtn(ActionEvent event) {

    }

}
