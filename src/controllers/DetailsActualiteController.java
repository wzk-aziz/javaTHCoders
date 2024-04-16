package controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import Entites.Actualite;
import Service.ServiceActualite;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DetailsActualiteController {
    @FXML
    private Label idAuteurA;

    @FXML
    private TextArea idContenuA;

    @FXML
    private Label idDateA;

    @FXML
    private Label idTitreA;

    @FXML
    private Label idcategA;

    @FXML
    private ImageView imageViewAct;

    @FXML
    private VBox vBoxAct;
    public void afficherDetailsActualite(Actualite actualite) {
        if (actualite != null) {
            // Récupérer les détails de l'actualité
            String titre = actualite.getTitre();
            String contenu = actualite.getContenu();
            Date dateUtil = actualite.getDateA(); // Récupérer la java.util.Date
            LocalDate date = dateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); // Convertir en LocalDate
            String categorie = actualite.getCategorie();
            String auteur = actualite.getAuteur();
            String imagePath = actualite.getImagepath();

            // Afficher les détails dans les éléments de l'interface utilisateur
            idTitreA.setText(titre);
            idContenuA.setText(contenu);
            idDateA.setText(date.toString());
            idcategA.setText(categorie);
            idAuteurA.setText(auteur);

            // Charger et afficher l'image
            if (imagePath != null) {
                Image image = new Image("file:" + imagePath);
                imageViewAct.setImage(image);
            } else {
                // Effacer l'image s'il n'y en a pas
                imageViewAct.setImage(null);
            }
        } else {
            // Gérer le cas où aucune actualité n'est sélectionnée
            // Par exemple, effacer les valeurs des éléments de l'interface utilisateur
            idTitreA.setText("");
            idContenuA.setText("");
            idDateA.setText("");
            idcategA.setText("");
            idAuteurA.setText("");
            imageViewAct.setImage(null);
        }
    }
    public void initData(Actualite actualite) {
        afficherDetailsActualite(actualite);
    }


}
