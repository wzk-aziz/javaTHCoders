package org.pi.demo.services;


import org.pi.demo.Interfaces.ReservationInterface;

import org.pi.demo.entities.Reservation;
import org.pi.demo.utils.MyConnection;

import java.sql.Connection; // Correct import
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements ReservationInterface {

    private Connection cnx;
    private static ReservationService instance;
    private ReservationService() throws SQLException {
        cnx = MyConnection.getInstance().getCnx();

    }

    public static ReservationService getInstance() throws SQLException {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }




    @Override
    public void addReservation(Reservation rv) {
        try {
            String request = "INSERT INTO reservation(nom, address,phone,event_id) VALUES (?, ?, ?,?)";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setString(1, rv.getNom());
            pst.setString(2, rv.getAddress());
            pst.setInt(3, rv.getPhone());
            pst.setInt(4,rv.getEvent_id());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    @Override
    public List<Reservation> AfficherReservation() {
        List<Reservation> reservations = new ArrayList<>();
        try {
            String request = "SELECT * FROM reservation";
            PreparedStatement pst = cnx.prepareStatement(request);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Reservation reservation = new Reservation(rs.getInt("id"), rs.getString("nom"), rs.getString("address"),rs.getInt("phone"),rs.getInt("event_id"));
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;

    }


    @Override
    public void SupprimerReservation(int id) {
        try {
            String request = "DELETE FROM reservation WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    @Override
    public boolean updateReservatoin(Reservation rv) {
        try {
            String request = "UPDATE reservation SET nom = ?, address = ?, phone = ? WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setString(1, rv.getNom());
            pst.setString(2, rv.getAddress());
            pst.setInt(3, rv.getPhone());
            int rowsUpdated = pst.executeUpdate();
            return rowsUpdated > 0;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
