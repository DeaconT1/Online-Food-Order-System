package ui;

import model.FoodItem;
import model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The QuickBiteApp class provides a console-based interface for interacting
 * with the ordering system. Users can browse the menu, add items to their order,
 * view their current order, and perform other actions like removing items or clearing the order.
 */
public class QuickBiteApp {
    private List<FoodItem> menu;      
    private Order currentOrder;       
    private Scanner scanner;          

    public QuickBiteApp() {
        initializeMenu();             
        currentOrder = new Order();   
        scanner = new Scanner(System.in);  
    }   

    // MODIFIES: this.menu
    // EFFECTS: Initializes the menu with predefined food items.
    private void initializeMenu() {
        menu = new ArrayList<>();
        menu.add(new FoodItem("MoonCake", "Traditional Chinese food for celebrating mid-autumn festival", 9.99));
        menu.add(new FoodItem("Cheese Burger", "Juicy beef cheese burger", 6.49));
        menu.add(new FoodItem("Sushi", "Fresh salmon sushi", 2.99));
        menu.add(new FoodItem("Kung Pao Chicken", "Spicy Chinese stir-fry with chicken, peanuts, and chili peppers.", 18.99));
        menu.add(new FoodItem("Salad", "Fresh garden salad", 5.99));
    }

    // MODIFIES: this.menu
    // EFFECTS: Initializes the menu with predefined food items.
    public void run() {
        System.out.println("\nWelcome to QuickBite! 🍚🍔🍣");
        boolean keepRunning = true;

        while (keepRunning) {
            displayMainMenu();          // show the main menu
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":               // browse the menu
                    displayMenu();
                    break;
                case "2":               // add the food in menu to the order
                    addItemToOrder();
                    break;
                case "3":               // check the current order
                    displayOrder();
                    break;
                case "4":               // remove item from the current order
                    removeItemFromOrder();
                    break;
                case "5":               // clear the order
                    clearOrder();
                    break;
                case "6":
                    placeOrder();
                    break;
                case "7":               // quit
                    System.out.println("Thank you for choosing QuickBite! Goodbye! 👋");
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid input. Please choose a valid option.");
            }
        }
    }

    // EFFECTS: Displays the main menu options in the console.
    private void displayMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Browse Menu");
        System.out.println("2. Add Item to Order");
        System.out.println("3. View Current Order");
        System.out.println("4. Remove Item from Order");
        System.out.println("5. Clear Order");
        System.out.println("6. Place your order");
        System.out.println("7. Exit");
        System.out.print("Please enter your choice: ");
    }

    // EFFECTS: Displays all items in the menu along with their description and price.
    private void displayMenu() {
        System.out.println("\nMenu:");
        for (int i = 0; i < menu.size(); i++) {
            FoodItem item = menu.get(i);
            System.out.printf("%d. %s - $%.2f\n", (i + 1), item.getName(), item.getPrice());
            System.out.println("   Description: " + item.getDescription());
        }
    }

    // REQUIRES: User input must be a valid item number present in the menu.
    // MODIFIES: this.currentOrder
    // EFFECTS: Adds the selected food item from the menu to the current order.
    private void addItemToOrder() {
        displayMenu();  // this method will first display the menu
        System.out.print("Enter the number of the item you want to add to your order: ");
        int itemNumber = Integer.parseInt(scanner.nextLine()) - 1;

        if (itemNumber >= 0 && itemNumber < menu.size()) {
            FoodItem item = menu.get(itemNumber);
            currentOrder.addFoodItem(item);
            System.out.println(item.getName() + " has been added to your order!");
        } else {
            System.out.println("Invalid item number.");
        }
    }

    // EFFECTS: Displays all items currently in the order along with their description and price. 
    //          Shows the total cost of the order.  
    private void displayOrder() {
        List<FoodItem> orderedItems = currentOrder.getOrderedItems();

        if (orderedItems.isEmpty()) {
            System.out.println("Your order is currently empty.");
        } else {
            System.out.println("\nCurrent Order:");
            for (int i = 0; i < orderedItems.size(); i++) {
                FoodItem item = orderedItems.get(i);
                System.out.printf("%d. %s - $%.2f\n", (i + 1), item.getName(), item.getPrice());
                System.out.println("   Description: " + item.getDescription());
            }
            System.out.printf("Total: $%.2f\n", currentOrder.countTotal());
        }
    }

    // REQUIRES: User input must be a valid item number present in the current order.
    // MODIFIES: this.currentOrder
    // EFFECTS: Removes the specified item from the current order.
    private void removeItemFromOrder() {
        displayOrder();  // first it will display the current order
        if (currentOrder.getItemCount() == 0) {
            return;  // if there's nothing in the current, just return
        }

        System.out.print("Enter the number of the item you want to remove from your order: ");
        int itemNumber = Integer.parseInt(scanner.nextLine()) - 1;

        if (itemNumber >= 0 && itemNumber < currentOrder.getItemCount()) {
            FoodItem item = currentOrder.getOrderedItems().get(itemNumber);
            currentOrder.removeFoodItem(item);
            System.out.println(item.getName() + " has been removed from your order!");
        } else {
            System.out.println("Invalid item number.");
        }
    }

    // MODIFIES: this.currentOrder
    // EFFECTS: Clears all items from the current order.
    private void clearOrder() {
        currentOrder.clearOrder();
        System.out.println("Your order has been cleared.");
    }

    //MODIFIES: this.currentOrder
    // EFFECTS: Displays the total amount of the order and clears the current order after placing it.
    private void placeOrder() {
    System.out.printf("Your order has been successfully placed! Total amount: $%.2f\n", currentOrder.countTotal());
    System.out.println("Thank you for your order! Your order is now being prepared. 🍽️");
    currentOrder.clearOrder();
}

}