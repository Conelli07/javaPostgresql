package model;

public class Category {

    private int identifier;
    private String name;

    public Category(int identifier, String name) {
        this.identifier = identifier;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name + " (id: " + identifier + ")";
    }
}
