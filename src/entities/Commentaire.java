package Entites;

import java.time.LocalDate;
import java.util.Date;

public class Commentaire {
    private int id_com;
    private int id_actualite;
    private int id_user;
    private String contenuC;
    private Date dateC;

    public Commentaire() {

    }
    public Commentaire(int id_com, int id_actualite, int id_user, String contenuC, Date dateC) {
        this.id_com = id_com;
        this.id_actualite = id_actualite;
        this.id_user = id_user;
        this.contenuC = contenuC;
        this.dateC = dateC;
    }
    public Commentaire( int id_actualite, int id_user, String contenuC, Date dateC) {

        this.id_actualite = id_actualite;
        this.id_user = id_user;
        this.contenuC = contenuC;
        this.dateC = dateC;
    }

    public Commentaire(String contenuC, Date dateC) {
        this.contenuC = contenuC;
        this.dateC = dateC;
    }

    public Commentaire(int idUser, String contenuC, String text, int idActualite) {
    }

    public Commentaire(int id, int i, String text, LocalDate now) {
    }

    public Commentaire(int idCom, String contenuC) {
    }

    public Commentaire(String contenuC, LocalDate dateC) {
    }

    public int getId_com() {
        return id_com;
    }

    public void setId_com(int id_com) {
        this.id_com = id_com;
    }

    public int getId_actualite() {
        return id_actualite;
    }

    public void setId_actualite(int id_actualite) {
        this.id_actualite = id_actualite;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getContenuC() {
        return contenuC;
    }

    public void setContenuC(String contenuC) {
        this.contenuC = contenuC;
    }

    public Date getDateC() {
        return dateC;
    }

    public void setDateC(Date dateC) {
        this.dateC = dateC;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "id_com=" + id_com +
                ", id_actualite=" + id_actualite +
                ", id_user=" + id_user +
                ", contenuC='" + contenuC + '\'' +
                ", dateC=" + dateC +
                '}';
    }
}