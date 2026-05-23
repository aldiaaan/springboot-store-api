package com.aldian.store.exception;

import org.springframework.http.HttpStatus;
import java.util.UUID;

public class ItemNotFoundException extends ClientException {

    public ItemNotFoundException(UUID itemId) {
        super("ITM-4004", "Item not found with ID: " + itemId, HttpStatus.NOT_FOUND);
    }
}