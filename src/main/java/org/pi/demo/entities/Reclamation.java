package org.pi.demo.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reclamation {
    private int id;
    private String nom;
    private String sujet;
    private String details;
    private LocalDateTime date;

    public Reclamation() {
    }

    public Reclamation(int id, String nom, String sujet, String details, LocalDateTime date) {
        this.id = id;
        this.nom = nom;
        this.sujet = sujet;
        this.details = details;
        this.date = date;
    }

    public Reclamation(String nom, String sujet, String details, LocalDateTime date) {
        this.nom = nom;
        this.sujet = sujet;
        this.details = details;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", sujet='" + sujet + '\'' +
                ", details='" + details + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reclamation that = (Reclamation) o;
        return id == that.id && Objects.equals(nom, that.nom) && Objects.equals(sujet, that.sujet) && Objects.equals(details, that.details) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, sujet, details, date);
    }
}
