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

    // MODIFIES: welcomPanel, welcomeImage
    // EFFECTS: Sets the layout of the JFrame to BorderLayout and initializes the welcome page.
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