package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class TestOrder {
    private Order testOrder;
    private FoodItem testFoodItem1;
    private FoodItem testFoodItem2;
    private FoodItem testFoodItem3;

    @BeforeEach
    void runBefore() {
        testOrder = new Order();
        testFoodItem1 = new FoodItem("Pizza", "A delicious cheese pizza", 9.99);
        testFoodItem2 = new FoodItem("Burger", "Juicy beef burger", 6.49);
        testFoodItem3 = new FoodItem("Sushi", "Fresh salmon sushi", 12.99);

    }

    @Test
    void testConstructor() {
        Order order = new Order();
        assertTrue(order.getOrderedItems().isEmpty());
        assertEquals(0, order.getOrderedItems().size());
        assertEquals(0.0, order.countTotal(), 0.01);
    }
    

    @Test
    void testAddandGetandCountFoodItem() {
        // test three method: addFoodItem
        //                    getOrderedItems
        //                    getItemCount
        testOrder.addFoodItem(testFoodItem1); 
        testOrder.addFoodItem(testFoodItem2);  
        assertEquals(2, testOrder.getItemCount());

        assertTrue(testOrder.containsFoodItem(testFoodItem1));
        assertTrue(testOrder.containsFoodItem(testFoodItem2));

        List<FoodItem> testOrderedItems = testOrder.getOrderedItems();
        assertEquals("Pizza", testOrderedItems.get(0).getName());
        assertEquals("Burger", testOrderedItems.get(1).getName());

    }

    @Test
    void testRemoveFoodItem() {
        //test remove single item
        testOrder.addFoodItem(testFoodItem2);  
        assertEquals(1, testOrder.getItemCount());
        testOrder.removeFoodItem(testFoodItem2);
        assertEquals(0, testOrder.getItemCount());

        //tes remove multiple items
        Order testOrder1 = new Order();
        testOrder1.addFoodItem(testFoodItem1); 
        testOrder1.addFoodItem(testFoodItem2);  
        assertEquals(2, testOrder1.getItemCount());
        testOrder1.removeFoodItem(testFoodItem1);  
        assertEquals(1, testOrder1.getItemCount());

        assertTrue(testOrder1.containsFoodItem(testFoodItem2));

        List<FoodItem> testOrderedItems1 = testOrder1.getOrderedItems();
        assertEquals("Burger", testOrderedItems1.get(0).getName());

        testOrder1.removeFoodItem(testFoodItem2);
        assertEquals(0, testOrder1.getItemCount());

    }

    @Test
    void testClearOrder() {
        testOrder.addFoodItem(testFoodItem1); 
        testOrder.addFoodItem(testFoodItem2);  
        assertEquals(2, testOrder.getItemCount());

        testOrder.clearOrder();
        assertEquals(0, testOrder.getItemCount());

    }

    @Test
    void testcountTotal() {
        //test count single item
        testOrder.addFoodItem(testFoodItem3);
        assertEquals(12.99, testOrder.countTotal());

        Order testOrder1 = new Order();
        testOrder1.addFoodItem(testFoodItem1); 
        testOrder1.addFoodItem(testFoodItem2);  
        assertEquals((9.99 + 6.49), testOrder1.countTotal());
    }



        
} 



    

