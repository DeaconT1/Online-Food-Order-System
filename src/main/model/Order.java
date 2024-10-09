package model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<FoodItem> orderedItems;

    // Construct a emtpy order list
    public Order() {
        this.orderedItems = new ArrayList<>();
    }

    // Requires: item != null
    // Modifies: this
    // Effects: add foodItem to the ordered list
    public void addFoodItem(FoodItem item) {
        this.orderedItems.add(item);
        System.out.println("Added food item to order: " + item.getName());
    }

    // Requires: item != null && !orderedItems.isEmpty() && item in orderedItems
    // Modifies: this
    // Effects: remove foodItem in the ordered list
    public void removeFoodItem(FoodItem item) {
        this.orderedItems.remove(item);
        System.out.println("Removed food item in the order" + item.getName());
    }

    // Effects: return the total price of the order
    public double countTotal() {
        double totalprice = 0;
        for (FoodItem item : orderedItems) {
            totalprice += item.getPrice();
        }
        return totalprice;
    }

    public void displayOrder() {
        if (orderedItems.isEmpty()) {
            System.out.println("The order is empty.");
        } else {
            System.out.println("\n=== Your Order ===");
            for (int i = 0; i < orderedItems.size(); i++) {
                FoodItem item = orderedItems.get(i);
                System.out.printf("%d. %s - $%.2f\n", i + 1, item.getName(), item.getPrice());
            }
            System.out.printf("Total Price: $%.2f\n", countTotal());
        }
    }



}
