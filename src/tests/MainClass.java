package tests;

import entities.Inventory;
import services.InventoryService;
import utils.MyConnection;

import java.sql.SQLException;
import java.util.List;

public class MainClass {
    public static void main(String[] args) throws SQLException {
        MyConnection mc=new MyConnection();

        // Test adding inventory
        testAddInventory();
    }

    public static void testAddInventory() {
        // Create a new inventory
        Inventory newInventory = new Inventory();
        newInventory.setTitle("Test Title");
        newInventory.setDescription("Test Description");

        // Add the new inventory *
        try {
            InventoryService.getInstance().addInventory(newInventory);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Retrieve the list of all inventory i
        List<Inventory> inventoryList = null;
        try {
            inventoryList = InventoryService.getInstance().AfficherInventory();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Check if the new inventory item is in the list
        boolean isAdded = false;
        for (Inventory inventory : inventoryList) {
            if (inventory.getTitle().equals(newInventory.getTitle()) &&
                    inventory.getDescription().equals(newInventory.getDescription())) {
                isAdded = true;
                break;
            }
        }

        // Print the result
        if (isAdded) {
            System.out.println("The new inventory item was added successfully.");
        } else {
            System.out.println("The new inventory item was not added.");
        }
    }
}