package org.pi.demo.services;

import org.pi.demo.Interfaces.ItemsInterface;
import org.pi.demo.entities.Inventory;
import org.pi.demo.entities.Items;
import org.pi.demo.utils.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemsService implements ItemsInterface {

    public static ItemsService instance;
    private Connection cnx;

    public ItemsService() throws SQLException {
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
            String request = "INSERT INTO items (name,description,ref, part_condition, quantity,photos,inventory_id) VALUES (?, ?,?, ?, ?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setString(1, i.getName());
            pst.setString(2, i.getDescription());
            pst.setString(3, i.getRef());
            pst.setString(4, i.getPart_condition());
            pst.setInt(5, i.getQuantity());
            pst.setString(6, i.getPhotos());
            pst.setInt(7, i.getInventory_id());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

public List<Items> AfficherItems() {
    List<Items> items = new ArrayList<>();
    try {
        String request = "SELECT items.*, inventory.title AS inventory_title FROM items LEFT JOIN inventory ON items.inventory_id = inventory.id";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(request);
        while (rs.next()) {
            Items i = new Items();
            i.setId(rs.getInt("id"));
            i.setName(rs.getString("name"));
            i.setDescription(rs.getString("description"));
            i.setRef(rs.getString("ref"));
            i.setPart_condition(rs.getString("part_condition"));
            i.setQuantity(rs.getInt("quantity"));
            i.setPhotos(rs.getString("photos"));
            i.setInventory_id(rs.getInt("inventory_id"));

            // Create a new Inventory object and set the title
            Inventory inventory = new Inventory();
            inventory.setTitle(rs.getString("inventory_title")); // This will be null if there's no matching inventory_id

            // Set the Inventory object to the Items object
            i.setInventory(inventory);

            items.add(i);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return items;
}
//    public List<Items> AfficherItems() {
//        List<Items> items = new ArrayList<>();
//        try {
//            String request = "SELECT items.*, inventory.title AS inventory_title FROM items INNER JOIN inventory ON items.inventory_id = inventory.id";
//            Statement st = cnx.createStatement();
//            ResultSet rs = st.executeQuery(request);
//            while (rs.next()) {
//                Items i = new Items();
//                i.setId(rs.getInt("id"));
//                i.setName(rs.getString("name"));
//                i.setDescription(rs.getString("description"));
//                i.setRef(rs.getString("ref"));
//                i.setPart_condition(rs.getString("part_condition"));
//                i.setQuantity(rs.getInt("quantity"));
//                i.setPhotos(rs.getString("photos"));
//                i.setInventory_id(rs.getInt("inventory_id"));
//
//                // Create a new Inventory object and set the title
//                Inventory inventory = new Inventory();
//                inventory.setTitle(rs.getString("inventory_title"));
//
//                // Set the Inventory object to the Items object
//                i.setInventory(inventory);
//
//                items.add(i);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return items;
//    }
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
            String request = "UPDATE items SET name = ?,description=?, ref = ?, part_condition = ?, quantity = ?, photos= ?, inventory_id = ? WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setString(1, i.getName());
            pst.setString(2, i.getDescription());
            pst.setString(3, i.getRef());
            pst.setString(4, i.getPart_condition());
            pst.setInt(5, i.getQuantity());
            pst.setString(6, i.getPhotos());
            pst.setInt(7, i.getInventory_id());
            pst.setInt(8, i.getId());
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
                i.setPhotos(rs.getString("photos"));
                items.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
//stream
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
                i.setPhotos(rs.getString("photos"));
                items.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
//stream
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
                i.setPhotos(rs.getString("photos"));
                items.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

//stream
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
                i.setPhotos(rs.getString("photos"));
                items.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }


    public List<Items> getItemsForInventory(int inventoryId) {
        List<Items> items = new ArrayList<>();
        try {
            String request = "SELECT * FROM items WHERE inventory_id = ?";
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setInt(1, inventoryId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Items i = new Items();
                i.setId(rs.getInt("id"));
                i.setName(rs.getString("name"));
                i.setDescription(rs.getString("description"));
                i.setRef(rs.getString("ref"));
                i.setPart_condition(rs.getString("part_condition"));
                i.setQuantity(rs.getInt("quantity"));
                i.setPhotos(rs.getString("photos"));
                i.setInventory_id(rs.getInt("inventory_id"));
                items.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

public Items getItemById(int id) {
    Items item = null;
    try {
        String request = "SELECT * FROM items WHERE id = ?";
        PreparedStatement pst = cnx.prepareStatement(request);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            item = new Items();
            item.setId(rs.getInt("id"));
            item.setName(rs.getString("name"));
            item.setDescription(rs.getString("description"));
            item.setRef(rs.getString("ref"));
            item.setPart_condition(rs.getString("part_condition"));
            item.setQuantity(rs.getInt("quantity"));
            item.setPhotos(rs.getString("photos"));
            item.setInventory_id(rs.getInt("inventory_id"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return item;
}
















}