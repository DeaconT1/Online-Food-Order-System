package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

public class FoodItem implements Writable {
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

    // EFFECTS: return the FoodItem name
    public String getName() {
        return name;
    }
    
    // REQUIRES: name != null
    // MODIFIES: this
    // EFFECTS: change the FoodItem Name
    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: return the FoodItem description
    public String getDescription() {
        return description;
    }

    // REQUIRES: description != null
    // MODIFIES: this
    // EFFECTS: change the FoodItem Name
    public void setDescription(String description) {
        this.description = description;
    }

    // EFFECTS: return the FoodItem price
    public double getPrice() {
        return price;
    }

    // REQUIRES: price > 0
    // MODIFIES: this
    // EFFECTS: change the FoodItem price
    public void setPrice(double price) {
        this.price = price;
    }

    // REQUIRES: price > 0
    // MODIFIES: this
    // EFFECTS: add a certain amount to the FoodItem price
    public void increasePrice(double amount) {
        this.price += amount;
    }

    // REQUIRES: price > 0
    // MODIFIES: this
    // EFFECTS: minus a certain amount to the FoodItem price
    public void decreasePrice(double amount) {
        this.price -= amount;
    }

    // REQUIRES: comment != null
    // MODIFIES: this
    // EFFECTS: add a comment to the FoodItem
    public void addComment(String comment) {
        comments.add(comment);
    }

    // REQUIRES: !comments.isEmpty() && comment != null
    // MODIFIES: this
    // EFFECTS: remove a comment to the FoodItem
    public boolean removeComment(String comment) {
        return comments.remove(comment);
    }

    // EFFECTS: return the FoodItem comments
    public List<String> getComments() {
        return comments;
    }

    @Override
    // EFFECTS: returns this food item as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("description", description);
        json.put("price", price);
        json.put("comments", commentsToJson());
        return json;
    }

    // EFFECTS: returns the comments as a JSON array
    private JSONArray commentsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (String comment : comments) {
            jsonArray.put(comment);  
        }
        return jsonArray;
    }
}

  

