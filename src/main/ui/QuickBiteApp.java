package ui;

import model.FoodItem;
import model.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistence.JsonReader;
import persistence.JsonWriter;


/**
 * Constructs a new QuickBiteApp with initialized fields and a welcome page.
 * Sets up the JFrame properties such as size, layout, and close operation.
 * 
 * REQUIRES: None
 * MODIFIES: this
 * EFFECTS: Initializes fields and displays the welcome page.
 *          Sets up the default behavior for closing the application.
 */
public class QuickBiteApp extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String JSON_STORE = "./data/order.json";
    
    private List<FoodItem> menu;
    private Order currentOrder;


    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    private JPanel menuPanel;
    private JPanel orderPanel;
    private JPanel buttonPanel;
    private DefaultListModel<String> orderListModel;
    private JList<String> orderList;
    private JTextArea descriptionArea;
    private JLabel totalLabel;
    private JList<String> menuList;

    private JPanel welcomePanel;
    private ImageIcon welcomeImage;

    private boolean isOrderSaved = true;

    private Map<String, ImageIcon> foodImages;

    private JLabel foodImageLabel;

    // EFFECTS: construct a new QuickBiteApp with InitailizeFields(include pannel) and welcomepage
    public QuickBiteApp() {
        super("QuickBite Food Ordering System");
        initializeFields();
        createWelcomePage();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: menu, currentOrder, jsonWriter, jsonReader, orderListModel
    // EFFECTS: initialieze all the fields.
    private void initializeFields() {
        menu = new ArrayList<>();
        currentOrder = new Order();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        orderListModel = new DefaultListModel<>();
        initializeMenu();
        initializeFoodImages();
    }

    // EFFECTS: set up the panel, including the menuPanel, orderPanel and buttonPanel.
    //          Remind the customer to save their order when closing the menu panel.
    private void initializeGraphics() {
        setLayout(new BorderLayout(10, 10));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        
        createMenuPanel();
        createOrderPanel();
        createButtonPanel();
        
        add(menuPanel, BorderLayout.WEST);
        add(orderPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveOrderPrompt();
                dispose();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    // MODIFIES: menuPanel, menuList, foodImageLabel, descriptionArea
    // EFFECTS: create the MenuPanel with a BorderLayout(for order and botton layout)
    //          also with the panel displaying the foodItems.
    @SuppressWarnings("methodlength")
    private void createMenuPanel() {
        menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBorder(BorderFactory.createTitledBorder("Menu"));
        
        JPanel topPanel = new JPanel(new BorderLayout());
        
        menuList = new JList<>(getMenuNames());
        menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        foodImageLabel = new JLabel();
        foodImageLabel.setPreferredSize(new Dimension(300, 200));
        foodImageLabel.setHorizontalAlignment(JLabel.CENTER);
        
        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        
        menuList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = menuList.getSelectedIndex();
                if (index >= 0) {
                    FoodItem selected = menu.get(index);
                    descriptionArea.setText(selected.getDescription() 
                            + "\nPrice: $" + String.format("%.2f", selected.getPrice()));
                    updateFoodImage(selected.getName());
                }
            }
        });

        topPanel.add(new JScrollPane(menuList), BorderLayout.WEST);
        topPanel.add(foodImageLabel, BorderLayout.CENTER);
        
        menuPanel.add(topPanel, BorderLayout.CENTER);
        menuPanel.add(new JScrollPane(descriptionArea), BorderLayout.SOUTH);
        menuPanel.setPreferredSize(new Dimension(300, HEIGHT));
    }

    // MODIFIES: orderPanel, orderList, totalLabel
    // EFFECTS: build a orderPanel with ordered FoodItem show in the panel and count total price at
    //          the bottom of the panel
    private void createOrderPanel() {
        orderPanel = new JPanel(new BorderLayout());
        orderPanel.setBorder(BorderFactory.createTitledBorder("Current Order"));
        
        orderList = new JList<>(orderListModel);
        orderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        totalLabel = new JLabel("Total: $0.00");
        
        orderPanel.add(new JScrollPane(orderList), BorderLayout.CENTER);
        orderPanel.add(totalLabel, BorderLayout.SOUTH);
    }

    // MODIFEIS: buttonPanel, addButton, removeButton, saveButton, loadButton, commentButton, viewCommentsButton
    //           placeOrderButton
    // EFFECTS:  create the buttonPanel with a list of panel in it.
    private void createButtonPanel() {
        buttonPanel = new JPanel(new FlowLayout());
        
        JButton addButton = new JButton("Add to Order");
        JButton removeButton = new JButton("Remove from Order");
        JButton saveButton = new JButton("Save Order");
        JButton loadButton = new JButton("Load Order");
        JButton commentButton = new JButton("Add Comment");
        JButton viewCommentsButton = new JButton("View Comments");
        JButton placeOrderButton = new JButton("Place Order");
        
        addButton.addActionListener(e -> addSelectedItemToOrder());
        removeButton.addActionListener(e -> removeSelectedItemFromOrder());
        saveButton.addActionListener(e -> saveOrder());
        loadButton.addActionListener(e -> loadOrder());
        commentButton.addActionListener(e -> addComment());
        viewCommentsButton.addActionListener(e -> viewComments());
        placeOrderButton.addActionListener(e -> placeOrder());
        
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(commentButton);
        buttonPanel.add(viewCommentsButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(placeOrderButton);
    }

    // MODIFIES: this
    // EFFECTS: Initializes the menu with predefined food items.
    private void initializeMenu() {
        menu.add(new FoodItem("MoonCake", "Traditional Chinese food for celebrating mid-autumn festival", 9.99));
        menu.add(new FoodItem("Cheese Burger", "Juicy beef cheese burger", 6.49));
        menu.add(new FoodItem("Sushi", "Fresh salmon sushi", 2.99));
        menu.add(new FoodItem("Kung Pao Chicken", 
                "Spicy Chinese stir-fry with chicken, peanuts, and chili peppers.", 18.99));
        menu.add(new FoodItem("Salad", "Fresh garden salad", 5.99));
    }

    // EFFECTS: Helper method to get menu item names for JList
    private DefaultListModel<String> getMenuNames() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (FoodItem item : menu) {
            listModel.addElement(item.getName());
        }
        return listModel;
    }

    // REQUIRES: selectedIndex >= 0
    // EFFECTS: Add selected item to order
    private void addSelectedItemToOrder() {
        int selectedIndex = menuList.getSelectedIndex();
        if (selectedIndex >= 0) {
            FoodItem selectedItem = menu.get(selectedIndex);
            currentOrder.addFoodItem(selectedItem);
            updateOrderList();
            updateTotal();
            isOrderSaved = false;  // Order modified, needs saving
        }
    }

    // REQUIRES: selectedIndex >= 0
    // EFFECTS: Remove selected item from order
    private void removeSelectedItemFromOrder() {
        int selectedIndex = orderList.getSelectedIndex();
        if (selectedIndex >= 0) {
            FoodItem selectedItem = currentOrder.getOrderedItems().get(selectedIndex);
            currentOrder.removeFoodItem(selectedItem);
            updateOrderList();
            updateTotal();
            isOrderSaved = false;  // Order modified, needs saving
        }
    }

    // EFFECTS: Update the order list display
    private void updateOrderList() {
        orderListModel.clear();
        for (FoodItem item : currentOrder.getOrderedItems()) {
            orderListModel.addElement(item.getName() + " - $" + String.format("%.2f", item.getPrice()));
        }
    }

    // EFFECTS: Update the total price display
    private void updateTotal() {
        totalLabel.setText("Total: $" + String.format("%.2f", currentOrder.countTotal()));
    }

    // EFFECTS: Add comment to selected item
    private void addComment() {
        int selectedIndex = menuList.getSelectedIndex();
        if (selectedIndex >= 0) {
            FoodItem selectedItem = menu.get(selectedIndex);
            String comment = JOptionPane.showInputDialog(this, "Enter your comment:");
            if (comment != null && !comment.trim().isEmpty()) {
                selectedItem.addComment(comment);
                JOptionPane.showMessageDialog(this, "Comment added successfully!");
            }
        }
    }

    // EFFECTS: Save order to file
    private void saveOrder() {
        try {
            jsonWriter.open();
            jsonWriter.write(currentOrder);
            jsonWriter.close();
            isOrderSaved = true;  // Order has been saved
            JOptionPane.showMessageDialog(this, "Order saved successfully!");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to save order: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: Prompt to save order when closing
    private void saveOrderPrompt() {
        if (!currentOrder.orderisEmpty() && !isOrderSaved) {
            int result = JOptionPane.showConfirmDialog(this,
                    "Would you like to save your order before quitting?",
                    "Save Order",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                saveOrder();
            }
        }
    }   

    // EFFECTS: Load order from file
    private void loadOrder() {
        try {
            currentOrder = jsonReader.read();
            updateOrderList();
            updateTotal();
            JOptionPane.showMessageDialog(this, "Order loaded successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to load order: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            currentOrder = new Order();
        }
    }

    // EFFECTS: Prompt to load order when starting
    private void loadOrderPrompt() {
        try {
            Order loadedOrder = jsonReader.read();
            if (!loadedOrder.orderisEmpty()) {
                int result = JOptionPane.showConfirmDialog(this,
                        "Would you like to load your previous order?",
                        "Load Order",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    currentOrder = loadedOrder;
                    updateOrderList();
                    updateTotal();
                } else {
                    currentOrder = new Order();
                }
            }
        } catch (IOException e) {
            // If file doesn't exist or can't be read, silently create new order
            currentOrder = new Order();
        }
    }

    // EFFECTS: Place the order and clear it while preserving comments
    private void placeOrder() {
        if (!currentOrder.orderisEmpty()) {
            double total = currentOrder.countTotal();
            String message = "Order placed successfully!\n\n" 
                    + "Total amount: $" + String.format("%.2f", total) + "\n" 
                    + "Your food will be delivered soon. Thank you for choosing QuickBite!";
            JOptionPane.showMessageDialog(this, message, 
                    "Order Confirmation", JOptionPane.INFORMATION_MESSAGE);
            currentOrder.clearOrder();
            updateOrderList();
            updateTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Your order is empty!", 
                    "Empty Order", JOptionPane.WARNING_MESSAGE);
        }
    }

    // REQUIRES: selectedIndex >= 0
    // EFFECTS: View comments for selected item
    private void viewComments() {
        int selectedIndex = menuList.getSelectedIndex();
        if (selectedIndex >= 0) {
            FoodItem selectedItem = menu.get(selectedIndex);
            List<String> comments = selectedItem.getComments();
            
            if (comments.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                        "No comments yet for " + selectedItem.getName(), 
                        "Comments", 
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder commentText = new StringBuilder();
                commentText.append("Comments for ").append(selectedItem.getName()).append(":\n\n");
                for (String comment : comments) {
                    commentText.append("- ").append(comment).append("\n");
                }
                JOptionPane.showMessageDialog(this, 
                        commentText.toString(), 
                        "Comments", 
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // MODIFIES: welcomPanel, welcomeImage
    // EFFECTS: Sets the layout of the JFrame to BorderLayout and initializes the welcome page.
    @SuppressWarnings("methodlength")
    private void createWelcomePage() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        
        welcomePanel = new JPanel(new BorderLayout());
        welcomeImage = new ImageIcon("./data/QuickBite.png");
        
        // Get original image dimensions
        int originalWidth = welcomeImage.getIconWidth();
        int originalHeight = welcomeImage.getIconHeight();
        
        // Calculate scaling factor to maintain aspect ratio
        double scaleFactor = Math.min(
                (double) (WIDTH - 200) / originalWidth,
                (double) (HEIGHT - 200) / originalHeight
        );
        
        // Scale image maintaining aspect ratio
        Image img = welcomeImage.getImage();
        int scaledWidth = (int) (originalWidth * scaleFactor);
        int scaledHeight = (int) (originalHeight * scaleFactor);
        Image scaledImg = img.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        welcomeImage = new ImageIcon(scaledImg);
        
        // Create image label with centered alignment
        JLabel imageLabel = new JLabel(welcomeImage);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        
        // Create welcome text
        JLabel welcomeText = new JLabel("Welcome to QuickBite!");
        welcomeText.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeText.setHorizontalAlignment(JLabel.CENTER);
        
        // Create enter button
        JButton enterButton = new JButton("Enter");
        enterButton.setFont(new Font("Arial", Font.PLAIN, 18));
        enterButton.setPreferredSize(new Dimension(120, 40));
        enterButton.addActionListener(e -> switchToMainMenu());
        
        // Panel for button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        buttonPanel.add(enterButton);
        
        // Add components to welcome panel
        welcomePanel.add(welcomeText, BorderLayout.NORTH);
        welcomePanel.add(imageLabel, BorderLayout.CENTER);
        welcomePanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add welcome panel to frame
        add(welcomePanel);
        pack();
    }

    // EFFECTS: Switch from welcome page to main menu(Removes the existing welcomePanel from the frame)
    private void switchToMainMenu() {
        remove(welcomePanel);
        initializeGraphics();
        loadOrderPrompt();
        revalidate();
        repaint();
    }

    // MODIFIES: foodImageLabel
    //EFFECTS: create a hashmap to store the image for each certain foodItem.
    private void initializeFoodImages() {
        foodImages = new HashMap<>();
        foodImages.put("MoonCake", new ImageIcon("./data/mooncake.jpg"));
        foodImages.put("Cheese Burger", new ImageIcon("./data/cheeseburger.jpg"));
        foodImages.put("Sushi", new ImageIcon("./data/sushi.jpg"));
        foodImages.put("Kung Pao Chicken", new ImageIcon("./data/kunpaoChicken.jpg"));
        foodImages.put("Salad", new ImageIcon("./data/salad.jpg"));
    }

    //EFFECTS: get the foodItem's image.
    private void updateFoodImage(String foodName) {
        ImageIcon icon = foodImages.get(foodName);
        if (icon != null) {
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            foodImageLabel.setIcon(new ImageIcon(scaledImg));
        } else {
            foodImageLabel.setIcon(null);
        }
    }
}