package org.pi.demo.entities;


import java.util.Date;

public class Events {
    private int id;
    private String event_name,place,description;
    private String image;
    private int capacity;
    private Date start_date,end_date;
    //new
    private float latitude;
    private float longitude;

    public Events() {
    }

    public Events(String event_name, String place, String description,String image ,int capacity, Date start_date, Date end_date) {
        this.event_name = event_name;
        this.place = place;
        this.description = description;
        this.image = image;
        this.capacity = capacity;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Events(int id, String event_name, String place, String description ,String image, int capacity, Date start_date, Date end_date) {
        this.id = id;
        this.event_name = event_name;
        this.place = place;
        this.description = description;
        this.image = image;
        this.capacity = capacity;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Events(int id, String event_name, String place, String description ,String image, int capacity, Date start_date, Date end_date,float latitude,float longitude) {
        this.id = id;
        this.event_name = event_name;
        this.place = place;
        this.description = description;
        this.image = image;
        this.capacity = capacity;
        this.start_date = start_date;
        this.end_date = end_date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Events(String event_name, String place, String description ,String image, int capacity, Date start_date, Date end_date,float latitude,float longitude) {
        this.event_name = event_name;
        this.place = place;
        this.description = description;
        this.image = image;
        this.capacity = capacity;
        this.start_date = start_date;
        this.end_date = end_date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", event_name='" + event_name + '\'' +
                ", place='" + place + '\'' +
                ", description='" + description + '\'' +
                //", image='" + image + '\'' +
                ", capacity=" + capacity +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                '}';
    }
}
