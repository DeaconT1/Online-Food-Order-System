package persistence;

import model.FoodItem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

public class JsonTest {
    protected void checkFoodItem(String name, String description, double price, List<String> comments, FoodItem f) {
        assertEquals(name, f.getName());
        assertEquals(description, f.getDescription());
        assertEquals(price, f.getPrice());
        assertEquals(comments, f.getComments());
    }
}
