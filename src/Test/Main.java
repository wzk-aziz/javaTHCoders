package Test;

import Entites.Commentaire;
import Service.ServiceCommentaire;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {

        ServiceCommentaire ser = new ServiceCommentaire();

        // Adding a commentaire
        try {
            Commentaire commentaireTest = new Commentaire(12, 1, "contenuC1", Date.valueOf("2003-01-19"));

            ser.ajouter(commentaireTest);
            System.out.println("Commentaire ajouté avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du commentaire : " + e.getMessage());
        }

        // Displaying comments after addition
        try {
            List<Commentaire> commentaires = ser.readAll();
            System.out.println("Affichage après l'ajout du commentaire :");
            commentaires.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e);
        }

       /// Deleting a commentaire
        try {
            Commentaire commentaireToDelete = ser.findById(9);
            ser.delete(commentaireToDelete);
            System.out.println("Commentaire supprimée avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du commentaire : " + e.getMessage());
        }

        // Displaying commentaires after deletion
        try {
            List<Commentaire> commentaires = ser.readAll();
            System.out.println("Affichage après la suppression du commentaire :");
            commentaires.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e);
        }

        // Updating
        Commentaire commentaireToUpdate = new Commentaire(5, 12, 1, "contenuC3", Date.valueOf("2021-02-26"));
        ser.update(commentaireToUpdate);

        // Displaying after the update
        try {
            List<Commentaire> commentaires = ser.readAll();
            System.out.println("Affichage après la modification du commentaire :");
            commentaires.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}

/*package Test;

import Entites.Parking;
import Service.ServiceParking;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        ServiceParking parkingService = new ServiceParking();

        // Adding a parking
       try {
            Parking parkingTest = new Parking("Test Parking", "Test Address", 50, "Open");
            parkingService.ajouter(parkingTest);
            System.out.println("Parking ajouté avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du parking : " + e.getMessage());
        }

        // Displaying parkings after addition
        try {
            List<Parking> parkings = parkingService.readAll();
            System.out.println("Affichage après l'ajout du parking :");
            parkings.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e);
        }

        // Deleting a parking
        try {
            Parking parkingToDelete = parkingService.findById(28); // Assuming there is a parking with id = 1
            parkingService.delete(parkingToDelete);
            System.out.println("Parking supprimé avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du parking : " + e.getMessage());
        }

        // Displaying parkings after deletion
        try {
            List<Parking> parkings = parkingService.readAll();
            System.out.println("Affichage après la suppression du parking :");
            parkings.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e);
        }

        // Updating a parking
        Parking parkingToUpdate = new Parking(2, "Updated Parking", "Updated Address", 75, "Closed");
        parkingService.update(parkingToUpdate);

        // Displaying parkings after the update
        try {
            List<Parking> parkings = parkingService.readAll();
            System.out.println("Affichage après la modification du parking :");
            parkings.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}*/
