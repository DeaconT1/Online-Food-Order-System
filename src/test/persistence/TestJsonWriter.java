package persistence;

import model.FoodItem;
import model.Order;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestJsonWriter extends JsonTest {
    
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyOrder() {
        try {
            Order order = new Order();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(order);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            order = reader.read();
            assertTrue(order.orderisEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriteGeneralOrder() {
        try {
            Order order = new Order();
            order.addFoodItem(new FoodItem("MoonCake", 
                    "Traditional Chinese food for celebrating mid-autumn festival", 9.99));
            order.addFoodItem(new FoodItem("Cheese Burger", "Juicy beef cheese burger", 6.49));
            List<FoodItem> f = order.getOrderedItems();
            f.get(0).addComment("Nice!!");
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralOrder.json");
            writer.open();
            writer.write(order);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralOrder.json");
            order = reader.read();
            
            assertEquals(2, f.size());
            checkFoodItem("MoonCake", "Traditional Chinese food for celebrating mid-autumn festival", 
                    9.99, Arrays.asList("Nice!!"), f.get(0));
            checkFoodItem("Cheese Burger", "Juicy beef cheese burger", 6.49, new ArrayList<>(), f.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }

    }
}
