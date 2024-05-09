package com.projet.azizprojet.entities;

import java.time.LocalDate;
import java.util.Date;

public class CommentDTO {
	private int id;
	private String contenu;
	private Date datecommnt;
	private String annonceTitre;
	private String annonceDescription;
	private int liked;

	// Empty constructor
	public CommentDTO() {
	}

	// Constructor with ID only
	public CommentDTO(int id) {
		this.id = id;
	}

	// Fully parameterized constructor
	public CommentDTO(int id, String contenu, Date datecommnt, String annonceTitre, String annonceDescription, int liked) {
		this.id = id;
		this.contenu = contenu;
		this.datecommnt = datecommnt;
		this.annonceTitre = annonceTitre;
		this.annonceDescription = annonceDescription;
		this.liked = liked;
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

	public String getAnnonceTitre() {
		return annonceTitre;
	}

	public void setAnnonceTitre(String annonceTitre) {
		this.annonceTitre = annonceTitre;
	}

	public String getAnnonceDescription() {
		return annonceDescription;
	}

	public void setAnnonceDescription(String annonceDescription) {
		this.annonceDescription = annonceDescription;
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
		return
			"id=" + id +
			", contenu='" + contenu + '\'' +
			", datecommnt=" + datecommnt +
			", annonceTitre='" + annonceTitre + '\'' +
			", annonceDescription='" + annonceDescription + '\'' +
			", liked=" + liked
		;
	}
}
