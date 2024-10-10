// package ui;

// import model.FoodItem;
// import model.Order;

// import java.util.ArrayList;
// import java.util.List;

// public class QuickBiteApp {
//     private List<String> comments;
//     private List<FoodItem> foodItems;
//     private List<FoodItem> orderedItems;

//     public void addComment(String comment) {
//         if (comment != null && !comment.trim().isEmpty()) {
//             comments.add(comment);
//             System.out.println("Comment added: " + comment);
//         } else {
//             System.out.println("Cannot add an empty or null comment.");
//         }
//     }

//     public void displayInfo() {
//         System.out.printf("Name: %s\nDescription: %s\nPrice: $%.2f\nImage: %s\n", name, description, price);
//         System.out.println("Comments:");
//         for (String comment : comments) {
//             System.out.println("- " + comment);
//         }
//     }

//     public void displayOrder() {
//         if (orderedItems.isEmpty()) {
//             System.out.println("The order is empty.");
//         } else {
//             System.out.println("\n=== Your Order ===");
//             for (int i = 0; i < orderedItems.size(); i++) {
//                 FoodItem item = orderedItems.get(i);
//                 System.out.printf("%d. %s - $%.2f\n", i + 1, item.getName(), item.getPrice());
//             }
//             System.out.printf("Total Price: $%.2f\n", countTotal());
//         }
//     }

//     public void displayMenu() {
//         if (foodItems.isEmpty()) {
//             System.out.println("The menu is empty.");
//         } else {
//             System.out.println("Menu:");
//             for (int i = 0; i < foodItems.size(); i++) {
//                 FoodItem item = foodItems.get(i);
//                 item.displayInfo();
//                 System.out.println("-------------------");
//                 System.out.println("-------------------");
//             }
//         }
//     }
// }
