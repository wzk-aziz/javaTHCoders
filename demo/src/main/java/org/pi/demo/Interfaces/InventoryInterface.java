package org.pi.demo.Interfaces;

import org.pi.demo.entities.Inventory;

import java.util.List;

public interface InventoryInterface {
    public void addInventory(Inventory i);
    public List<Inventory> AfficherInventory();
    public void SupprimerInventory(int id);
    public boolean updateInventory(Inventory i) ;
    public List<Inventory> searchByTitle(String title);
}
