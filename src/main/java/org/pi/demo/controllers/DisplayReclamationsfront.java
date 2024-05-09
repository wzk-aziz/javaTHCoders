package org.pi.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.pi.demo.entities.Reclamation;
import org.pi.demo.services.ReclamationService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class DisplayReclamationsfront implements Initializable {
    @FXML
    private VBox cardLayout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Call the method to retrieve all Reclamation objects from the database
        List<Reclamation> allReclamations;
        try {
            allReclamations = ReclamationService.getInstance().AfficherReclamation();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cardLayout.setSpacing(25);

        // Loop through the list and create cards for each Reclamation
        for (int i = 0; i < allReclamations.size(); i++) {
            Reclamation reclamation = allReclamations.get(i);
            VBox card = createReclamationCard(reclamation, i); // Pass the index i to createReclamationCard
            cardLayout.getChildren().add(card);
        }
    }

    // Method to create a card for each Reclamation
    private VBox createReclamationCard(Reclamation reclamation, int index) { // Accept index as a parameter
        // Create a new VBox for the card
        VBox card = new VBox();

        // Set the labels for the Reclamation
        Label label1 = new Label("Nom: " + reclamation.getNom());
        Label label2 = new Label("Sujet: " + reclamation.getSujet());
        Label label3 = new Label("Details: " + reclamation.getDetails());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = reclamation.getDate().format(formatter);
        Label label4 = new Label("Date: " + formattedDate);

        // Enable text wrapping for labels
        label1.setWrapText(true);
        label2.setWrapText(true);
        label3.setWrapText(true);
        label4.setWrapText(true);

        // Create a delete button for the card
        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #f61010;");
        deleteButton.setOnAction(event -> {
            try {
                deleteAction(event, index);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }); // Pass the index to deleteAction

        // Add the labels and delete button to the card
        card.getChildren().addAll(label1, label2, label3, label4, deleteButton);

        return card;
    }

    @FXML
    void generateExcel(ActionEvent event) throws SQLException, IOException {
        ReclamationService reclamationService = ReclamationService.getInstance(); // Initialize your EchangeService

        List<Reclamation> reclamations = reclamationService.AfficherReclamation(); // Get all exchanges

        // Create a file chooser dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");
        fileChooser.setInitialFileName("ReclamationData.xlsx");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx"));

        // Show save file dialog
        File selectedFile = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            // Create a new workbook
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Reclamation Data");

                // Create header row
                Row headerRow = sheet.createRow(0);
                String[] headers = {"ID", "Nom", "Sujet", "Détails", "Date"};
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                }

                // Populate data rows
                int rowNum = 1;
                for (Reclamation reclamation : reclamations) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(reclamation.getId());
                    row.createCell(1).setCellValue(reclamation.getNom());
                    row.createCell(2).setCellValue(reclamation.getSujet());
                    row.createCell(3).setCellValue(reclamation.getDetails());
                    row.createCell(4).setCellValue(reclamation.getDate().toString());
                }

                // Write the workbook content to the selected file
                try (FileOutputStream outputStream = new FileOutputStream(selectedFile)) {
                    workbook.write(outputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void generatePDF(ActionEvent event) {
        try {
            ReclamationService reclamationService = ReclamationService.getInstance(); // Initialize your EchangeService
            List<Reclamation> reclamations = reclamationService.AfficherReclamation(); // Get all exchanges

            // Create a file chooser dialog
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF File");

            // Set initial directory
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

            // Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);

            // Show save file dialog
            File file = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

            if (file != null) {
                // User chose a file, proceed with PDF generation
                try (PDDocument document = new PDDocument()) {
                    PDPage page = new PDPage();
                    document.addPage(page);

                    PDPageContentStream contentStream = new PDPageContentStream(document, page);
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.beginText();
                    float lineSpacing = 20; // Adjust coordinates as needed
                    float contentHeight = (reclamations.size() + 1) * lineSpacing; // Add 1 for the header

                    // Calculate the starting Y position dynamically
                    float yPosition = page.getMediaBox().getHeight() - contentHeight - 20; // Subtract extra margin
                    contentStream.newLineAtOffset(20, yPosition);// Adjust coordinates as needed
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    for (Reclamation reclamation : reclamations) {

                        contentStream.showText("Nom: " + reclamation.getNom());
                        contentStream.newLineAtOffset(0, -lineSpacing);
                        contentStream.showText("Sujet: " + reclamation.getSujet());
                        contentStream.newLineAtOffset(0, -lineSpacing);
                        contentStream.showText("Détails: " + reclamation.getDetails());
                        contentStream.newLineAtOffset(0, -lineSpacing);
                        contentStream.showText("Date: " + reclamation.getDate().format(formatter));
                        contentStream.newLineAtOffset(0, -lineSpacing);
                        contentStream.newLineAtOffset(0, -lineSpacing);
                        contentStream.newLineAtOffset(0, -lineSpacing);// Add spacing between exchanges
                    }

                    contentStream.endText();
                    contentStream.close();

                    // Save the PDF document to the selected file
                    document.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // User canceled the dialog, do nothing
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void menu(ActionEvent event) throws IOException {
        Parent AjouterReclamationParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Menu.fxml")));
        if (AjouterReclamationParent == null) {
            System.err.println("Error: Menu.fxml not found!");
            return;
        }
        Scene AjouterReclamationScene = new Scene(AjouterReclamationParent);

        // This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(AjouterReclamationScene);
        window.show();
    }

    // Modify deleteAction to accept the index of the card to be deleted
    @FXML
    void deleteAction(ActionEvent event, int index) throws SQLException {
        // Get the list of all Reclamations
        List<Reclamation> allReclamations = ReclamationService.getInstance().AfficherReclamation();

        // Get the selected Reclamation
        Reclamation selectedReclamation = allReclamations.get(index);

        // Delete the selected reclamation from the database
        ReclamationService.getInstance().SupprimerReclamation(selectedReclamation.getNom());

        // Remove the selected card from the card layout
        cardLayout.getChildren().remove(index);
    }

    public void deleteAction(ActionEvent actionEvent) {
    }
}
