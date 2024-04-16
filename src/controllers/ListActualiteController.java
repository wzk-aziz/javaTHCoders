package controllers;

import Entites.Actualite;
import Service.ServiceActualite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.image.Image;

public class ListActualiteController {
    @FXML
    private ComboBox<String> combofiltrer;
    @FXML
    private FlowPane flowPaneA;
    private int id_actualite_selected;
    @FXML
    private TextField recherche;
    private List<Node>originalNodes=new ArrayList<>();
    private final ServiceActualite ser = new ServiceActualite();

    public void initialize() throws SQLException {

        initFlowpane();
        /////////////////////////////////RECHERCHE/////////////////////////////////
        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filter();
        });
        recherche.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.BACK_SPACE && recherche.getText().isEmpty()) {
                flowPaneA.getChildren().setAll(originalNodes);
            }
        });


    }
    public void initFlowpane() throws SQLException {
        ServiceActualite ser = new ServiceActualite();
        List<Actualite> actualiteList = ser.readAll();
        ObservableList<Actualite> obers = FXCollections.observableList(actualiteList);
        FilteredList<Actualite> filteredList = new FilteredList<>(obers);

        // Ajouter un écouteur de changement de valeur à ComboBox
        combofiltrer.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(actualite -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Afficher tous les éléments si rien n'est sélectionné
                }
                String selectedType = newValue.toLowerCase();
                return actualite.getCategorie().toLowerCase().equals(selectedType);
            });
            refreshFlowPane(filteredList);
        });
        List<Actualite> ActualiteList = ser.readAll();
        flowPaneA.setHgap(30);
        flowPaneA.setVgap(30);


        // Parcours la liste d'images et crée des VBox pour chaque image
        for (Actualite actualite : ActualiteList) {
            VBox Vbox = createVBox(actualite);
            Vbox.setUserData(actualite); // Set the Club object as user data for the VBox

            // Handle the Details button click
            Button Details = (Button) Vbox.getChildren().stream()
                    .filter(node -> node instanceof Button)
                    .findFirst()
                    .orElse(null);
            if (Details != null) {
                Details.setOnAction(event -> {
                    id_actualite_selected = actualite.getId();
                    try {
                        showDetails();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            flowPaneA.getChildren().add(Vbox);
        }
        originalNodes.addAll(flowPaneA.getChildren());
    }
    private void refreshFlowPane(FilteredList<Actualite> filteredList) {
        flowPaneA.getChildren().clear(); // Supprimer les anciens éléments

        // Parcourir la liste filtrée et créer des VBox pour chaque élément
        for (Actualite actualite : filteredList) {
            VBox Vbox = createVBox(actualite);
            Vbox.setUserData(actualite); // Set the Club object as user data for the VBox

            // Gérer le clic sur le bouton Details
            Button details = (Button) Vbox.getChildren().stream()
                    .filter(node -> node instanceof Button)
                    .findFirst()
                    .orElse(null);
            if (details != null) {
                details.setOnAction(event -> {
                    id_actualite_selected = actualite.getId();
                    try {
                        showDetails();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            flowPaneA.getChildren().add(Vbox);
        }
    }
    private VBox createVBox(Actualite actualite) {
        VBox Vbox = new VBox();
        Vbox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;");

        // Crée l'ImageView pour l'image
        String imageUrl = actualite.getImagepath();
        ImageView imageView = createImageView(imageUrl);
        System.out.println(imageUrl);
        imageView.setStyle("-fx-border-color: black; -fx-border-width: 5px;");

        Separator separator1 = new Separator(Orientation.HORIZONTAL);
        separator1.setPrefWidth(120);
        Separator separator2 = new Separator(Orientation.HORIZONTAL);
        separator2.setPrefWidth(120);
        Separator separator3 = new Separator(Orientation.HORIZONTAL);
        separator3.setPrefWidth(120);

        Label nom_club = new Label( actualite.getCategorie());
        nom_club.setAlignment(Pos.CENTER);

        Label label2 = new Label(actualite.getTitre());

        // Crée le bouton "Détails" pour chaque image
        Button Details = new Button("Details");
        Details.setPrefWidth(120);
       // Details.setStyle("-fx-content-display: LEFT;");


        // Ajoute l'ImageView et le bouton "Détails" à la VBox
        Vbox.getChildren().addAll(nom_club,separator1,imageView,separator2,label2,Details,separator3);

        return Vbox;
    }
    private void showDetails() throws SQLException {
        System.out.println("Showing details for Actualité ID: " + id_actualite_selected);
        if (id_actualite_selected != -1) {
            ServiceActualite ser = new ServiceActualite();
            Actualite actualiteselected = ser.findById(id_actualite_selected);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ActualiteDetails.fxml"));
            try {
                Parent root = loader.load();
                ActualiteDetailsController LCC = loader.getController();
                LCC.initActualite(actualiteselected);
                //LCC.initialize(actualiteselected);
                Stage currentStage = (Stage) flowPaneA.getScene().getWindow();
                currentStage.setScene(new Scene(root));
                System.out.println("Navigating to ListActualite.fxml");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private ImageView createImageView(String imageUrl) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(150);
        imageView.setFitHeight(130);

        try {
            File file = new File(imageUrl);
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return imageView;
    }
    private void filter() {
        String searchText = recherche.getText().toLowerCase();

        List<Node> filteredNodes = flowPaneA.getChildren().stream()
                .filter(node -> {
                    if (node instanceof VBox) {
                        VBox vBox = (VBox) node;
                        Label clubNameLabel = (Label) vBox.getChildren().get(4);
                        String clubName = clubNameLabel.getText().toLowerCase();
                        return clubName.startsWith(searchText);
                    }
                    return false;
                })
                .collect(Collectors.toList());

        flowPaneA.getChildren().clear();
        flowPaneA.getChildren().addAll(filteredNodes);



    }


    public void ChatBot(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChatBot.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) flowPaneA.getScene().getWindow();
        currentStage.setScene(new Scene(root));
    }
}
