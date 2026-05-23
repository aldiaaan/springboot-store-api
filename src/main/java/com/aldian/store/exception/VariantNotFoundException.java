package com.aldian.store.exception;

import org.springframework.http.HttpStatus;
import java.util.UUID;

public class VariantNotFoundException extends ClientException {

    public VariantNotFoundException(UUID variantId) {
        super("INV-4004", "Variant not found with ID: " + variantId, HttpStatus.NOT_FOUND);
    }
}