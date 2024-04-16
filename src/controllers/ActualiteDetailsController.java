package controllers;
import Entites.Actualite;
import Service.ServiceActualite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import Entites.Actualite;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ActualiteDetailsController {
    private Actualite ActualiteToModifier;
    private int ActualiteSelected;
    @FXML
    private TextField idAuteurA;

    @FXML
    private TextField idCategA;

    @FXML
    private TextArea idContenuA;

    @FXML
    private TextField idDateA;

    @FXML
    private TextField idTitreA;

    @FXML
    private ImageView imageViewA;
    public void initActualite(Actualite actualite) {

        ActualiteToModifier = actualite;
       // id_actualite.setText(String.valueOf(actualite.getId()));
        idTitreA.setText(actualite.getTitre());
        idContenuA.setText(actualite.getContenu());
        idCategA.setText(actualite.getCategorie());
        idAuteurA.setText(actualite.getAuteur());
        idDateA.setText(String.valueOf(actualite.getDateA()));


        String imagePath =ActualiteToModifier.getImagepath() ;
        System.out.println(imagePath);

        Image image = new Image(new File(imagePath).toURI().toString());
        imageViewA.setImage(image);
        //imageViewA.setStyle("-fx-border-color: black; -fx-border-width: 5px;");

        ActualiteSelected=actualite.getId();
    }
    @FXML
    void CommenterActualite(ActionEvent event) throws IOException, SQLException {
       /* FXMLLoader loader = new FXMLLoader(getClass().getResource("/Commentaire.fxml"));
        Parent root = loader.load();

        // Passer l'ID de l'actualité sélectionnée au CommentaireController
        CommentaireController commentaireController = loader.getController();
        commentaireController.setActualiteId(ActualiteSelected);

        Stage currentStage = (Stage) imageViewA.getScene().getWindow();
        currentStage.setScene(new Scene(root));*/

       FXMLLoader loader = new FXMLLoader(getClass().getResource("/Commentaire.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) imageViewA.getScene().getWindow();
        currentStage.setScene(new Scene(root));


        /*System.out.println("Showing details for Actualité ID: " + ActualiteSelected);
        if (ActualiteSelected != -1) {
            ServiceActualite ser = new ServiceActualite();
            Actualite actualiteselected = ser.findById(ActualiteSelected);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Commentaire.fxml"));
            try {
                Parent root = loader.load();
                CommentaireController LCC = loader.getController();
                LCC.initialize();
                //LCC.initialize(actualiteselected);
                Stage currentStage = (Stage) imageViewA.getScene().getWindow();
                currentStage.setScene(new Scene(root));
                System.out.println("Navigating to Commentaire.fxml");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }*/
    }


}
