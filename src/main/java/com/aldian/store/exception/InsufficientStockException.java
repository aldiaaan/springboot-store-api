package com.aldian.store.exception;

import org.springframework.http.HttpStatus;

public class InsufficientStockException extends ClientException {

    public InsufficientStockException(String message) {
        super("INV-4000", message, HttpStatus.BAD_REQUEST);
    }
}