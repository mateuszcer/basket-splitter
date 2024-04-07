package com.mateuszcer.basket.logic;

import java.util.List;
import java.util.Map;

public interface BasketSplitterService {

    Map<String, List<String>> getBestDistribution(List<String> products);
}
