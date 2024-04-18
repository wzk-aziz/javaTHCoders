package org.pi.demo.entities;

public class Reservation {
   private int id;

    private String nom,address;
    private int phone;
    private int event_id;
   Event event;


public Reservation()
{
}

  public Reservation(String nom, String address, int phone, int event_id) {
        this.nom = nom;
        this.address = address;
        this.phone = phone;
        this.event_id = event_id;

    }

    public Reservation(int id, String nom, String address, int phone, int event_id) {
        this.id = id;
        this.nom = nom;
        this.address = address;
        this.phone = phone;
        this.event_id = event_id;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", address='" + address + '\'' +
                ", phone=" + phone +
                ", event_id=" + event_id +
                ", event=" + event +
                '}';
    }
}
