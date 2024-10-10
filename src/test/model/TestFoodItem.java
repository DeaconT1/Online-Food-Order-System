package model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestFoodItem {
    private FoodItem testFoodItem1;
    private FoodItem testFoodItem2;
    
    @BeforeEach
    void runBefore() {
        testFoodItem1 = new FoodItem("MoonCake", "A light, fluffy, and deliciously sweet milk-flavored cake that melts in your mouth.", 2.99);
        testFoodItem2 = new FoodItem("Hamburger", "Nice Food", 8.99);
    }

    @Test
    void TestConstructor() {
        FoodItem testItem = new FoodItem("ABC", "Abc123456", 12.99);
        assertEquals("ABC", testItem.getName());
        assertEquals("Abc123456", testItem.getDescription());
        assertEquals(12.99, testItem.getPrice(), 0.01);

    }

    @Test
    void TestSetAndGetName() {
        assertEquals("MoonCake", testFoodItem1.getName());
        testFoodItem1.setName("Chips");
        assertEquals("Chips", testFoodItem1.getName());
    }

    @Test
    void TestSetAndGetDescription() {
        assertEquals("Nice Food", testFoodItem2.getDescription());
        testFoodItem2.setDescription("What a nice food!!");
        assertEquals("What a nice food!!", testFoodItem2.getDescription());
    }

    @Test
    void TestSetAndGetPrice() {
        assertEquals(2.99, testFoodItem1.getPrice(), 0.01);
        testFoodItem1.setPrice(39.99);
        assertEquals(39.99, testFoodItem1.getPrice(), 0.01);
    }

    @Test
    void TestIncreasePrice() {
        testFoodItem1.increasePrice(3.01);
        assertEquals(6.0, testFoodItem1.getPrice(), 0.01);
        testFoodItem2.increasePrice(7.98);
        assertEquals((8.99 + 7.98), testFoodItem2.getPrice(), 0.01);
    }

    @Test
    void TestDecreasePrice() {
        testFoodItem1.decreasePrice(1.01);
        assertEquals(1.98, testFoodItem1.getPrice(), 0.01);
        testFoodItem2.decreasePrice(7.98);
        assertEquals(1.01, testFoodItem2.getPrice(), 0.01);
    }

    @Test
    void TestAddAndGetComment() {
        testFoodItem1.addComment("I like it!!");
        assertEquals(1, testFoodItem1.getComments().size());
        assertEquals("I like it!!", testFoodItem1.getComments().get(0));
        testFoodItem1.addComment("I hate it!!");
        assertEquals(2, testFoodItem1.getComments().size());
        assertEquals("I hate it!!", testFoodItem1.getComments().get(1));
    }

    @Test
    void TestRemoveComment() {
        testFoodItem1.addComment("I like it!!");
        assertEquals(1, testFoodItem1.getComments().size());
        assertEquals("I like it!!", testFoodItem1.getComments().get(0));
        testFoodItem1.removeComment("I like it!!");
        assertEquals(0, testFoodItem1.getComments().size());
        }
}


