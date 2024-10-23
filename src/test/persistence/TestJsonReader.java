package persistence;

import model.FoodItem;
import model.Order;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestJsonReader extends JsonTest{
    
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Order order = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyOrder() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyOrder.json");
        try {
            Order order = reader.read();
            assertTrue(order.orderisEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralOrder() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralOrder.json");
        try {
            Order order = reader.read();
            List<FoodItem> f = order.getOrderedItems();
            assertEquals(2, f.size());
            checkFoodItem("MoonCake", "Traditional Chinese food for celebrating mid-autumn festival", 9.99, Arrays.asList("Delicious!!", "Nice~"), f.get(0));
            checkFoodItem("Sushi", "Fresh salmon sushi", 2.99, new ArrayList<>(), f.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

