package com.mateuszcer.basket.exception;

public class ProductNotInConfigurationException extends RuntimeException {
    public ProductNotInConfigurationException(String message) {
        super(message);
    }
}
