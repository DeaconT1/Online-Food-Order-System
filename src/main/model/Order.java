package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

public class Order implements Writable {
    private List<FoodItem> orderedItems;

    // Construct a emtpy order list
    public Order() {
        this.orderedItems = new ArrayList<>();
    }

    // REQUIRES: item != null
    // MODIFIES: this
    // EFFECTS: add foodItem to the ordered list
    public void addFoodItem(FoodItem item) {
        this.orderedItems.add(item);
    }

    // REQUIRES: item != null && !orderedItems.isEmpty() && item in orderedItems
    // MODIFIES: this
    // EFFECTS: remove foodItem in the ordered list
    public void removeFoodItem(FoodItem item) {
        this.orderedItems.remove(item);
    }

    // EFFECTS: return a copy of the orderedItems
    public List<FoodItem> getOrderedItems() {
        return new ArrayList<>(orderedItems);  
    }

    // EFFECTS: return the total FoodItem
    public int getItemCount() {
        return orderedItems.size();
    }

    // REQUIRES: !orderedItems.isEmpty()
    // MODIFIES: this
    // EFFECTS: remove all the FoodItem in the current order
    public void clearOrder() {
        orderedItems.clear();
    }

    // REQUIRES: item != null
    // EFFECTS: return true if the current ordered items contain the item
    //          otherwise false
    public boolean containsFoodItem(FoodItem item) {
        return orderedItems.contains(item);
    }

    // EFFECTS: return the total price of the order
    public double countTotal() {
        double totalprice = 0;
        for (FoodItem item : orderedItems) {
            totalprice += item.getPrice();
        }
        return totalprice;
    }

    // EFFECTS: return true if empty false otherwise;
    public boolean orderisEmpty() {
        return orderedItems.isEmpty();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("orderedItems", orderedItemsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray orderedItemsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (FoodItem item : orderedItems) {
            jsonArray.put(item.toJson());
        }

        return jsonArray;
    }

}
