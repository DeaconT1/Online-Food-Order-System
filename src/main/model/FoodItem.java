package model;

import java.util.ArrayList;
import java.util.List;

public class FoodItem {
    private String name;
    private String description;
    private double price;
    private List<String> comments;

    public FoodItem() {

    }

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

    public void addComment(String comment) {
        if (comment != null && !comment.trim().isEmpty()) {  // 确保评论非空且不为null
            comments.add(comment);
            System.out.println("Comment added: " + comment);
        } else {
            System.out.println("Cannot add an empty or null comment.");
        }
    }

    public List<String> getComments() {
        return comments;
    }

    public void displayInfo() {
        System.out.printf("Name: %s\nDescription: %s\nPrice: $%.2f\nImage: %s\n", name, description, price);
        System.out.println("Comments:");
        for (String comment : comments) {
            System.out.println("- " + comment);
        }
    }

    
}
