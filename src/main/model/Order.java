package model;

import java.util.ArrayList;
import java.util.List;

public class Order {
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

    public List<FoodItem> getOrderedItems() {
        return new ArrayList<>(orderedItems);  
    }

    public int getItemCount() {
        return orderedItems.size();
    }

    public void clearOrder() {
        orderedItems.clear();
    }

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

}
