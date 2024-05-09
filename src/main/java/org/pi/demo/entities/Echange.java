package org.pi.demo.entities;

import java.util.Objects;

public class Echange {
    private int id;
    private String etat;
    private String offre;
    private String image;

    public Echange() {
    }

    public Echange(int id, String etat, String offre, String image) {
        this.id = id;
        this.etat = etat;
        this.offre = offre;
        this.image = image;
    }

    public Echange(String etat, String offre, String image) {
        this.etat = etat;
        this.offre = offre;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getOffre() {
        return offre;
    }

    public void setOffre(String offre) {
        this.offre = offre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Echange{" +
                "id=" + id +
                ", etat='" + etat + '\'' +
                ", offre='" + offre + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Echange echange = (Echange) o;
        return id == echange.id && Objects.equals(etat, echange.etat) && Objects.equals(offre, echange.offre) && Objects.equals(image, echange.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, etat, offre, image);
    }
}
