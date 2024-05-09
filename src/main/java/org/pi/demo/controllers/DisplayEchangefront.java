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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.pi.demo.entities.Echange;
import org.pi.demo.services.EchangeService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class DisplayEchangefront implements Initializable {

    @FXML
    private HBox cardLayout;

    private Echange selectedEchange;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cardLayout.getChildren().clear();
        System.out.println("Initializing DisplayEchangefront...");

        // Call the method to retrieve all Echange objects from the database
        List<Echange> allEchanges;
        try {
            allEchanges = EchangeService.getInstance().AfficherEchange();
            System.out.println("Retrieved " + allEchanges.size() + " Echange(s) from the database.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        cardLayout.setSpacing(25);
        // Loop through the list and create cards for each Echange
        for (Echange echange : allEchanges) {

            VBox card = createEchangeCard(echange);
            // Add mouse click event to select an item
            card.setOnMouseClicked(event -> {
                selectedEchange = echange;
                System.out.println("Selected Echange: " + selectedEchange);
            });
            cardLayout.getChildren().add(card);
        }
    }

    private VBox createEchangeCard(Echange echange) {
        // Create a new VBox for the card
        VBox card = new VBox();

        System.out.println("Creating card for Echange: " + echange.toString());

        // Set the image for the Echange
        Image image = new Image(echange.getImage());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100); // Set the width of the image view
        imageView.setPreserveRatio(true); // Preserve the aspect ratio

        // Set the labels for the Echange
        Label label1 = new Label("Etat: " + echange.getEtat());
        Label label2 = new Label("Offre: " + echange.getOffre());

        // Add image and labels to the card
        card.getChildren().addAll(imageView, label1, label2);

        return card;
    }

    @FXML
    void ModifierReclamation(ActionEvent event) {
        // Check if an item is selected
        if (selectedEchange != null) {
            // Load ModifierEchange.fxml and pass the selected Echange
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEchange.fxml"));
                Parent modifierEchangeParent = loader.load();
                ModifierEchangeController controller = loader.getController();
                controller.initData(selectedEchange); // Pass the selected Echange to the ModifierEchangeController
                Scene modifierEchangeScene = new Scene(modifierEchangeParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(modifierEchangeScene);
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No item selected.");
        }
    }

    @FXML
    void SupprimerReclamation(ActionEvent event) {
        // Check if an item is selected
        if (selectedEchange != null) {
            try {
                EchangeService.getInstance().SupprimerEchange(selectedEchange.getId());
                // Remove the card from UI
                // Get the parent node (VBox) of the selected card and remove it
                cardLayout.getChildren().removeIf(node -> node instanceof VBox && ((VBox) node).getChildren().contains(selectedEchange));
                selectedEchange = null; // Reset selectedEchange after deletion

                // Clear existing cards
                cardLayout.getChildren().clear();

                // Repopulate with updated list of exchanges
                List<Echange> allEchanges = EchangeService.getInstance().AfficherEchange();
                for (Echange echange : allEchanges) {
                    VBox card = createEchangeCard(echange);
                    // Add mouse click event to select an item
                    card.setOnMouseClicked(e -> {
                        selectedEchange = echange;
                        System.out.println("Selected Echange: " + selectedEchange);
                    });
                    cardLayout.getChildren().add(card);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No item selected.");
        }

    }

    @FXML
    void generatePDF(ActionEvent event) {
        try {
            EchangeService echangeService = EchangeService.getInstance(); // Initialize your EchangeService
            List<Echange> exchanges = echangeService.AfficherEchange(); // Get all exchanges

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
                    float lineSpacing = 20; // Adjust coordinates as needed
                    float yPosition = page.getMediaBox().getHeight() - 20; // Start at the top of the page

                    for (Echange exchange : exchanges) {
                        // Check if there's enough space on the current page for the exchange and its image
                        if (yPosition - lineSpacing * 5 < 0) {
                            // If not, create a new page
                            contentStream.close();
                            page = new PDPage();
                            document.addPage(page);
                            contentStream = new PDPageContentStream(document, page);
                            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                            yPosition = page.getMediaBox().getHeight() - 20; // Reset yPosition to the top of the new page
                        }
                        contentStream.beginText();
                        contentStream.newLineAtOffset(20, yPosition);

                        contentStream.showText("Exchange ID: " + exchange.getId());
                        contentStream.newLineAtOffset(0, -lineSpacing);
                        contentStream.showText("State: " + exchange.getEtat());
                        contentStream.newLineAtOffset(0, -lineSpacing);
                        contentStream.showText("Offer: " + exchange.getOffre());
                        contentStream.newLineAtOffset(0, -lineSpacing);
                        contentStream.newLineAtOffset(0, -lineSpacing);
                        contentStream.newLineAtOffset(0, -lineSpacing);
                        contentStream.newLineAtOffset(0, -lineSpacing);
                        contentStream.newLineAtOffset(0, -lineSpacing);// Add extra spacing between exchanges
                        contentStream.endText();

                        // Draw the image for the current exchange
                        PDImageXObject pdImage = PDImageXObject.createFromFile(exchange.getImage(), document);
                        float imageYPosition = yPosition - lineSpacing * 15; // Adjust the position as needed
                        contentStream.drawImage(pdImage, 20, imageYPosition, pdImage.getWidth(), pdImage.getHeight());

                        // Move to the next line
                        yPosition -= lineSpacing * 20; // Adjust the line spacing as needed
                    }

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
    void generateExcel(ActionEvent event) throws SQLException, IOException {
        EchangeService echangeService = EchangeService.getInstance(); // Initialize your EchangeService

        List<Echange> exchanges = echangeService.AfficherEchange(); // Get all exchanges

        // Create a file chooser dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");
        fileChooser.setInitialFileName("EchangeData.xlsx");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx"));

        // Show save file dialog
        File selectedFile = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            // Create a new workbook
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Echange Data");

                // Create header row
                Row headerRow = sheet.createRow(0);
                String[] headers = {"Exchange ID", "State", "Offer", "Image"};
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                }

                // Populate data rows
                int rowNum = 1;
                for (Echange exchange : exchanges) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(exchange.getId());
                    row.createCell(1).setCellValue(exchange.getEtat());
                    row.createCell(2).setCellValue(exchange.getOffre());
                    row.createCell(3).setCellValue(exchange.getImage());
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
}
