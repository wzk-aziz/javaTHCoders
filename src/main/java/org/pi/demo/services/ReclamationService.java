package org.pi.demo.services;

import org.pi.demo.entities.Reclamation;
import org.pi.demo.utils.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReclamationService {
    private static ReclamationService instance;
    private Connection cnx;

    public ReclamationService() throws SQLException {
        cnx = MyConnection.getInstance().getCnx();
    }

    public static ReclamationService getInstance() throws SQLException {
        if (instance == null) {
            instance = new ReclamationService();
        }
        return instance;
    }

    public void addReclamation(Reclamation r) {
        try {
            String request = "INSERT INTO Reclamation (nom,sujet,details,date) VALUES (?, ?, ?,CURRENT_TIMESTAMP)";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setString(1, r.getNom());
            pst.setString(2, r.getSujet());
            pst.setString(3, r.getDetails());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Reclamation> AfficherReclamation() {
        List<Reclamation> Reclamation = new ArrayList<>();
        try {
            String request = "SELECT * FROM Reclamation ORDER BY date DESC";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(request);
            while (rs.next()) {
                Reclamation r = new Reclamation();
                r.setNom(rs.getString("nom"));
                r.setSujet(rs.getString("sujet"));
                r.setDetails(rs.getString("details"));
                r.setDate(rs.getTimestamp("date").toLocalDateTime());
                Reclamation.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Reclamation;
    }
    public List<Reclamation> AfficherReclamationSortedByName() {
        List<Reclamation> Reclamation = new ArrayList<>();
        try {
            String request = "SELECT * FROM Reclamation ORDER BY nom ASC";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(request);
            while (rs.next()) {
                Reclamation r = new Reclamation();
                r.setNom(rs.getString("nom"));
                r.setSujet(rs.getString("sujet"));
                r.setDetails(rs.getString("details"));
                r.setDate(rs.getTimestamp("date").toLocalDateTime());
                Reclamation.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Reclamation;
    }

    public void SupprimerReclamation(String nom) {
        try {
            String request = "DELETE FROM Reclamation WHERE nom = ?";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setString(1,nom);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean updateReclamation(String name, String sujet, String details) {
        try {
            String request = "UPDATE Reclamation SET sujet = ?, details = ? WHERE nom = ?";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setString(1, sujet);
            pst.setString(2, details);
            pst.setString(3, name);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //stream
    public List<Reclamation> searchById(int id) {
        List<Reclamation> Reclamation = new ArrayList<>();
        try {
            String request = "SELECT * FROM Reclamation WHERE id =" + id +" ";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setString(1, "%" + id + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Reclamation i = new Reclamation();
                i.setId(rs.getInt("id"));
                i.setNom(rs.getString("nom"));
                i.setSujet(rs.getString("sujet"));
                i.setDetails(rs.getString("details"));
                Reclamation.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Reclamation;
    }
}
