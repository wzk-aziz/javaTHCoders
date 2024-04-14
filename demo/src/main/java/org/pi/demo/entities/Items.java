package org.pi.demo.entities;



import java.util.Objects;

public class Items {
    private int id;

    private String name;
    private String description;
    private String ref;
    private String part_condition;
    private int quantity;
    private int inventory_id;
    private String photos;
    private Inventory inventory; // This represents the inventory that an item belongs to

    public Items() {
    }

    public Items(String name, String description, String ref, String part_condition, int quantity, int inventory_id, String photos) {
        this.name = name;
        this.description = description;
        this.ref = ref;
        this.part_condition = part_condition;
        this.quantity = quantity;
        this.inventory_id = inventory_id;
        this.photos = photos;

    }

    public Items(int id, String name, String description, String ref, String part_condition, int quantity, int inventory_id, String photos, Inventory inventory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ref = ref;
        this.part_condition = part_condition;
        this.quantity = quantity;
        this.inventory_id = inventory_id;
        this.photos = photos;
        this.inventory = inventory;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {

       if(description == null || description.isEmpty()){
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        this.description = description;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
       if(ref == null || ref.isEmpty()){
            throw new IllegalArgumentException("Ref cannot be null or empty");
        }
        this.ref = ref;
    }

    public String getPart_condition() {
        return part_condition;
    }

    public void setPart_condition(String part_condition) {
    if(part_condition == null || part_condition.isEmpty()){
        throw new IllegalArgumentException("Part condition cannot be null or empty");
    }
    if(!part_condition.equalsIgnoreCase("New") && !part_condition.equalsIgnoreCase("Used") && !part_condition.equalsIgnoreCase("Refurbished")){
        throw new IllegalArgumentException("Part condition can be either 'New', 'Used' or 'Refurbished'");
    }
    this.part_condition = part_condition;
}
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    public int getInventory_id() {
        return inventory_id;
    }

    public void setInventory_id(int inventory_id) {
        this.inventory_id = inventory_id;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        if(photos == null || photos.isEmpty()){
            throw new IllegalArgumentException("Photos cannot be null or empty");
        }
        this.photos = photos;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        if (inventory == null) {
            throw new IllegalArgumentException("Inventory cannot be null");
        }
        this.inventory = inventory;
    }

    @Override
    public String toString() {
        return "Items{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ref='" + ref + '\'' +
                ", part_condition='" + part_condition + '\'' +
                ", quantity=" + quantity +
                ", inventory_id=" + inventory_id +
                ", photos='" + photos + '\'' +
                ", inventory=" + inventory +
                '}';
    }

// equals() and hashCode() methods


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Items items = (Items) obj;
        return Objects.equals(name, items.name) &&
                Objects.equals(description, items.description) &&
                Objects.equals(ref, items.ref) &&
                Objects.equals(part_condition, items.part_condition) &&
                quantity == items.quantity &&
                inventory_id == items.inventory_id &&
                Objects.equals(photos, items.photos) &&
                Objects.equals(inventory, items.inventory);
    }
    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getRef() != null ? getRef().hashCode() : 0);
        result = 31 * result + (getPart_condition() != null ? getPart_condition().hashCode() : 0);
        result = 31 * result + getQuantity();
        result = 31 * result + getInventory_id();
        result = 31 * result + (getPhotos() != null ? getPhotos().hashCode() : 0);
        result = 31 * result + (getInventory() != null ? getInventory().hashCode() : 0);
        return result;
    }
}