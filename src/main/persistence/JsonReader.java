package persistence;

import model.FoodItem;
import model.Order;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;


// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

      // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Order read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseOrder(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses order from JSON object and returns it
    private Order parseOrder(JSONObject jsonObject) {
        Order order = new Order();
        addFoodItems(order, jsonObject);
        return order;
    }

    // MODIFIES: order
    // EFFECTS: parses food items from JSON object and adds them to order
    private void addFoodItems(Order order, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("orderedItems");
        for (Object json : jsonArray) {
            JSONObject nextFoodItem = (JSONObject) json;
            addFoodItem(order, nextFoodItem);
        }
    }

    // MODIFIES: order
    // EFFECTS: parses foodItem from JSON object and adds it to order
    private void addFoodItem(Order order, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        double price = jsonObject.getDouble("price");

        JSONArray commentsJsonArray = jsonObject.getJSONArray("comments");
        List<String> comments = new ArrayList<>();
        for (int i = 0; i < commentsJsonArray.length(); i++) {
            comments.add(commentsJsonArray.getString(i));
        }
        
        FoodItem foodItem = new FoodItem(name, description, price);
        for (String comment : comments) {
            foodItem.addComment(comment);  
        }

        order.addFoodItemWithoutLogging(foodItem);
    }
}
 