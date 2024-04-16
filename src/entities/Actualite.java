package Entites;

import java.util.Date;


public class Actualite {
    private int id_actualite;
    private String titre;
    private String contenu;
    private Date dateA;
    private String categorie;
    private String auteur;
    private String imagepath;

    public Actualite(String titre, String contenu, Date dateA, String categorie, String auteur) {
    }

    public Actualite(int idActualite, String titre, String contenu, Date dateA, String categorie, String auteur) {
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public int getId() {
        return id_actualite;
    }

    public void setId(int id) {
        this.id_actualite = id_actualite;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
    public Date getDateA() {
        return dateA;
    }

    public void setDateA(Date dateA) {
        this.dateA = dateA;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }
    public Actualite() {

    }
    public Actualite(int id_actualite, String titre, String contenu, Date dateA, String categorie, String auteur,String imagepath) {
        this.id_actualite = id_actualite;
        this.titre = titre;
        this.contenu = contenu;
        this.dateA = dateA;
        this.categorie = categorie;
        this.auteur = auteur;
        this.imagepath = imagepath;
    }
    public Actualite( String titre, String contenu, Date dateA, String categorie, String auteur, String imagepath) {

        this.titre = titre;
        this.contenu = contenu;
        this.dateA = dateA;
        this.categorie = categorie;
        this.auteur = auteur;
        this.imagepath = imagepath;
    }

    @Override
    public String toString() {
        return "Actualite{" +
                "id_actualite=" + id_actualite +
                ", titre='" + titre + '\'' +
                ", contenu='" + contenu + '\'' +
                ", dateA=" + dateA +
                ", categorie='" + categorie + '\'' +
                ", auteur='" + auteur + '\'' +
                ", imagepath='" + imagepath + '\'' +
                '}';
    }


}
