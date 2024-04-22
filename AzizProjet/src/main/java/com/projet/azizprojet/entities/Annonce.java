package com.projet.azizprojet.entities;
import java.util.Date;

public class Annonce {
	private int id;
	private String titre;
	private Date datedepub;
	private String description;
	private int liked;

	// Empty constructor
	public Annonce() {
	}

	// Constructor without ID parameter
	public Annonce(String titre, Date datedepub, String description, int liked) {
		this.titre = titre;
		this.datedepub = datedepub;
		this.description = description;
		this.liked = liked;
	}

	// Fully parameterized constructor
	public Annonce(int id, String titre, Date datedepub, String description, int liked) {
		this.id = id;
		this.titre = titre;
		this.datedepub = datedepub;
		this.description = description;
		this.liked = liked;
	}

	// Constructor with only ID
	public Annonce(int id) {
		this.id = id;
	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public java.sql.Date getDatedepub() {
		return (java.sql.Date) datedepub;
	}

	public void setDatedepub(Date datedepub) {
		this.datedepub = datedepub;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
			", titre='" + titre + '\'' +
			", datedepub=" + datedepub +
			", description='" + description + '\'' +
			", liked=" + liked
			;
	}
}
