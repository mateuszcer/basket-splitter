package com.mateuszcer.basket.logic;

import java.util.List;

public interface Configuration {

    List<String> getDeliveryOptions(String product);

    boolean containsProduct(String product);

}
