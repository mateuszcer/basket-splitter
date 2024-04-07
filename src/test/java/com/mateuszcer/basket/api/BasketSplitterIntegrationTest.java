package com.mateuszcer.basket.api;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mateuszcer.basket.exception.ProductNotInConfigurationException;
import com.mateuszcer.basket.utils.io.FileParser;

public class BasketSplitterIntegrationTest {

    @Test
    void testSplit() {
        // Given
        List<String> items = FileParser.loadFromJSONFile("src/test/resources/solving/test-basket.json");

        BasketSplitter splitter = new BasketSplitter("src/test/resources/solving/test-config.json");

        // When
        Map<String, List<String>> distribution = splitter.split(items);

        // Then
        assertAll(() -> assertEquals(distribution.size(), 2),
                () -> assertContainsItems(distribution.get("Parcel locker"),
                        "English Muffin",
                        "Ecolab - Medallion",
                        "Chocolate - Unsweetened",
                        "Cheese Cloth"),
                () -> assertContainsItems(distribution.get("Pick-up point"),
                        "Sole - Dover, Whole, Fresh",
                        "Cookies Oatmeal Raisin"));
    }

    @Test
    void testSplit2() {
        // Given
        List<String> items = FileParser.loadFromJSONFile("src/test/resources/solving/basket-2.json");

        BasketSplitter splitter = new BasketSplitter("src/test/resources/solving/config.json");

        // When
        Map<String, List<String>> distribution = splitter.split(items);

        // Then
        assertAll(() -> assertEquals(3, distribution.size(), "Expected three delivery options"),
                () -> assertContainsItems(distribution.get("Same day delivery"),
                        "Sauce - Mint",
                        "Numi - Assorted Teas",
                        "Garlic - Peeled"),
                () -> assertContainsItems(distribution.get("Courier"), "Cake - Miini Cheesecake Cherry"),
                () -> assertContainsItems(distribution.get("Express Collection"),
                        "Fond - Chocolate",
                        "Chocolate - Unsweetened",
                        "Nut - Almond, Blanched, Whole",
                        "Haggis",
                        "Mushroom - Porcini Frozen",
                        "Longan",
                        "Bag Clear 10 Lb",
                        "Nantucket - Pomegranate Pear",
                        "Puree - Strawberry",
                        "Apples - Spartan",
                        "Cabbage - Nappa",
                        "Bagel - Whole White Sesame",
                        "Tea - Apple Green Tea"));
    }

    @Test
    void testSplitWithNonExistentProductThrowsError() {
        // Given
        List<String> items = List.of("Steak (300g)",
                "Carrots (1kg)",
                "AA Battery (4 Pcs.)",
                "Espresso Machine",
                "Garden Chair",
                "Non Existent Product");

        BasketSplitter splitter = new BasketSplitter("src/test/resources/solving/itest-config.json");

        // Then
        assertThrows(ProductNotInConfigurationException.class, () -> splitter.split(items));

    }

    private void assertContainsItems(List<String> actualItems, String... expectedItems) {
        assertTrue(actualItems.containsAll(Arrays.asList(expectedItems)),
                "Expected items not found in the distribution");
    }

}
