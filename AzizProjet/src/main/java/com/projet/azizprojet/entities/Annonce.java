package com.projet.azizprojet.entities;
import java.util.Date;

public class Annonce {
	private int id;
	private String titre;
	private Date datedepub;
	private String description;
	private int liked;
	private  double rating;

	// Empty constructor
	public Annonce() {
	}

	// Constructor without ID parameter
	public Annonce(String titre, Date datedepub, String description, int liked,double rating) {
		this.titre = titre;
		this.datedepub = datedepub;
		this.description = description;
		this.liked = liked;
		this.rating=rating;
	}

	// Fully parameterized constructor
	public Annonce(int id, String titre, Date datedepub, String description, int liked,double rating) {
		this.id = id;
		this.titre = titre;
		this.datedepub = datedepub;
		this.description = description;
		this.liked = liked;
		this.rating=rating;
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

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Annonce{" +
				"id=" + id +
				", titre='" + titre + '\'' +
				", datedepub=" + datedepub +
				", description='" + description + '\'' +
				", liked=" + liked +
				", rating=" + rating +
				'}';
	}
}
