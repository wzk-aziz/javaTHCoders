package Interfaces;

import entities.Items;

import java.util.List;

public interface ItemsInterface {
    public void addItems(Items i);
    public List<Items> AfficherItems();
    public void SupprimerItems(int id);
    public boolean updateItems(Items i) ;
    public List<Items> searchByName(String name);
    public List<Items> searchByRef(String ref);
    public List<Items> searchByPartCondition(String part_condition);
    public List<Items> searchByQuantity(int quantity);
}
