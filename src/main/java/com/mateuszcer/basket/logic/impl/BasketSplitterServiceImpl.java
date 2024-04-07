package com.mateuszcer.basket.logic.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mateuszcer.basket.logic.BasketSplitterService;
import com.mateuszcer.basket.logic.Configuration;

/**
 * Implementation of the {@link BasketSplitterService} interface hat provides methods
 * to split a list of products into the best possible distribution into delivery options.
 * "Best" distribution means allocating products to delivery options in a way that maximizes
 * the capacity utilization of each delivery option
 */
public class BasketSplitterServiceImpl implements BasketSplitterService {

    private final Configuration configuration;

    public BasketSplitterServiceImpl(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * Retrieves the best distribution of products across available delivery options.
     * Products are distributed in a way to maximize sizes of delivery options.
     *
     * @param products the list of products to be distributed
     * @return a Map representing the best distribution of products across delivery options,
     * where keys are delivery options and values are lists of products assigned to each option
     */
    @Override
    public Map<String, List<String>> getBestDistribution(List<String> products) {
        List<String> productsCopy = new ArrayList<>(products);
        Map<String, Integer> deliveryOptionCapacity = getDeliveryOptionsCapacity(productsCopy);
        Map<String, List<String>> solution = new HashMap<>();

        while (!productsCopy.isEmpty()) {
            String bestDeliveryOption = findBestDeliveryOption(deliveryOptionCapacity);

            List<String> productsForBestOption = assignProductsToDeliveryOption(productsCopy, bestDeliveryOption);

            updateCapacity(deliveryOptionCapacity, productsForBestOption);

            solution.put(bestDeliveryOption, productsForBestOption);
        }

        return solution;
    }

    /**
     * Finds the delivery option with the highest capacity based on the provided delivery option capacity map.
     * If two options have the same capacity, best one will be chosen based on name.
     *
     * @param deliveryOptionCapacity A map containing delivery options as keys and their respective capacities as values.
     * @return The delivery option with the highest capacity. If the map is empty, an empty string is returned.
     */
    private String findBestDeliveryOption(Map<String, Integer> deliveryOptionCapacity) {
        return deliveryOptionCapacity.entrySet()
                .stream()
                .max(this.getDeliveryOptionComparator())
                .map(Map.Entry::getKey)
                .orElse("");
    }

    /**
     * Assigns products to a given delivery option and removes them from the list of available products.
     *
     * @param products       The list of available products to be assigned.
     * @param deliveryOption The delivery option to which products are assigned.
     * @return A list of products assigned to the given delivery option.
     */
    private List<String> assignProductsToDeliveryOption(List<String> products, String deliveryOption) {
        List<String> productsForBestOption = new ArrayList<>();
        Iterator<String> iterator = products.iterator();

        while (iterator.hasNext()) {
            String product = iterator.next();
            if (configuration.getDeliveryOptions(product).contains(deliveryOption)) {
                productsForBestOption.add(product);
                iterator.remove();
            }
        }
        return productsForBestOption;
    }

    /**
     * Updates the capacity of delivery options based on the products assigned to them.
     *
     * @param deliveryOptionCapacity A map containing delivery options as keys and their respective capacities as values.
     * @param products               The list of products assigned to various delivery options.
     */
    private void updateCapacity(Map<String, Integer> deliveryOptionCapacity, List<String> products) {
        for (String product : products) {
            for (String option : configuration.getDeliveryOptions(product)) {
                deliveryOptionCapacity.compute(option, (opt, val) -> val - 1);
            }
        }
    }

    /**
     * Calculates the capacity of each delivery option based on the list of products.
     *
     * @param products The list of products to be considered for calculating delivery option capacities.
     * @return A map containing each delivery option as a key and its corresponding capacity as a value.
     */
    private Map<String, Integer> getDeliveryOptionsCapacity(List<String> products) {
        return products.stream()
                .flatMap(product -> configuration.getDeliveryOptions(product).stream())
                .collect(Collectors.toMap(deliveryOption -> deliveryOption, deliveryOption -> 1, Integer::sum));
    }

    /**
     * Provides a comparator to compare entries in a map based on their values (capacities),
     * and if values are equal, compares them based on their keys (delivery option names).
     *
     * @return A comparator for comparing entries in a map based on their values (capacities),
     * and if values are equal, compares them based on their keys (delivery option names).
     */
    private Comparator<Map.Entry<String, Integer>> getDeliveryOptionComparator() {
        return Comparator.comparingInt((Map.Entry<String, Integer> entry) -> entry.getValue())
                .thenComparing(Map.Entry::getKey);
    }
}
