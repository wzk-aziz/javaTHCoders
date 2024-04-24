package com.projet.azizprojet.Controllers;

import com.projet.azizprojet.HelloApplication;
import com.projet.azizprojet.ImServices.AnnoncesServicesImp;
import com.projet.azizprojet.entities.Annonce;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class DisplayAnnonces {

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;
    @FXML
    private ScrollPane scroll1;


    @FXML
    private VBox root;

    @FXML
    private TextField searchField;

    private AnnoncesServicesImp annoncesService = new AnnoncesServicesImp();

    public void initialize() {
        loadAnnonces();

        // Add a listener to the textProperty of the searchField
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                filterAnnouncements(newValue);
            } else {
                // If the searchField is empty, show all announcements
                for (Node node : grid.getChildren()) {
                    node.setVisible(true);
                }
            }
        });
    }

    private void loadAnnonces() {
        List<Annonce> annoncesList = annoncesService.afficher();
        int column = 0;
        int row = 0;
        try {
            for (Annonce annonce : annoncesList) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/projet/azizprojet/annonces20.fxml"));
                Parent root = fxmlLoader.load();
                Annonces20 annonceController = fxmlLoader.getController();
                annonceController.setAnnonceDetails(annonce);

                root.getProperties().put("controller", annonceController);

                grid.add(fxmlLoader.getRoot(), column++, row);
                if (column == 3) {
                    column = 0;
                    row++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void comment(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Comment.fxml"));
        Scene scene2 = new Scene(fxmlLoader.load());
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(scene2);
        app_stage.show();
    }


    @FXML
    void Annonces(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Annonce.fxml"));
            Parent root = loader.load();
            AnnonceController annonceController = loader.getController();

            // You may need to pass any necessary data to the controller here

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Selects a grid in the display by its row and column indices.
     *
     * @param rowIndex    The index of the row.
     * @param columnIndex The index of the column.
     */
    private void selectGrid(int rowIndex, int columnIndex) {
        for (Node node : grid.getChildren()) {
            if (GridPane.getRowIndex(node) == rowIndex && GridPane.getColumnIndex(node) == columnIndex) {
                // Set the style or perform any action to indicate selection
                node.setStyle("-fx-background-color: lightblue;");
            }
        }
    }


    private EventHandler<ActionEvent> buttonEventHandler() {
        return event -> {
            Node node = (Node) event.getTarget();
            int row = GridPane.getRowIndex(node);
            int column = GridPane.getColumnIndex(node);
            System.out.println("Button clicked at row: " + row + ", column: " + column);
            // Add your logic here to handle the button click event
        };
    }
    @FXML
    void returnhomepage(ActionEvent event) throws IOException {
        Parent menuParent = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Scene menuScene = new Scene(menuParent);

        // Get the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to Menu.fxml
        window.setScene(menuScene);
        window.show();
    }

    @FXML
    void search(ActionEvent event) {
        System.out.println("Search button clicked");
        String keyword = searchField.getText().trim();
        if (!keyword.isEmpty()) {
            filterAnnouncements(keyword);
        }
    }

    private void filterAnnouncements(String keyword) {
        for (Node node : grid.getChildren()) {
            if (node instanceof Pane) {
                Pane pane = (Pane) node;
                Annonces20 annonceControllers = (Annonces20) pane.getProperties().get("controller");
                Annonce annonce = annonceControllers.getAnnonceDetails();
                if (annonce != null) {
                    String titre = annonce.getTitre().toLowerCase();
                    String description = annonce.getDescription().toLowerCase();
                    if (titre.contains(keyword.toLowerCase()) || description.contains(keyword.toLowerCase())) {
                        pane.setVisible(true);
                    } else {
                        pane.setVisible(false);
                    }
                } else {
                    // Handle null Annonce object, e.g., by making the pane visible
                    pane.setVisible(true);
                }
            }
        }
    }

    // Remaining methods...
    }








