package org.pi.demo.services;

import org.pi.demo.entities.Echange;
import org.pi.demo.utils.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EchangeService {
    private static EchangeService instance;
    private Connection cnx;

    public EchangeService() throws SQLException {
        cnx = MyConnection.getInstance().getCnx();
    }

    public static EchangeService getInstance() throws SQLException {
        if (instance == null) {
            instance = new EchangeService();
        }
        return instance;
    }

    public void addEchange(Echange ec) {
        try {
            String request = "INSERT INTO Echange (etat,offre,image) VALUES (?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setString(1, ec.getEtat());
            pst.setString(2, ec.getOffre());
            pst.setString(3, ec.getImage());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Echange> AfficherEchange() {
        List<Echange> Echange = new ArrayList<>();
        try {
            String request = "SELECT * FROM Echange";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(request);
            while (rs.next()) {
                Echange ec = new Echange();
                ec.setId(rs.getInt("id"));
                ec.setEtat(rs.getString("etat"));
                ec.setOffre(rs.getString("offre"));
                ec.setImage(rs.getString("image"));

                Echange.add(ec);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Echange;
    }

    public void SupprimerEchange(int id) {
        try {
            String request = "DELETE FROM Echange WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean updateEchange(Echange ec) {
        try {
            String request = "UPDATE Echange SET etat = ?,offre = ?, image = ? WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setString(1, ec.getEtat());
            pst.setString(2, ec.getOffre());
            pst.setString(3, ec.getImage());
            pst.setInt(4, ec.getId());

            System.out.println("Executing SQL query: " + pst.toString()); // Add this line to log the SQL query being executed

            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Echange> searchById(int id) {
        List<Echange> Echange = new ArrayList<>();
        try {
            String request = "SELECT * FROM Echange WHERE id =" + id +" ";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setString(1, "%" + id + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Echange ec = new Echange();
                ec.setId(rs.getInt("id"));
                ec.setEtat(rs.getString("etat"));
                ec.setOffre(rs.getString("offre"));
                ec.setImage(rs.getString("image"));
                Echange.add(ec);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Echange;
    }
}
