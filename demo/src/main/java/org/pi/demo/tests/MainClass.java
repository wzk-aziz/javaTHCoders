package org.pi.demo.tests;

import org.pi.demo.entities.Inventory;
import org.pi.demo.entities.Items;
import org.pi.demo.services.InventoryService;
import org.pi.demo.services.ItemsService;
import org.pi.demo.utils.MyConnection;

import java.sql.SQLException;
import java.util.List;

public class MainClass {
    public static void main(String[] args) throws SQLException {
        MyConnection mc = new MyConnection();
        InventoryService inv = new InventoryService();
        Inventory i = new Inventory("test", "test", null);
        inv.addInventory(i);
        List<Inventory> invs = inv.AfficherInventory();
        for (Inventory in : invs) {
            System.out.println(in);


        }

        ItemsService is = new ItemsService();
        Items it = new Items("test", "test", "test", "New", 1, 19,"test photos");
        is.addItems(it);
        List<Items> its = is.AfficherItems();
        for (Items item : its) {
            System.out.println(item);
        }


    }
}