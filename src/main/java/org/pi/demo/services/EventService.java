package org.pi.demo.services;

import org.pi.demo.Interfaces.EventInterface;
import org.pi.demo.entities.Event;
import org.pi.demo.utils.MyConnection;

import java.sql.Connection; // Correct import
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventService implements EventInterface {


    private static Connection cnx;
    private static EventService instance;

    private EventService() throws SQLException {
        cnx = (Connection) MyConnection.getInstance().getCnx();

    }

    public static EventService getInstance() throws SQLException {
        if (instance == null) {
            instance = new EventService();
        }
        return instance;
    }

    public void addEvent(Event ev) {
        try {
            String request = "INSERT INTO event(event_name, capacity, start_date, end_date, place, description,image) VALUES (?, ?, ?, ?, ?, ?,?)";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setString(1, ev.getEvent_name());
            pst.setInt(2, ev.getCapacity());
            pst.setDate(3, new java.sql.Date(ev.getStart_date().getTime()));
            pst.setDate(4, new java.sql.Date(ev.getEnd_date().getTime()));
            pst.setString(5, ev.getPlace());
            pst.setString(6, ev.getDescription());
            pst.setString(7, ev.getImage());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void SupprimerEvent(int id) {
        try {
            String request = "DELETE FROM event WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  public static List<Event> AfficherEvent() {
    List<Event> events = new ArrayList<>();
    try {
        String request = "SELECT * FROM event";
        PreparedStatement pst = cnx.prepareStatement(request);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Event event = new Event(rs.getInt("id"), rs.getString("event_name"), rs.getString("place"), rs.getString("description") ,rs.getString("image"), rs.getInt("capacity"), rs.getDate("start_date"), rs.getDate("end_date"));
            events.add(event);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return events;
}

  public boolean updateEvent(Event ev) {
    try {
        String request = "UPDATE event SET event_name = ?, capacity = ?, start_date = ?, end_date = ?, place = ?, description = ? WHERE id = ?";
        PreparedStatement pst = cnx.prepareStatement(request);
        pst.setString(1, ev.getEvent_name());
        pst.setInt(2, ev.getCapacity());
        pst.setDate(3, new java.sql.Date(ev.getStart_date().getTime()));
        pst.setDate(4, new java.sql.Date(ev.getEnd_date().getTime()));
        pst.setString(5, ev.getPlace());
        pst.setString(6, ev.getDescription());
        pst.setInt(7, ev.getId());
        int rowsUpdated = pst.executeUpdate();
        return rowsUpdated > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}


    public List<Event> searchByPlace(String place){
        List<Event> event = new ArrayList<>();
        try{
            String request="SELECT * FROM event WHERE place=?";
            PreparedStatement pst=cnx.prepareStatement(request);
            pst.setString(1,place);
            pst.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return event;
    }




}