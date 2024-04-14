package tests;

import entities.Inventory;
import entities.Items;
import services.InventoryService;
import services.ItemsService;
import utils.MyConnection;

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