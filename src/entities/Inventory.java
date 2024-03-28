package entities;
import java.util.Date;
import java.util.List;

public class Inventory {

    private int id;
    private String title;
    private String description;
    private Date add_date;
    private List<Items> items;
    public Inventory() {
    }

    public Inventory(String title, String description, Date add_date) {
        this.title = title;
        this.description = description;
        this.add_date = add_date;
    }

    public Inventory(int id, String title, String description, Date add_date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.add_date = add_date;
    }
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    public Date getAdd_date() {
        return add_date;
    }
    public List<Items> getItems() {
        return items;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }
    public void setItems(List<Items> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Inventory{" + "id=" + id + ", title=" + title + ", description=" + description + ", add_date=" + add_date + '}';
    }
}
