package com.projet.azizprojet.entities;

import java.util.Date;

public class Comment {
    private int id;
    private String contenu;
    private Date datecommnt;
    private int annonces_id;
    private int liked;

    // Empty constructor
    public Comment() {
    }

    // Constructor without ID parameter
    public Comment(String contenu, Date datecommnt, int annonces_id, int liked) {
        this.contenu = contenu;
        this.datecommnt = datecommnt;
        this.annonces_id = annonces_id;
        this.liked = liked;
    }

    // Fully parameterized constructor
    public Comment(int id, String contenu, Date datecommnt, int annonces_id, int liked) {
        this.id = id;
        this.contenu = contenu;
        this.datecommnt = datecommnt;
        this.annonces_id = annonces_id;
        this.liked = liked;
    }

    // Constructor with only ID
    public Comment(int id) {
        this.id = id;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public java.sql.Date getDatecommnt() {
        return (java.sql.Date) datecommnt;
    }

    public void setDatecommnt(Date datecommnt) {
        this.datecommnt = datecommnt;
    }

    public int getAnnonces_id() {
        return annonces_id;
    }

    public void setAnnonces_id(int annonces_id) {
        this.annonces_id = annonces_id;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    // toString method
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", contenu='" + contenu + '\'' +
                ", datecommnt=" + datecommnt +
                ", annonces_id=" + annonces_id +
                ", liked=" + liked +
                '}';
    }
}