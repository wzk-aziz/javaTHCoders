package services;

import Interfaces.InventoryInterface;
import entities.Inventory;
import utils.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryService implements InventoryInterface {
    private static InventoryService instance;
    private Connection cnx;

    private InventoryService() throws SQLException {
        cnx = MyConnection.getInstance().getCnx();
    }

    public static InventoryService getInstance() throws SQLException {
        if (instance == null) {
            instance = new InventoryService();
        }
        return instance;
    }

    public void addInventory(Inventory i) {
        try {
            String request = "INSERT INTO inventory (title, description,add_date) VALUES (?, ?,CURRENT_TIMESTAMP)";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setString(1, i.getTitle());
            pst.setString(2, i.getDescription());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Inventory> AfficherInventory() {
        List<Inventory> inventory = new ArrayList<>();
        try {
            String request = "SELECT * FROM inventory";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(request);
            while (rs.next()) {
                Inventory i = new Inventory();
                i.setId(rs.getInt("id"));
                i.setTitle(rs.getString("title"));
                i.setDescription(rs.getString("description"));
                i.setAdd_date(rs.getTimestamp("add_date"));
                inventory.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventory;
    }

    public void SupprimerInventory(int id) {
        try {
            String request = "DELETE FROM inventory WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean updateInventory(Inventory i) {
        try {
            String request = "UPDATE inventory SET title = ?, description = ? WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setString(1, i.getTitle());
            pst.setString(2, i.getDescription());
            pst.setInt(3, i.getId());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<Inventory> searchByTitle(String title) {
        List<Inventory> inventory = new ArrayList<>();
        try {
            String request = "SELECT * FROM inventory WHERE title LIKE ?";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setString(1, "%" + title + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Inventory i = new Inventory();
                i.setId(rs.getInt("id"));
                i.setTitle(rs.getString("title"));
                i.setDescription(rs.getString("description"));
                inventory.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inventory;
    }
}
