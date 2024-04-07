package com.mateuszcer.basket.logic.impl;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.mockito.Mockito;

import static org.mockito.Mockito.when;

import com.mateuszcer.basket.logic.BasketSplitterService;
import com.mateuszcer.basket.logic.Configuration;

public class BasketSplitterServiceImplTest {

    @Test
    void testGetBestDistribution_OneProduct() {
        // Given
        Configuration configuration = Mockito.mock(Configuration.class);
        when(configuration.containsProduct("Product1")).thenReturn(true);
        when(configuration.getDeliveryOptions("Product1")).thenReturn(Arrays.asList("Option1", "Option2"));

        BasketSplitterService splitterService = new BasketSplitterServiceImpl(configuration);
        List<String> products = List.of("Product1");

        // When
        Map<String, List<String>> result = splitterService.getBestDistribution(products);

        // Then
        assertEquals(1, result.size());
        assertEquals(List.of("Product1"), result.get("Option1"));
    }

    @Test
    void testGetBestDistribution_MultipleProducts() {
        // Given
        Configuration configuration = Mockito.mock(Configuration.class);
        when(configuration.containsProduct("Product1")).thenReturn(true);
        when(configuration.getDeliveryOptions("Product1")).thenReturn(Arrays.asList("Option1", "Option2", "Option3"));
        when(configuration.containsProduct("Product2")).thenReturn(true);
        when(configuration.getDeliveryOptions("Product2")).thenReturn(List.of("Option1", "Option2"));
        when(configuration.containsProduct("Product3")).thenReturn(true);
        when(configuration.getDeliveryOptions("Product3")).thenReturn(List.of("Option2"));
        when(configuration.containsProduct("Product4")).thenReturn(true);
        when(configuration.getDeliveryOptions("Product4")).thenReturn(List.of("Option3"));

        BasketSplitterService splitterService = new BasketSplitterServiceImpl(configuration);
        List<String> products = List.of("Product1", "Product2", "Product3", "Product4");

        // When
        Map<String, List<String>> result = splitterService.getBestDistribution(products);

        // Then
        assertEquals(List.of("Product1", "Product2", "Product3"), result.get("Option2"));
        assertEquals(List.of("Product4"), result.get("Option3"));
    }

    @Test
    void testGetBestDistribution_NoProducts() {
        // Given
        Configuration configuration = Mockito.mock(Configuration.class);

        BasketSplitterService splitterService = new BasketSplitterServiceImpl(configuration);
        List<String> products = List.of();

        // When
        Map<String, List<String>> result = splitterService.getBestDistribution(products);

        // Then
        assertEquals(0, result.size());
    }
}
