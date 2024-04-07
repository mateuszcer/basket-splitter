package com.mateuszcer.basket.logic.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mateuszcer.basket.logic.BasketSplitterService;
import com.mateuszcer.basket.logic.Configuration;

/**
 * Implementation of the {@link BasketSplitterService} interface hat provides methods
 * to split a list of products into the best possible distribution based on delivery options.
 */
public class BasketSplitterServiceImpl implements BasketSplitterService {

    private final Configuration configuration;

    public BasketSplitterServiceImpl(Configuration configuration) {
        this.configuration = configuration;
    }


    /**
     * Retrieves the best distribution of products across available delivery options.
     * Each product is assigned to the delivery option with the highest capacity.
     *
     * @param products the list of products to be distributed
     * @return a Map representing the best distribution of products across delivery options,
     *         where keys are delivery options and values are lists of products assigned to each option
     */
    @Override
    public Map<String, List<String>> getBestDistribution(List<String> products) {
        Map<String, Integer> deliveryOptionCapacity = getDeliveryOptionCapacity(products);

        Map<String, List<String>> solution = new HashMap<>();

        products.forEach(product -> {
            String bestDeliveryOption = configuration.getDeliveryOptions(product).stream()
                    .max(java.util.Comparator.comparingInt(deliveryOptionCapacity::get))
                    .orElse("");

            solution.computeIfAbsent(bestDeliveryOption, k -> new LinkedList<>()).add(product);
        });

        return solution;
    }

    private Map<String, Integer> getDeliveryOptionCapacity(List<String> products) {
        return products.stream()
                .flatMap(product -> configuration.getDeliveryOptions(product).stream())
                .collect(Collectors.toMap(
                        deliveryOption -> deliveryOption,
                        deliveryOption -> 1,
                        Integer::sum
                ));
    }
}
