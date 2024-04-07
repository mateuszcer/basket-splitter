package com.mateuszcer.basket.exception;

public class FileParserException extends RuntimeException {
    public FileParserException(String message, Throwable err) {
        super(message, err);
    }
}
