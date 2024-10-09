package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

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
    void TestAddValidComment() {
        testFoodItem1.addComment("Nice! I like it!");
        testFoodItem1.addComment("It's a bad experience!");
        List<String> testComments1 = testFoodItem1.getComments();

        assertEquals(2, testComments1.size());
        assertTrue(testComments1.contains("Nice! I like it!"));
        assertTrue(testComments1.contains("It's a bad experience!"));
    }


}
