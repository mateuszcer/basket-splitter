package com.mateuszcer.basket.logic.impl;

import java.util.List;
import java.util.Map;

import com.mateuszcer.basket.logic.Configuration;
import com.mateuszcer.basket.exception.ProductNotInConfigurationException;

public class MapBasedConfigurationImpl implements Configuration {

    private final Map<String, List<String>> configuration;

    public MapBasedConfigurationImpl(Map<String, List<String>> configuration) {
        this.configuration = configuration;
    }

    @Override
    public List<String> getDeliveryOptions(String product) {
        if (!configuration.containsKey(product)) {
            throw new ProductNotInConfigurationException("%s not found in the configuration".formatted(product));
        }
        return configuration.get(product);
    }

    @Override
    public boolean containsProduct(String product) {
        return configuration.containsKey(product);
    }
}
