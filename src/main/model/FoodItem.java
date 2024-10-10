package model;

import java.util.ArrayList;
import java.util.List;

public class FoodItem {
    private String name;
    private String description;
    private double price;
    private List<String> comments;
    
    /**
     * Constructs a food item with the given name, description, and price.
     * Initializes an empty list of comments.
     * 
     * REQUIRES: name and description are not null, price is non-negative.
     * MODIFIES: this
     * EFFECTS: Initializes a FoodItem object with the given name, description, and price.
     *          Initializes an empty comments list
     * */
    public FoodItem(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.comments = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void increasePrice(double amount) {
        this.price += amount;
    }

    public void decreasePrice(double amount) {
        this.price -= amount;
    }

    public void addComment(String comment) {
        comments.add(comment);
    }

    public boolean removeComment(String comment) {
        return comments.remove(comment);
    }

    public List<String> getComments() {
        return comments;
    }

  
}
