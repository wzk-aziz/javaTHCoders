package org.pi.demo.services;

import org.pi.demo.Interfaces.EventInterface;
import org.pi.demo.entities.Events;
import org.pi.demo.utils.MyConnection;

import java.sql.Connection; // Correct import
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventService implements EventInterface {


    private Connection cnx;
    private static EventService instance;

    public EventService() throws SQLException {
        cnx = (Connection) MyConnection.getInstance().getCnx();

    }

    public static EventService getInstance() throws SQLException {
        if (instance == null) {
            instance = new EventService();
        }
        return instance;
    }

    public void addEvent(Events ev) {
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



   public boolean SupprimerEvent(int id) {
    try {
        String request = "DELETE FROM event WHERE id = ?";
        PreparedStatement pst = cnx.prepareStatement(request);
        pst.setInt(1, id);
        int rowsDeleted = pst.executeUpdate();
        return rowsDeleted > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public List<Events> AfficherEvent() {
    List<Events> events = new ArrayList<>();
    try {
        String request = "SELECT * FROM event";
        PreparedStatement pst = cnx.prepareStatement(request);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Events event = new Events(rs.getInt("id"), rs.getString("event_name"), rs.getString("place"), rs.getString("description") ,rs.getString("image"), rs.getInt("capacity"), rs.getDate("start_date"), rs.getDate("end_date"));
            events.add(event);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return events;
}


    public boolean updateEvent(Events ev) {
        try {
            String request = "UPDATE event SET event_name = ?, capacity = ?, start_date = ?, end_date = ?, place = ?, description = ?,image= ? WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setString(1, ev.getEvent_name());
            pst.setInt(2, ev.getCapacity());
            pst.setDate(3, new java.sql.Date(ev.getStart_date().getTime()));
            pst.setDate(4, new java.sql.Date(ev.getEnd_date().getTime()));
            pst.setString(5, ev.getPlace());
            pst.setString(6, ev.getDescription());
            pst.setString(7, ev.getImage());
            pst.setInt(8, ev.getId());

            int rowsUpdated = pst.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage()); // Print the SQL exception message
            e.printStackTrace();
        }
        return false;
    }



    public List<Events> searchByPlace(String place){
        List<Events> events = new ArrayList<>();
        try{
            String request="SELECT * FROM event WHERE place=?";
            PreparedStatement pst=cnx.prepareStatement(request);
            pst.setString(1,place);
            pst.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return events;
    }

   public void decrementEventCapacity(int eventId) throws SQLException {
    String query = "UPDATE event SET capacity = capacity - 1 WHERE id = ?";
    PreparedStatement pst = cnx.prepareStatement(query);
    pst.setInt(1, eventId);
    pst.executeUpdate();
}

public Events getEventById(int eventId) throws SQLException {
    String query = "SELECT * FROM event WHERE id = ?";
    PreparedStatement pst = cnx.prepareStatement(query);
    pst.setInt(1, eventId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
        return new Events(rs.getInt("id"), rs.getString("event_name"), rs.getString("place"), rs.getString("description"), rs.getString("image"), rs.getInt("capacity"), rs.getDate("start_date"), rs.getDate("end_date"));
    }
    return null;
}
public List<Events> getTop3EventsBasedOnReservations() throws SQLException {
    String query = "SELECT e.*, COUNT(r.id) as reservation_count " +
            "FROM Event e " +
            "JOIN Reservation r ON e.id = r.event_id " +
            "GROUP BY e.id " +
            "ORDER BY reservation_count DESC " +
            "LIMIT 3";

    PreparedStatement preparedStatement = cnx.prepareStatement(query);
    ResultSet resultSet = preparedStatement.executeQuery();

    List<Events> eventsList = new ArrayList<>();
    while (resultSet.next()) {
        Events event = new Events();
        event.setId(resultSet.getInt("id"));
        event.setEvent_name(resultSet.getString("event_name"));
        event.setPlace(resultSet.getString("place"));
        event.setDescription(resultSet.getString("description"));
        event.setImage(resultSet.getString("image"));
        event.setCapacity(resultSet.getInt("capacity"));
        event.setStart_date(resultSet.getDate("start_date"));
        event.setEnd_date(resultSet.getDate("end_date"));
        eventsList.add(event);
    }
    return eventsList;
}
}