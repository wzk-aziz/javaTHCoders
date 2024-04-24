package com.projet.azizprojet.Controllers;

import com.projet.azizprojet.entities.Comment;
import com.projet.azizprojet.entities.CommentDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;

public class comment20 {

    @FXML
    private Label contenuLabel;

    @FXML
    private Label dateLabel;

    private CommentDTO selectedComment;

    public void initialize() {
        // Initialize UI components
    }

    public void setCommentDetails(CommentDTO comment) {
        // Set the details of the selected comment
        selectedComment = comment;
        contenuLabel.setText(comment.getContenu());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(comment.getDatecommnt());
        dateLabel.setText(formattedDate);
        // Set other details as needed
    }

    public CommentDTO getCommentDetails() {
        return selectedComment;
    }

}