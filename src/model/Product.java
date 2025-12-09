package model;
import java.time.Instant;

public class Product {

    private int identifier;
    private String name;
    private Instant creationDateTime;
    private Category category;

    public Product(int identifier, String name, Instant creationDateTime, Category category) {
        this.identifier = identifier;
        this.name = name;
        this.creationDateTime = creationDateTime;
        this.category = category;
    }

    public String toString() {
        return name +
                " (créé le " + creationDateTime + ")" +
                " - Catégorie : " + category.getName();
    }
}
