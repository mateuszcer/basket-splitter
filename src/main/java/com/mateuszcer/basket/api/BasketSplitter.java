package com.mateuszcer.basket.api;

import java.util.List;
import java.util.Map;

import com.mateuszcer.basket.logic.Configuration;
import com.mateuszcer.basket.logic.impl.MapBasedConfigurationImpl;
import com.mateuszcer.basket.logic.BasketSplitterService;
import com.mateuszcer.basket.logic.impl.BasketSplitterServiceImpl;
import com.mateuszcer.basket.utils.io.FileParser;

public class BasketSplitter {

    private final BasketSplitterService basketSplitterService;

    public BasketSplitter(String absolutePathToConfigFile) {
        Configuration configuration = new MapBasedConfigurationImpl(FileParser.loadFromJSONFile(absolutePathToConfigFile));
        basketSplitterService = new BasketSplitterServiceImpl(configuration);
    }

    public Map<String, List<String>> split(List<String> items) {
        return basketSplitterService.getBestDistribution(items);
    }

}
