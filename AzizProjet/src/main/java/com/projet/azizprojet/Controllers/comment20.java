package com.projet.azizprojet.Controllers;

import com.projet.azizprojet.entities.Comment;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class comment20 {

    @FXML
    private Label contenuLabel;

    @FXML
    private Label dateLabel;

    private Comment selectedComment;

    public void initialize() {
        // Initialize UI components
    }

    public void setCommentDetails(Comment comment) {
        // Set the details of the selected comment
        selectedComment = comment;
        contenuLabel.setText(comment.getContenu());
        dateLabel.setText(comment.getDatecommnt().toString());
        // Set other details as needed
    }

    public Comment getCommentDetails() {
        return selectedComment;
    }

}
