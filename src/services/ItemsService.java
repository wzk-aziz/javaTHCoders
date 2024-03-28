package services;

import Interfaces.ItemsInterface;
import entities.Items;
import utils.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemsService implements ItemsInterface {

    public static ItemsService instance;
    private Connection cnx;

    private ItemsService() throws SQLException {
        cnx = MyConnection.getInstance().getCnx();
    }

    public static ItemsService getInstance() throws SQLException {
        if (instance == null) {
            instance = new ItemsService();
        }
        return instance;
    }

    public void addItems(Items i) {
        try {
            String request = "INSERT INTO items (name, ref, part_condition, quantity) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setString(1, i.getName());
            pst.setString(2, i.getRef());
            pst.setString(3, i.getPart_condition());
            pst.setInt(4, i.getQuantity());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Items> AfficherItems() {
        List<Items> items = new ArrayList<>();
        try {
            String request = "SELECT * FROM items";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(request);
            while (rs.next()) {
                Items i = new Items();
                i.setId(rs.getInt("id"));
                i.setName(rs.getString("name"));
                i.setRef(rs.getString("ref"));
                i.setPart_condition(rs.getString("part_condition"));
                i.setQuantity(rs.getInt("quantity"));
                items.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return items;
    }

    public void SupprimerItems(int id) {
        try {
            String request = "DELETE FROM items WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateItems(Items i) {
        try {
            String request = "UPDATE items SET name = ?, ref = ?, part_condition = ?, quantity = ? WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setString(1, i.getName());
            pst.setString(2, i.getRef());
            pst.setString(3, i.getPart_condition());
            pst.setInt(4, i.getQuantity());
            pst.setInt(5, i.getId());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Items> searchByName(String name) {
        List<Items> items = new ArrayList<>();
        try {
            String request = "SELECT * FROM items WHERE name = ?";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Items i = new Items();
                i.setId(rs.getInt("id"));
                i.setName(rs.getString("name"));
                i.setRef(rs.getString("ref"));
                i.setPart_condition(rs.getString("part_condition"));
                i.setQuantity(rs.getInt("quantity"));
                items.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<Items> searchByRef(String ref) {
        List<Items> items = new ArrayList<>();
        try {
            String request = "SELECT * FROM items WHERE ref = ?";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setString(1, ref);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Items i = new Items();
                i.setId(rs.getInt("id"));
                i.setName(rs.getString("name"));
                i.setRef(rs.getString("ref"));
                i.setPart_condition(rs.getString("part_condition"));
                i.setQuantity(rs.getInt("quantity"));
                items.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<Items> searchByPartCondition(String part_condition) {
        List<Items> items = new ArrayList<>();
        try {
            String request = "SELECT * FROM items WHERE part_condition = ?";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setString(1, part_condition);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Items i = new Items();
                i.setId(rs.getInt("id"));
                i.setName(rs.getString("name"));
                i.setRef(rs.getString("ref"));
                i.setPart_condition(rs.getString("part_condition"));
                i.setQuantity(rs.getInt("quantity"));
                items.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }


    public List<Items> searchByQuantity(int quantity) {
        List<Items> items = new ArrayList<>();
        try {
            String request = "SELECT * FROM items WHERE quantity = ?";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setInt(1, quantity);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Items i = new Items();
                i.setId(rs.getInt("id"));
                i.setName(rs.getString("name"));
                i.setRef(rs.getString("ref"));
                i.setPart_condition(rs.getString("part_condition"));
                i.setQuantity(rs.getInt("quantity"));
                items.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }


















}