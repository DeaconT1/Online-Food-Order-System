package model;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<FoodItem> foodItems;

    public Menu() {
        this.foodItems = new ArrayList<>();
    }

    public void displayMenu() {
        if (foodItems.isEmpty()) {
            System.out.println("The menu is empty.");
        } else {
            System.out.println("Menu:");
            for (int i = 0; i < foodItems.size(); i++) {
                FoodItem item = foodItems.get(i);
                item.displayInfo();
                System.out.println("-------------------");
                System.out.println("-------------------");
            }
        }
    }
    
}
